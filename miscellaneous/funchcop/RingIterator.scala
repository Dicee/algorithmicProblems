package miscellaneous.funchcop

import java.util.LinkedList
import scala.collection.JavaConversions._
import java.util.Collections.unmodifiableList

class RingIterator[+T](initialItems: T*) extends Iterator[T] {
	private[this] var items = new LinkedList[T]()
	items.addAll(asJavaCollection(initialItems))
	
	private[this] var it = items.iterator
	
 	override def hasNext = it.hasNext || { it = items.iterator; it.hasNext }
 	override def next    = it.next()
 	
 	def remove() = it.remove()
 	
 	// use only if performance is not a concern, or if the number of elements is low
 	def elements = {
 		var elts = List[T]()
 		for (item <- items.reverseIterator) { elts = item :: elts }
 		elts
 	}
}