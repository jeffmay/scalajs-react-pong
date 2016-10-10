package io.gloriousfuture.pong

import japgolly.scalajs.react.ReactDOM
import org.scalajs.dom.html

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport

object Main extends js.JSApp {

  def main() = {
    println("JS env")
  }

  import PongSettings.Implicits.defaults

  @JSExport
  def start(element: html.Div) = {
    ReactDOM.render(PongReact.components.PongApp(), element)
  }

//	def renderGame(board: dom.Element, state: PongState) = {
//    <.svg(
//      ^.width := state.board.width,
//      p1Bar(state.p1State, state.board.width),
//      p2Bar(state.p1State, state.board.width)
//    )
//  }
//
//  def p1Bar(player: PlayerState, gameWidth: Int) = playerBar(isPlayerOne = true, player, gameWidth)
//  def p2Bar(player: PlayerState, gameWidth: Int) = playerBar(isPlayerOne = false, player, gameWidth)
//
//  def playerBar(isPlayerOne: Boolean, player: PlayerState, gameWidth: Int) = {
//    val color = if (isPlayerOne) "blue" else "red"
//    val barLeftCorner = if (isPlayerOne) 0 else gameWidth - player.width
//    <.rect(
//      ^.x := barLeftCorner,
//      ^.y := player.y,
//      ^.fill := color,
//      ^.width := "10px",
//      ^.height := s"${player.height}px"
//    )
//  }

}
