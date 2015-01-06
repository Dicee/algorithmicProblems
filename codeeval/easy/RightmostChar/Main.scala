object Main extends App {
  val source = scala.io.Source.fromFile(args(0))
  val lines = source.getLines.filter(!_.isEmpty).foreach(l => {
    val split = l.split(",");
    println(split(0).lastIndexOf(split(1)))
  });
}