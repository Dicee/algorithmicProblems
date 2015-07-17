package codeeval.easy.UniqueElements;

object Main extends App {
    val source = scala.io.Source.fromFile(args(0))
    val lines = source.getLines.filter(!_.isEmpty).foreach(l => {
        val split = l.split(",")
        var i = 0
        while (i < split.length) {
            val s = split(i)
            print((if (i == 0) "" else ",") + s)
            while (i < split.length && split(i) == s) i += 1
        }   
        println()
    });
}