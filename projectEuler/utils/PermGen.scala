package utils

import scala.collection.Iterator

class PermGen(var n : Int, var reverse : Boolean = false) extends Iterable[Array[Int]] {
	private var generator : PermutationGenerator = new PermutationGenerator(n,reverse)
	
	def this(seed : String, reverse : Boolean) = {
		this(seed.length,reverse)
		generator = new PermutationGenerator(seed,reverse);
	}
	
	def iterator = new Iterator[Array[Int]] {
		def next    = generator.iterator.next.map(_.toInt)
		def hasNext = generator.iterator.hasNext
	}
}

object PermGen {
	def apply(n : Int, reverse : Boolean = false) = new PermGen(n,reverse)
	def apply(seed : String, reverse : Boolean)   = new PermGen(seed,reverse)
	def apply(seed : String)                      = new PermGen(seed,false)
}