package codeeval.easy.RollerCoaster;

object Main extends App {
    val cases = Array((ch: Char) => ch.toUpper,(ch: Char) => ch.toLower)
    
    scala.io.Source.fromFile(args(0)).getLines
    	.filter(!_.isEmpty)
    	.foreach(line => {
    		var useCase = 0
    	    println(line.map { 
    	        case x if !x.isLetter => x
    	        case x                => useCase = (useCase + 1) % 2; cases((useCase + 1) % 2)(x)
    	    }.mkString)
    	})
}