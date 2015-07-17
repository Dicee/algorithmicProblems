package codeeval.moderate.Pangrams;

object Main extends App {
	val alphabet = (for (ch <- Range('a','z' + 1)) yield ch.toChar).toSet
	scala.io.Source.fromFile(args(0)).getLines
		.filter(!_.isEmpty)
		.map(line => {
			val cp = collection.mutable.Set() ++ alphabet
			for (ch <- line.toLowerCase) cp -= ch
			if (cp.isEmpty) "NULL" else cp.toSeq.sorted.mkString
		})
		.foreach(println)
}
