object Main extends App {
    val source = scala.io.Source.fromFile(args(0))
    val lines = source.getLines.filter(!_.isEmpty).foreach(l => {
        val split = l.split(",").map(_.toInt);
        var (n,m) = (split(0),split(1))        
        while (n >= m) n -= m;
        println(n)
    });
}