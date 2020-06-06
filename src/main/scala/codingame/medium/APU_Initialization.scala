package codingame.medium

import java.util.Scanner
import scala.io.Source
import scala.collection.mutable.HashMap
import scala.collection.mutable.ArrayBuffer

object APU_Initialization extends App {
	val sc     = new Scanner(System.in)
	val width  = sc.nextInt
	val height = sc.nextInt
	sc.nextLine

	// contains j -> node mappings where node is the node in column j with
	// the largest line index so far
	val nodes       = ArrayBuffer[Node]()
	val nodesPerCol = HashMap[Int, Node]()
	
	for (line <- 0 until height) { 
		val nodesOnLine = sc.nextLine.zipWithIndex.filter(_._1 == '0').map(tuple => Node(tuple._2, line))
		var lastOnLine: Option[Node] = None
		for (node <- nodesOnLine) {
			nodes += node
			
			lastOnLine.foreach(_.right = node)
			lastOnLine = Some(node)
			
			nodesPerCol.get(node.x).foreach(_.bottom = node)
			nodesPerCol += node.x -> node
		}
	}
	println(nodes.map(_.answer).mkString("\n"))	
	
	case class Node(x: Int, y: Int) {
		var right : Node = NowhereNode
		var bottom: Node = NowhereNode
				
		override def toString = s"$x $y"
		def answer            = List(this, right, bottom).mkString(" ")	
	}
	object NowhereNode extends Node(-1, -1)
}