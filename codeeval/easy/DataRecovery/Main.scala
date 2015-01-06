object Main extends App {
    scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
        .map(line => {
            val split = line.split(";").map(_.split("\\s+"))
            val words = split(0).toSeq
            val res   = Array.ofDim[String ](words.length)
            val found = Array.ofDim[Boolean](words.length)
            for (t <- split(1).map(_.toInt).zip(words)) t match { case (pos,w) => res(pos - 1) = w; found(pos - 1) = true }
            res(res.zipWithIndex.find(_._1 == null).get._2) = words(words.length - 1)
            res.mkString(" ")
         })
         .foreach(println)
}