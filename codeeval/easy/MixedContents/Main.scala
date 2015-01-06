import scala.collection.mutable.Queue

object Main extends App {
	scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
    	.foreach(line => {
    		val (digits,words) = (new Queue[String](),new Queue[String]())
    	    line.split(",").foreach(x => (if (x.matches("\\d+")) digits  else words) += x)
    	    println("%s%s%s".format(words.mkString(","),if (digits.isEmpty || words.isEmpty) "" else "|",digits.mkString(",")))
    	})
}