object Main extends App {
    scala.io.Source.fromFile(args(0)).getLines
    	.filter(!_.isEmpty)
        .map(line => {
            val arr   = line.split(",").map(_.toInt)
            val rect0 = new Rectangle(new Point(arr(0),arr(1)),new Point(arr(2),arr(3)))
            val rect1 = new Rectangle(new Point(arr(4),arr(5)),new Point(arr(6),arr(7)))
            if (rect0 intersects rect1) "True" else "False"
        })
        .foreach(println)
}

class Point(val x: Float = 0, val y: Float = 0)
class Rectangle(val tl: Point, val br: Point) {
    def topLeft     = tl
    def bottomRight = br
    def intersects(that: Rectangle) = {
        var (tl0,br0) = if (tl.x <= that.br.x) (that.tl,br) else (tl,that.br)
        var (tl1,br1) = if (tl.y <= that.tl.y) (tl,that.br) else (that.tl,br)
        tl0.x <= br0.x && br1.y <= tl1.y
    }
}