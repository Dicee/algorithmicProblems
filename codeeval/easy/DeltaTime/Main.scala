import java.util.regex.Pattern

object Main extends App {
    val p = Pattern.compile("([0-9]{2}):([0-9]{2}):([0-9]{2})")
    scala.io.Source.fromFile(args(0)).getLines
    	.filter(!_.isEmpty)
    	.foreach(line => {
    	    val m      = p.matcher(line.trim)
    	    println((for (i <- 0 to 1 ; if m.find) yield Array(m.group(1),m.group(2),m.group(3)).map(_.toInt))
    	    	.toIndexedSeq.sortWith(sup).reduce((a,b) => a - b).map(i => (if (i < 10) "0" else "") + i).mkString(":"))
    	})
   
    	
    def sup(arr1: Array[Int], arr2: Array[Int]) =  {
       arr1.zipWithIndex.find { case (n,i) => n != arr2(i) } match {
           case Some((n,i)) => arr1(i) >= arr2(i)
           case None        => true
       }        
    }
    
    implicit class ExtendedArray(val arr: Array[Int]) {
        // assuming that (arr >= that) in this method
        def -(that: Array[Int]): Array[Int] = {
            val result = Array(0,0,0)
            for (i <- 2 to 0 by -1) {
                if (arr(i) >= that(i)) 
                    result(i) += arr(i) - that(i)
                else {
                    result(i)   += 60 + arr(i) - that(i)
                    result(i-1) -= 1
                }
            }
            result
        }
    }
}