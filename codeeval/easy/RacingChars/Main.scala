object Main extends App {
    var i = -1
    scala.io.Source.fromFile(args(0)).getLines.filter(!_.isEmpty)
        .map(line => {
            val checkPt  = line.indexOf("C")
            val gate     = line.indexOf("_")
            var (ch,rep) = ("","")
            if (i == -1) {
                rep = "|"                
                if (checkPt >= 0) { ch = "C"; i = checkPt } else { ch = "_"; i = gate }
            } else {
                var choice = -1
                if (checkPt >= 0) { ch = "C"; choice = checkPt } else { ch = "_"; choice = gate }
                rep = if (choice < i) "/" else if (choice == i) "|" else "\\"
                i   = choice
            }
            line.replace(ch,rep)
        })
        .foreach(println)
}