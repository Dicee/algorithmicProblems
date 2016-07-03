package codeeval.hard.MinimumPathSum

import scala.collection.mutable

/**
 * A* algorithm
 */
object Main extends App {
  val lines = scala.io.Source.fromFile(args(0)).getLines().toArray

  var i = 0
  while (i < lines.length && lines(i).toInt > 0) {
    val n      = lines(i).toInt
    val matrix = lines.slice(i + 1, i + n + 1).map(_.split(',').map(_.toInt))

    // for A* to work we need the estimation of the distance to the solution to always underestimate the real distance
    // If we are unlucky, the min will be close to 0 and A* will become equivalent to Dijkstra
    val minCost     = matrix.map(_.min).min
    val bottomRight = (n - 1, n - 1)

    val heuristic = new Ordering[State] {
      override def compare          (x: State, y: State)               = estimatedCost(y) compare estimatedCost(x)
      private  def estimatedCost    (state: State)                     = state.cost + minCost * manhattanDistance(state.pos, bottomRight)
      private  def manhattanDistance(from: (Int, Int), to: (Int, Int)) = Math.abs(from._1 - to._1) + Math.abs(from._2 - to._2)
    }

    val toExplore = mutable.PriorityQueue(State(matrix(0)(0), (0, 0)))(heuristic)
    val explored  = mutable.HashSet[State]()

    while (toExplore.nonEmpty && toExplore.head.pos != bottomRight) {
      val state = toExplore.dequeue()

      toExplore ++= (for {
        (dx, dy) <- List((0, 1), (1, 0))
        if state.pos._1 + dx < n && state.pos._2 + dy < n
        newPos   = (state.pos._1 + dx, state.pos._2 + dy)                  ; if newPos != state.pos
        newState = State(state.cost + matrix(newPos._1)(newPos._2), newPos); if explored.add(newState)
      } yield newState)
    }

    println(toExplore.head.cost)

    i += n + 1
  }
}

case class State(cost: Long, pos: (Int, Int))