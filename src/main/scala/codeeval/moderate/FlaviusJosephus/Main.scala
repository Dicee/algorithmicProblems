package codeeval.moderate.FlaviusJosephus;

import scala.collection.mutable.ArrayBuffer
object Main extends App {
    scala.io.Source.fromFile(args(0)).getLines
    	.filter(!_.isEmpty)
        .map(line => {
            val split = line.split(",").map(_.toInt)
            val alive = Array.fill(split(0))(true)
            val res   = new ArrayBuffer[Int](split(0))
            
            var i     = -1
            while (res.length != split(0)) {
                var count = 0
                while (count < split(1)) {
                	i = (i + 1) % split(0)
                    if (alive(i)) count += 1
                }
                alive(i) = false
                res += i
            }
            res.mkString(" ")
        })
        .foreach(println)
}