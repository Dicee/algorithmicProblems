package codeeval.hard.WordSearch

import scala.io.Source
import scala.collection.mutable.HashSet

object Main extends App {
    val boardString = "ABCE\nSFCS\nADEE"
    val board       = boardString.split("\n").map(_.toArray)
    Source.fromFile(args(0))
          .getLines
          .map(line => { 
              val word  = line.toArray
              val cells = for (i <- 0 until board.length ; j <- 0 until board(0).length) yield Cell(i, j)
              if (cells.exists(findWord(board, _, word))) "True" else "False" 
          })
         .foreach(println)
    
    def findWord(board: Array[Array[Char]], start: Cell, word: Array[Char]): Boolean = {
        def recSol(path: Path, index: Int = 0): Boolean = {
            if (path.current.value(board) != word(index)) return false
            if (index >= word.length - 1)                 return true                         
            val neighbours = path.current.neighboursEqualTo(board, word(index + 1))
            for (neighbour <- neighbours) {
                if (!path.contains(neighbour)) {
                    if (recSol(path.forward(neighbour), index + 1)) return true
                    path.backward()
                }
            }
            return false
        }
        recSol(Path(start))            
    }     
}

/*
 * Not necessary for this problem but it makes a nicer code to enclose the logic.
 */
class Path {
    private val explored = scala.collection.mutable.HashSet[Cell]()
    private var path: List[Cell] = Nil 
    def contains(cell: Cell) = explored.contains(cell)
    def forward (cell: Cell) = { explored += cell; path = cell :: path; this }
    def backward()           = { explored -= path.head; path = path.tail; this }
    def current              = path.head
}
object Path {
    def apply(start: Cell) = new Path forward start
}

case class Cell(i: Int, j: Int) {
    private def isSafe(i: Int, min: Int, max: Int) = min <= i && i < max
     def neighbours(board: Array[Array[Char]]) =
         for { 
            dx <- List(-1, 0, 1); 
            dy <- List(-1, 0, 1); 
            if dx == 0 ^ dy == 0;
            if isSafe(i + dx, 0, board   .length);
            if isSafe(j + dy, 0, board(0).length)
        } yield Cell(i + dx, j + dy)

    override lazy val hashCode = i.hashCode + 31*j.hashCode
    override def equals(a: Any) = a match {
        case Cell(k, l) => i == k && j == l
        case _          => false
    }
    
    def neighboursEqualTo(board: Array[Array[Char]], ch: Char) = neighbours(board).filter(cell => cell.value(board) == ch)
    def value(board: Array[Array[Char]]) = board(i)(j)
}