import scala.collection.mutable.ArrayBuffer
import java.util.regex.Pattern
import scala.util.matching.Regex

// We will use a general algorithm working also with pointing float coordinates
object Main extends App {
    val regex = new Regex("\\((\\d+),(\\d)+\\)")
    
    scala.io.Source.fromFile(args(0)).getLines
    	.filter(!_.isEmpty)
        .map(line => {
            val m   = regex.findAllIn(line)
            val pts = m.map(x => Point(m.group(1).toFloat,m.group(2).toFloat)).toArray
            
            // we choose arbitrarily two lines with the 4 points. The lines may be
            // parallel, in that case we try to swap the order and get orthogonal lines
            var (v0,v1) = (pts(1) - pts(0),pts(3) - pts(2))
            
            v0 != Vector() && v1 != Vector() && 
            	(if (v0 == v1 || v0 == -v1) {
                    val (a,b) = if (v0 == v1) ((0,2),(1,3)) else ((0,3),(1,2))
                    pts(a._1) - pts(a._2) == pts(b._1) - pts(b._2)
                } else Point.cmp(v0.cross(v1)) && (v0.norm,v0.mid) == (v1.norm,v1.mid))
        })
        .foreach(println)
}

class Point(val x: Float = 0, val y: Float = 0) {
	def unary_-           = Point (-x      ,-y      )
    def +    (p: Point)   = Point ( x + p.x, y + p.y)
    def -    (p: Point)   = Vector(this,p)
    def dist (p: Point)   = (this - p).norm
    def /    (f: Float  ) = Point(x/f,y/f)
    override def toString = "(%.2f,%.2f)".format(x,y)
    override def equals(that: Any) = that.isInstanceOf[Point] && { 
	    val p = that.asInstanceOf[Point]
	    Point.cmp(x,p.x) && Point.cmp(y,p.y)
	}
}

class Vector(val p: Point, val q: Point) extends Point(q.x - p.x,q.y - p.y) {
	def this(x: Float, y: Float) = this(Point(),Point(x,y))
    def norm                     = Math.sqrt(x*x + y*y)
    def cross(p: Point)          = x*p.x + y*p.y
    def mid                      = (p + q)/2
}

object Point {
    val EPSILON                       = 0.001
    def apply(x: Float=0, y: Float=0) = new Point(x,y)
    def cmp  (x: Float  , y: Float=0) = Math.abs(x - y) < EPSILON
}

object Vector {
    def apply(x: Float=0, y: Float=0) = new Vector(x,y)
    def apply(p: Point  , q: Point  ) = new Vector(p,q)
}