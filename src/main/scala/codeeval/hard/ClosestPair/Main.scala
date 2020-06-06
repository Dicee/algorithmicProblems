package codeeval.hard.ClosestPair

object Main extends App {
  val MAX_DIST = 10000

  val lines = scala.io.Source.fromFile(args(0)).getLines().toArray

  var i = 0
  while (i < lines.length && lines(i).toInt > 0) {
    val n        = lines(i).toInt
    var minDist  = Double.MaxValue
    val quadTree = new QuadTree

    for (j <- i + 1 to i + n) {
      val p   = lines(j).split(" ") match { case Array(x, y) => Point(x.toDouble, y.toDouble) }
      val d: Double = quadTree += p

      minDist = Math.min(minDist, d)
    }

    println(if (minDist > MAX_DIST) "INFINITY" else minDist.formatted("%.4f"))

    i += n + 1
  }
}

// we'll use the tree to compute the minimal distance while inserting the nodes rather than building the tree and then
// iterating over it. This is more efficient, but makes the tree less general purpose.
class QuadTree {
  import QuadTree.QuadTreeNode

  private var root: QuadTreeNode = _

  def +=(p: Point) = {
    if (root == null) { root = new QuadTreeNode(p); Double.MaxValue }
    else              root +=(p, Point(0, 0), QuadTree.MAX_BLOCK_SIZE)
  }
}

object QuadTree {
  private val MIN_COORD = 0
  private val MAX_COORD = 40000

  val MAX_BLOCK_SIZE = MAX_COORD - MIN_COORD

  private class QuadTreeNode(var value: Point) {
    private var topLeft    : QuadTreeNode = null
    private var topRight   : QuadTreeNode = null
    private var bottomRight: QuadTreeNode = null
    private var bottomLeft : QuadTreeNode = null

    def +=(p: Point, blockTopLeft: Point, blockSize: Double): Double = {
      val newBlockSize = blockSize / 2
      val Point(x, y)  = blockTopLeft
      val (midX, midY) = (x + newBlockSize, y + newBlockSize)

      val (pickedBlock, setBlock, newTopLeft) =
        if (p.x <= midX) { if (p.y <= midY) (topLeft,  topLeft  = _: QuadTreeNode, Point(x   , y)) else (bottomLeft , bottomLeft  = _: QuadTreeNode, Point(x   , midY)) }
        else             { if (p.y <= midY) (topRight, topRight = _: QuadTreeNode, Point(midX, y)) else (bottomRight, bottomRight = _: QuadTreeNode, Point(midX, midY)) }

        val minDist =
          List(Option(topLeft), Option(topRight), Option(bottomLeft), Option(bottomRight))
            .flatMap(nodeOpt => nodeOpt.map(_.value).map(_.dist(p)))
            .fold(Double.MaxValue)(Math.min)

      if (pickedBlock == null) { setBlock(new QuadTreeNode(p)); minDist }
      else                     { Math.min(minDist, pickedBlock += (p, newTopLeft, newBlockSize)) }
    }
  }
}

case class Point(x: Double, y: Double) {
  def dist(that: Point) = (x - that.x, y - that.y) match { case (a, b) => Math.sqrt(a*a + b*b) }
}