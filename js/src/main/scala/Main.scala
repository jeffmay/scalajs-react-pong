package io.gloriousfuture.pong

import akka.actor.Actor
import org.scalajs.dom
import org.scalajs.dom.html

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom

case class Point(x: Int, y: Int)

/**
  * A pong ball is a 5 px square
  *
  * @param location the upper-left-most point of the ball
  */
case class BallState(location: Point) {
  def contains(point: Point): Boolean = {
    (point.x > location.x && point.x < (location.x + BallState.SIZE)) &&
      (point.y > location.y && point.y < (location.y + BallState.SIZE))
  }
}
object BallState {
  final val SIZE = 5
}

case class PlayerState(y: Int, height: Int = 10)
object PlayerState {
  final val WIDTH = 10
}

case class BoardState(height: Int, width: Int)
object BoardState {
  val default: BoardState = BoardState(400, 500)
}

case class PongState(p1State: PlayerState, p2State: PlayerState, ball: BallState, board: BoardState = BoardState.default)

sealed trait Move
case object Up extends Move
case object Down extends Move

class PlayerActor extends Actor {

  var state: PlayerState = PlayerState(0)

  override def receive: Receive = {
    case Up => state = state.copy(y = state.y + 1)
    case Down => state = state.copy(y = state.y - 1)
  }
}

object PongRender {

  final val PLAYER_WIDTH = 10
  final val BALL_SIZE = 5
}

object Main extends js.JSApp {

  import JsDom.svgTags._
  import JsDom.svgAttrs._
  import JsDom.implicits._

  def main() = {
    println("JS env")

    // something loops/calls, ask actors for state
  }

  @JSExport
  def start(element: html.Div) = {
    val boardRect = element.getBoundingClientRect()
    val center = Point(
      x = ((boardRect.width + PongRender.BALL_SIZE) / 2).toInt,
      y = ((boardRect.height + PongRender.BALL_SIZE) / 2).toInt
    )
    var state = PongState(PlayerState(0), PlayerState(0), BallState(center))
    for (d <- 0 to 10) {
      state = state.copy(p1State = state.p1State.copy(y = state.p1State.y + 5))
      println(s"State = $state")
      renderGame(element, state)
    }
  }

	def renderGame(board: dom.Element, state: PongState) = {
    val x = svg(
      width := state.board.width,
      p1Bar(state.p1State, state.board.width),
      p2Bar(state.p1State, state.board.width)
    )
    x.applyTo(board)
  }

  def p1Bar(player: PlayerState, gameWidth: Int) = playerBar(isPlayerOne = true, player, gameWidth)
  def p2Bar(player: PlayerState, gameWidth: Int) = playerBar(isPlayerOne = false, player, gameWidth)

  def playerBar(isPlayerOne: Boolean, player: PlayerState, gameWidth: Int) = {
    val color = if (isPlayerOne) "blue" else "red"
    val barLeftCorner = if (isPlayerOne) 0 else gameWidth - PongRender.PLAYER_WIDTH
    rect(
      x := barLeftCorner,
      y := player.y,
      fill := color,
      width := "10px",
      height := s"${player.height}px"
    )
  }

}
