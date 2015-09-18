package codingame.medium


import java.util.Scanner
import scala.collection.mutable.HashMap
import scala.collection.mutable.HashSet
import scala.collection.mutable.Set
import java.util.LinkedList
import scala.collection.mutable.ArrayBuffer

object TeadsChallenge extends App {
	val sc = new Scanner(System.in)
	val n  = sc.nextInt
	sc.nextLine
	
	val graph = HashMap[Int, Node]()
	for(i <- 0 until n) {
		val indices = sc.nextLine.split(" ").map(_.toInt)
		graph.getOrElseUpdate(indices(0), new Node) >> graph.getOrElseUpdate(indices(1), new Node)
	}
	
	def totalDepth(node: Node, parent: Node): Int = 
		Math.max(depth(node, parent, _.inputs), depth(node, parent, _.outputs))
	def depth(node: Node, parent: Node, children: Node => Set[Node]) = 
		1 + (children(node) - parent).map(totalDepth(_, node)).foldLeft(0)((a, b) => Math.max(a, b))	
		
	val root       = graph.valuesIterator.next
	val longerPath = depth(root, root, _.inputs) + depth(root, root, _.outputs) - 1
	
	println(longerPath / 2)
}

class Node {
	val inputs  = HashSet[Node]()
	val outputs = HashSet[Node]()
	def >>(that: Node) = { outputs += that; that.inputs += this }
}