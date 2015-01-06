object Main extends App {
    val ascii = Array(
    		"-**----*--***--***---*---****--**--****--**---**--",
    		"*--*--**-----*----*-*--*-*----*-------*-*--*-*--*-",
    		"*--*---*---**---**--****-***--***----*---**---***-",
    		"*--*---*--*-------*----*----*-*--*--*---*--*----*-",
    		"-**---***-****-***-----*-***---**---*----**---**--",
    		"--------------------------------------------------")
    		
    scala.io.Source.fromFile(args(0)).getLines
    	.filter(!_.isEmpty)
    	.foreach(line => {
    	    var filtered = line.filter(_.isDigit).split("").filter(!_.isEmpty).map(_.toInt)
    	    for (i <- Range(0,ascii.length)) {
    	    	for (j <- filtered) 
    	    	    print(ascii(i).substring(j*5,(j + 1)*5))
    	    	println()
    	    }
    	})
}