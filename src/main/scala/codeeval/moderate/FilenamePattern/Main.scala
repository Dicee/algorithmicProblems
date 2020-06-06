package codeeval.moderate.FilenamePattern;

import java.util.regex.Pattern
import scala.collection.mutable.ArrayBuffer

object Main extends App {
	val res = new ArrayBuffer[String]
	scala.io.Source.fromFile(args(0)).getLines
		.filter(!_.isEmpty)
		.map(line => {
			res.clear
			val split = line.split(" ")
			val regex = split(0).replaceAll("\\*","[^\\\\s]*").replaceAll("\\.","\\\\.").replaceAll("\\?","\\.")
			val p     = Pattern.compile(regex)
			for (i <- Range(1,split.length) ; if p.matcher(split(i)).matches) res += split(i).trim
			if (res.isEmpty) "-" else res.mkString(" ")
		})
		.foreach(println)
}
