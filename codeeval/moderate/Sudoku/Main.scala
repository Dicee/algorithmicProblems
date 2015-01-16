package testouille

import scala.util.matching.Regex
import scala.collection.immutable.Stack

//import scala.collection.mutable.ArrayBuffer
//
//object Main extends App {
//    scala.io.Source.fromFile(args(0)).getLines
//    	.filter(!_.isEmpty)
//        .map(line => {
//            val nodes = line.split(",").map(s => new Node(s(0),s.last)).toSeq
//            val paths = collection.mutable.HashMap[Node,Int]()
//            for (node <- nodes) { paths += node -> 0; node.successors ++= nodes.filter(_.entry == node.exit) }
//            
//            nodes.map(_.successors)
//        })
//        .foreach(println)
//}
//
//class Node(val entry: Char, val exit: Char) {
//    var successors = ArrayBuffer[Node]()
//    override def toString = "(%s,%s)".format(entry,exit)
//}

object Main extends App {
    def getRow(grid: Array[Int], i: Int, n: Int) = for (j <- Range(0,n)) yield grid(i*n + j) 
    def getCol(grid: Array[Int], j: Int, n: Int) = for (i <- Range(0,n)) yield grid(i*n + j)
    
    def getSquare(grid: Array[Int], i: Int, j:Int, n: Int) = {
        val m = Math.sqrt(n).toInt
        for (k <- Range(i*m,(i + 1)*m) ; l <- Range(j*m,(j + 1)*m)) yield grid(k*n + l)
    }
   
    def isValid(it: Iterable[Int]) = { val set = it.toSet; set.size == it.size && set.forall(x => 1 <= x  && x <= it.size*it.size) }
    
    def checkGrid(grid: Array[Int], n: Int): String = {
        val m = Math.sqrt(n).toInt
        for (i <- Range(0,m) ; j <- Range(0,m)) 
           if (!isValid(getRow(grid,i*m + j,n)) || !isValid(getCol(grid,i*m + j,n)) || !isValid(getSquare(grid,i,j,n)))
                   return "False"
        return "True"
    }
        
    scala.io.Source.fromFile(args(0)).getLines
    	.filter(!_.isEmpty)
        .map(line => {
        	val grid = line.substring(line.indexOf(";") + 1).split(",").map(_.toInt)
        	val n    = Math.sqrt(grid.length).toInt
        	checkGrid(grid,n)
        })
        .foreach(println)
}