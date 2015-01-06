import scala.collection.mutable.Set
import scala.collection.mutable.TreeSet

object Main extends App {
    scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
    	.map(line => {
    	    var (set1,set2) = (new TreeSet[Int],new TreeSet[Int])
    	    val split       = line.split(";")
    	    set1 ++= split(0).split(",").map(_.toInt).toSet
    	    set2 ++= split(1).split(",").map(_.toInt).toSet
    	    (set1 & set2).mkString(",")
    	})
    	.foreach(println)
}