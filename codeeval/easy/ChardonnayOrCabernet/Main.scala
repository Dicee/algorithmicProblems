package codeeval.easy.ChardonnayOrCabernet

import scala.io.Source

object Main extends App {
    Source.fromFile(args(0))
          .getLines
          .map(_.split(" \\| "))
          .map { 
             case Array(winesString, fragment) => 
                 val wines = winesString.split(' ')
                 val counter = new Counter(fragment)                
                 wines.zip(wines.map(new Counter(_))).filter(counter <= _._2).map(_._1)                
          }
          .map(wines => if (wines.isEmpty) "False" else wines.mkString(" "))
          .foreach(println)
}

class Counter[T](iterable: Iterable[T]) {
    private val map = scala.collection.mutable.LinkedHashMap[T, Int]() 
    iterable.foreach(this += _)

    def +=(t: T) = map += t -> (map.getOrElse(t, 0) + 1)
    def <=(that: Counter[T]) = map.forall { case (k, v) =>
        that.map.get(k) match {
            case Some(n) => n >= v
            case _       => false
        }    
    }
}