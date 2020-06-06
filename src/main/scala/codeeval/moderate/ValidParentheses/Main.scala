package codeeval.moderate.ValidParentheses;

object Main extends App {
    val corresponding = Map('(' -> ')','[' -> ']','{' -> '}')
    
    def isValid(s: String): String = {
        val stack = collection.mutable.Stack[Char]()
        for (ch <- s) ch match {
            case '(' | '[' | '{' => stack push ch
            case _               => 
                if (stack.isEmpty || corresponding.get(stack.pop).orElse(Some('.')).get != ch)
                	return "False"
        }
        return if (stack.isEmpty) "True" else "False"
    }
    
    scala.io.Source.fromFile(args(0)).getLines
    	.filter(!_.isEmpty)
        .map(isValid)
        .foreach(println)
}