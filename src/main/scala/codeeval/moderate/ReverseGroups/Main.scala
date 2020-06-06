package codeeval.moderate.ReverseGroups;

import scala.collection.mutable.ArrayBuffer

object Main extends App {
    scala.io.Source.fromFile(args(0)).getLines
    	.filter(!_.isEmpty)
        .map(line => {
            val arr = line.split("[,;]").map(_.toInt)
            val res = new ArrayBuffer[Int](line.length - 1)
            val n   = arr(arr.length - 1)
            val q   = (arr.length - 1) / n
            
            for (i <- 0 to q - 1) res ++= arr.slice(i*n,(i+1)*n).reverse
            res ++= arr.slice(q*n,arr.length - 1)
            res.mkString(",")
        })
        .foreach(println)
}