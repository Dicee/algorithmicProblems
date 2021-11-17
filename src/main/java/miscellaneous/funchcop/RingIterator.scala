package miscellaneous.funchcop

import java.util.LinkedList

import scala.jdk.CollectionConverters._

class RingIterator[+T](initialItems: T*) extends Iterator[T] {
	private[this] val items = new LinkedList[T]()
	items.addAll(initialItems.asJava)
	
	private[this] var it = items.iterator
	
 	override def hasNext = it.hasNext || { it = items.iterator; it.hasNext }
 	override def next()  = it.next()
 	
 	def remove() = it.remove()
 	
 	// use only if performance is not a concern, or if the number of elements is low
 	def elements = {
 		var elts = List[T]()
 		for (item <- items.asScala.reverseIterator) { elts = item :: elts }
 		elts
 	}
}