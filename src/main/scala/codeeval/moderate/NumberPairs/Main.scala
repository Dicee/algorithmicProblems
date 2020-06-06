package codeeval.moderate.NumberPairs;

import java.util.Arrays

object Main extends App {
	scala.io.Source.fromFile(args(0)).getLines
		.filter(!_.isEmpty)
		.map(line => {
			val split = line.split(";")
			val n     = split(1).toInt
			val arr   = split(0).split(",").map(_.toInt)

			val res   = 
				for ((x,i) <- arr.takeWhile(_ < arr.last).zipWithIndex ;
				 	 index = Arrays.binarySearch(arr,n - x)
				 	 if index > 0 && index < arr.length && i < index)
				yield (x + "," + arr(index))
				
			if (res.isEmpty) "NULL" else res.mkString(";")
		})
		.foreach(println)
}
