package miscellaneous.utils.math

class ScalaPermutation(private val perm: Permutation) extends Iterable[Int] {
	def apply(index: Int) = perm.get(index)
	
	def iterator = new Iterator[Int] {
		private val it = perm.iterator
		def hasNext = it.hasNext
		def next    = it.next
	}
}