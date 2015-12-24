package codeeval.easy.BlackCard

object Main extends App {
    implicit class EnhancedInt(n: Int) {
        def %%(m: Int) = {
            val mod = n % m
            if (mod >= 0) mod else m - mod 
        }
    }
    
    def survivor(n: Int, m: Int) = {
        if (n == 1) 0 
        else {
            var sol = (m - 2) %% 2
            for (i <- 3 to n) {
                val eliminated = (m - 1) %% i
                if (sol >= eliminated) sol += 1
            }
            sol
        }
    }
    
    scala.io.Source.fromFile(args(0)).getLines.foreach { line => 
        val split = line.split('|').map(_.trim)
        val names = split(0).split(" ")
        val sol   = survivor(names.length, split(1).toInt)
        println(names(sol))        
    }
}