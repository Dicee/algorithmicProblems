package codeeval.moderate.StackImplementation;

object Main extends App {
    scala.io.Source.fromFile(args(0)).getLines
    	.filter(!_.isEmpty)
        .map(line => {
            val stack = new Stack[String]
        	line.split("\\s").foreach(stack push _)
        	var i  = 0
        	val sb = new StringBuilder
        	while (!stack.isEmpty) {
        	    val x = stack.pop
        	    if (i % 2 == 0) sb.append(x.value + " ")
        	    i += 1
        	}
            sb.toString.trim
        })
        .foreach(println)
}

class Stack[T] {
	var head: Option[Node[T]] = None
	def push(t: T) = head = Some(new Node[T](t,head))
	def pop        = {
	    head match {
	        case None    => throw new IllegalStateException("calling pop on an empty stack")
	        case Some(x) => head = x.next; x
	    }
	}
	def isEmpty = head == None
}

class Node[T](val value: T, val next: Option[Node[T]])