import math._
import scala.util._
import scala.collection.mutable.ArrayBuffer

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
object Player {
    implicit def strToDir(s: String) = s match { case "LEFT" => Left case _ => Right }

    def main(args: Array[String]) {
        val Array(nFloors, width, rounds, exitFloor, exitX, nClones, nAddElevators, nElevators) = for(i <- readLine split " ") yield i.toInt
        val tmp = new ArrayBuffer[Pos](nElevators)
        val exit = Pos(exitX,exitFloor)
        
        for(i <- 0 until nElevators) {
            val Array(floor,x) = for(i <- readLine split " ") yield i.toInt
            tmp += Pos(x,floor)
        }
        
        val elevators = tmp.sortBy(_.floor)
        while(true) {
            val Array(floor,x,dir) = readLine split " "
            val pos = Pos(x.toInt,floor.toInt)
            var ans = "WAIT"
            
            if (pos.floor >= 0) {
                val goal = if (pos.floor < nFloors - 1) elevators(pos.floor) else exit
                if (dir.inc*(goal.x - pos.x) < 0) ans = "BLOCK" 
            }
            println(ans)
        }
    }
}

sealed abstract class Direction(val inc: Int)
final case object Left extends Direction(-1)
final case object Right extends Direction(1)

final case class Pos(x: Int, floor: Int)