import java.util.regex.Pattern
import scala.collection.mutable.TreeSet

object Main extends App {
    val p = Pattern.compile("\\d+")
    scala.io.Source.fromFile(args(0)).getLines
    	.filter(!_.isEmpty)
        .map(line => {
            val dists = new TreeSet[Int]
            val m     = p.matcher(line)
            while (m.find) dists += m.group.toInt

            val list  = dists.toList
            list.zip(0 +: list).map { case (x,y) => x - y }.mkString(",")
        })
        .foreach(println)
}