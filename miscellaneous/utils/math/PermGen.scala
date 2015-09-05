package miscellaneous.utils.math

import scala.collection.Iterator

class PermGen(var perm: Permutation, var reverse : Boolean = false) extends Iterable[ScalaPermutation] {
	private var generator : PermutationGenerator = new PermutationGenerator(perm, reverse)
	
	def iterator = new Iterator[ScalaPermutation] {
		private val it = generator.iterator
		def next    = new ScalaPermutation(it.next)
		def hasNext = it.hasNext
	}
}

object PermGen {
	def apply(seed : String) = new PermGen(Permutation.fromDigits(seed), false)
}