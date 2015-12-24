package codeeval.easy.TimeToEat

import scala.io.Source

object Main extends App {
    Source.fromFile(args(0))
          .getLines
          .map(_.split(" ").map(Time(_)).sorted(Ordering[Time].reverse).toList)
          .foreach(list => println(list.mkString(" ")))
}

case class Time(hourOfDay: Int, minuteOfHour: Int, secondOfMinute: Int) extends Ordered[Time] {
    override def compare(that: Time) = 
        List(hourOfDay      - that.hourOfDay, 
             minuteOfHour   - that.minuteOfHour, 
             secondOfMinute - that.secondOfMinute).find(_ != 0).getOrElse(0)
             
    override def toString = "%02d:%02d:%02d".format(hourOfDay, minuteOfHour, secondOfMinute)
}
object Time {
    private val FORMAT = "(\\d{2}):(\\d{2}):(\\d{2})"r
    def apply(timestamp: String): Time = (FORMAT.unapplySeq(timestamp): @unchecked) match  {
        case Some(hh :: mm :: ss :: Nil) => Time(hh.toInt, mm.toInt, ss.toInt)
    }
}