import scala.util.matching.Regex

object Main extends App {
    val pointReg  = new Regex("\\((.*?),\\s(.*?)\\)")
    val radiusReg = new Regex("(?<=Radius: )(.+)(?=;)")
    
    scala.io.Source.fromFile(args(0)).getLines
    	.filter(!_.isEmpty)
        .map(line => {
            val m      = pointReg.findAllIn(line)
            val pts    = m.map(p => new Point(m.group(1).toFloat,m.group(2).toFloat)).toArray
            val circle = new Circle(pts(0),radiusReg.findFirstIn(line).get.toFloat)
            circle contains pts(1)
        })
        .foreach(println)
}

class Point(val x: Float=0, val y: Float=0) {
    def dist(that: Point) = { var (dx,dy) = (x - that.x,y - that.y); Math.sqrt(dx*dx + dy*dy) }
}

object Point {
    val EPSILON = 0.001
}

class Circle(val center: Point=new Point(), val radius: Float=1) {
    def contains(p: Point) = center.dist(p) <= radius + Point.EPSILON 
}