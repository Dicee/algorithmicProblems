package codeeval.moderate.LostInTranslation;

object Main extends App {
    val s0     = "rbc vjnmkf kd yxyqci na rbc zjkfoscdd ew rbc ujllmcp thg"
    val s1     = "the public is amazed by the quickness of the juggler wxv"
    val decode = collection.mutable.HashMap[Char,Char]()
    for (i <- Range(0,s0.length)) decode += s0(i) -> s1(i)
    
    scala.io.Source.fromFile(args(0)).getLines
    	.filter(!_.isEmpty)
        .map(line => line.map(decode))
        .foreach(println)
}