package miscellaneous.utils.math

import scala.collection.Iterator

class PermGen(var n : Int, var reverse : Boolean = false) extends Iterable[Array[Int]] {
	private var generator : PermutationGenerator = new PermutationGenerator(n,reverse)
	
	def this(seed : String, reverse : Boolean) = {
		this(seed.length,reverse)
		generator = new PermutationGenerator(seed,reverse);
	}
	
	def this(seed : String, goal : String) = {
		this(seed.length)
		generator = new PermutationGenerator(seed,goal);
	}
	
	def iterator = new Iterator[Array[Int]] {
		private val it = generator.iterator
		def next    = it.next.map(_.toInt)
		def hasNext = it.hasNext
	}
}

object PermGen {
	def apply(n : Int, reverse : Boolean = false) = new PermGen(n,reverse)
	def apply(seed : String, reverse : Boolean)   = new PermGen(seed,reverse)
	def apply(seed : String)                      = new PermGen(seed,false)
	def apply(seed : String, goal : String)       = new PermGen(seed,goal)
}