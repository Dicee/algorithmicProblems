package codeeval.easy.Football

import scala.io.Source

object Main extends App {
    def parseFanClubs(fanClubs: String, country: Int) = fanClubs.split("\\s+").map(_.toInt).map(FanClub(_, country))
    Source.fromFile(args(0))
          .getLines
          .map(_.split("\\s+\\|\\s+").zipWithIndex.flatMap { case (s, i) => parseFanClubs(s, i + 1)})
          .map(_.groupBy(_.club))
          .map(_.mapValues(_.map(_.country)))
          .map(_.toSeq.sortBy(_._1).map { case (k, v) =>  k + ":" + v.mkString(",") })
          .map(_.mkString("", "; ", ";"))
          .foreach(println)
}  

case class FanClub(club: Int, country: Int)