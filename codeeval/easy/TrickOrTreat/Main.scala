package codeeval.easy.TrickOrTreat

import scala.io.Source

object Main extends App {
    def parseTuple(s: String) = { val split = s.split(":").map(_.trim); (split(0), split(1).toInt) }
    val CANDIES_FOR_COSTUME = Map("Vampires" -> 3, "Zombies" -> 4, "Witches" -> 5)
    Source.fromFile(args(0))
          .getLines
          .map(_.split(",").map(parseTuple).toMap)
          .foreach { children => 
              val houses     = children("Houses")
              val totalCount = (children - "Houses").map { case (k, v) => (houses * v * CANDIES_FOR_COSTUME(k), v) }
                                                    .reduce((t1, t2) => (t1._1 + t2._1, t1._2 + t2._2))
              println((totalCount._1 / totalCount._2.toDouble).toInt)                                                                                            
          }
}