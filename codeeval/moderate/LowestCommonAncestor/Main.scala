package codeeval.moderate.LowestCommonAncestor;

object Main extends App {
    val bst = (new BinarySearchTree[Int]) += (30,8,52,3,20,10,29)
    scala.io.Source.fromFile(args(0)).getLines
    	.filter(!_.isEmpty)
        .map(line => {
            val split = line.split(" ").map(_.toInt).toSeq
            var node  = bst.root.get 
            var delta = split.map(_ - node.value)
            while (delta.product > 0) {
                node  = if (delta(0) < 0) node.left.get else node.right.get
                delta = split.map(_ - node.value)
            }
            node.value
        })
        .foreach(println)
}

class BinarySearchTree[T <% Ordered[T]] {
    var root: Option[Node] = None
    
    def +=(values: T*): BinarySearchTree[T] = { for (value <- values) this += value; this }
    
    def +=(value: T): BinarySearchTree[T] = {
        root match { case Some(x) => x += value; case None => root = Some(Node(value)) }
        this
    }
    
	override def toString = root match { case Some(x) => x.toString; case None => "." }
    
    case class Node(val value: T, var left: Option[Node] = None, var right: Option[Node] = None) {
        def +=(value: T): Unit = {
            var res: Node = null
            if (value < this.value) 
                if (left.isEmpty)   left       = Some(Node(value))
                else                left.get  += value
            else if (right.isEmpty) right      = Some(Node(value));
            else                    right.get += value
        }
        
        override def toString = toString("")
        private def toString(indent: String): String = {
            def childToString(child: Option[Node]) = 
                child match { case Some(x) => x.toString(indent + "\t"); case None    => indent + "\t." }
            "%s%d\n%s\n%s".format(indent,value,childToString(left),childToString(right))
        }
    }
}