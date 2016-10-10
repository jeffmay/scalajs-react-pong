package io.gloriousfuture.pong

import monocle.macros.Lenses

@Lenses("_") case class PongSettings(board: BoardState, playerWidth: Int, ballSize: Int)
object PongSettings {
  object Implicits {
    implicit val defaults: PongSettings = PongSettings(
      BoardState.default,
      playerWidth = 10,
      ballSize = 5
    )
  }
  def defaults: PongSettings = Implicits.defaults
  def current(implicit settings: PongSettings): PongSettings = settings
}

@Lenses("_") case class BoardState(height: Int, width: Int)
object BoardState {
  val default: BoardState = BoardState(400, 500)
}
