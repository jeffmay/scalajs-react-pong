package io.gloriousfuture.pong

import japgolly.scalajs.react.vdom.svg.prefix_<^._
import japgolly.scalajs.react.{BackendScope, Callback, ReactComponentB}
import org.scalajs.dom

object PongReact {

  def components(implicit settings: PongSettings): Components = new Components

  class Components(implicit settings: PongSettings) {

    class PongBackend($: BackendScope[Unit, PongState]) {

      val speed = 10

      private val moveP1Up = PongState._playerOneY.modify(_ - speed)
      private val moveP1Down = PongState._playerOneY.modify(_ + speed)

      def start() = Callback {
        dom.window.document.onkeypress = (e: dom.KeyboardEvent) => {
          e.key match {
            case "w" | "W" =>
              $.modState(moveP1Up).runNow()
            case "s" | "S" =>
              $.modState(moveP1Down).runNow()
            case _ =>
          }
        }
      }

      def render(state: PongState) = {
        <.svg(
          ^.height := settings.board.height,
          ^.width := settings.board.width,
          Player(state.p1State),
          Player(state.p2State)
        )
      }
    }

    val PongApp = ReactComponentB[Unit]("pong")
      .initialState(PongState.default)
      .renderBackend[PongBackend]
      .componentDidMount($ => $.backend.start())
      .build

    class PlayerBackend($: BackendScope[PlayerState, Unit]) {

      def render(player: PlayerState) = {
        <.rect(
          ^.fill := player.color,
          ^.x := player.location.x,
          ^.y := player.location.y,
          ^.width := player.width,
          ^.height := player.height
        )
      }
    }

    val Player = ReactComponentB[PlayerState]("player")
      .renderBackend[PlayerBackend]
      .build
  }
}
