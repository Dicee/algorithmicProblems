package codeeval.moderate.StringRotation;

object Main extends App {
    def findRotationIn(s: String, cmp: String, occurrences: Iterable[Int]): String = {
        val len = s.length
        for (i <- occurrences) {
            if (cmp.substring(i,len) == s.substring(0,len - i) && cmp.substring(0,i) == s.substring(len - i,len))
                return "True"
        }
        return "False"
    }
    
    scala.io.Source.fromFile(args(0)).getLines
    	.filter(!_.isEmpty)
        .map(line => {
            val split          = line.split(",")
            val firstLetterOcc = split(1).zipWithIndex.filter(_._1 == split(0)(0)).map(_._2)
            findRotationIn(split(0),split(1),firstLetterOcc)
        })
        .foreach(println)
}