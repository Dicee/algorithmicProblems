package codeeval.easy.RomanNumerals;

object Main extends App {
    val num = Array("I","V","X","L","C","D","M")
	scala.io.Source.fromFile(args(0)).getLines
    	.map(line => {
    	    val sb = new StringBuilder
    	    var pt = 0
    		for (i <- line.length-1 to 0 by -1) {
    		    line(i) - '0' match {
    		        case x if x < 4 => sb.append(num(pt    )* x     );
    		        case 4          => sb.append(num(pt + 1)        ); sb.append(num(pt    ))
    		        case x if x < 9 => sb.append(num(pt    )*(x - 5)); sb.append(num(pt + 1))
    		        case 9          => sb.append(num(pt + 2)        ); sb.append(num(pt    )) 
    		    }
    		    pt += 2
    		}
    	    sb.reverse
    	})
    	.foreach(println)
}