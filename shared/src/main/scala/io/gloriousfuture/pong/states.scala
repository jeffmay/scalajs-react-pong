package io.gloriousfuture.pong

import monocle.macros.Lenses

@Lenses("_") case class Point(x: Int, y: Int)

/**
  * @param location the upper-left-most point of the ball
  */
@Lenses("_") case class BallState(location: Point) {
  def height(implicit settings: PongSettings): Int = settings.ballSize
  def width(implicit settings: PongSettings): Int = settings.ballSize
  def contains(point: Point)(implicit settings: PongSettings): Boolean = {
    (point.x > location.x && point.x < (location.x + settings.ballSize)) &&
      (point.y > location.y && point.y < (location.y + settings.ballSize))
  }
}
object BallState {
  def center(implicit settings: PongSettings) = BallState(Point(
    x = (settings.board.width + settings.ballSize) / 2,
    y = (settings.board.height + settings.ballSize) / 2
  ))
}

@Lenses("_") case class PlayerState(color: String, location: Point, height: Int = 50) {
  def width(implicit settings: PongSettings): Int = settings.playerWidth
}
object PlayerState {
  val p1Default: PlayerState = PlayerState("blue", Point(0, 0))
  def p2Default(implicit settings: PongSettings): PlayerState = {
    PlayerState("red", Point(settings.board.width - settings.playerWidth, 0))
  }
}

@Lenses("_") case class PongState(
  p1State: PlayerState,
  p2State: PlayerState,
  ball: BallState
)
object PongState {

  def default(implicit settings: PongSettings): PongState = {
    PongState(PlayerState.p1Default, PlayerState.p2Default, BallState.center)
  }

  val _playerOneY = PongState._p1State ^|-> PlayerState._location ^|-> Point._y
  val _playerTwoY = PongState._p2State ^|-> PlayerState._location ^|-> Point._y
}
