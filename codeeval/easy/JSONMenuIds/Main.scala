import java.util.regex.Pattern

object Main extends App {
    val p = Pattern.compile("\"id\":\\s(\\d+),\\s\"label\"")
    scala.io.Source.fromFile(args(0)).getLines
    	.filter(!_.isEmpty)
        .map(line => {
            val m   = p.matcher(line)
            var sum = 0
            while (m.find) sum += m.group(1).toInt
            sum
        })
        .foreach(println)
}