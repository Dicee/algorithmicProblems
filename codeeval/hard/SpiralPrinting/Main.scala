package codeeval.hard.SpiralPrinting;

object Main extends App {
	private type P = (Int,Int)
	def reachedGoal(coords: P, wBounds: P, hBounds: P, incs: P) = 
		if (incs._1 != 0) coords._2 == (if (incs._1 < 0) wBounds._1 else wBounds._2)
		else              coords._1 == (if (incs._2 < 0) hBounds._1 else hBounds._2)
		
	scala.io.Source.fromFile(args(0)).getLines
		.map(line => {
			val split       = line.split(";")
			val arr         = split(2).split("\\s")
			
			var (w,h)       = (split(1).toInt,split(0).toInt)
			var (winc,hinc) = (1,    0)
			var (wmin,wmax) = (0,w - 1)
			var (hmin,hmax) = (0,h - 1)
			
			val res         = scala.collection.mutable.Queue(arr(0))
			var (i,j)       = (0,0)
			
			while (res.length != arr.length) {
				i          += hinc
				j          += winc
				res        += arr(i*w + j)
				var reached = reachedGoal((i,j),(wmin,wmax),(hmin,hmax),(winc,hinc))
				if (reached) 
					if (winc != 0) {
						winc = 0
						hinc = if (i == hmin) 1 else -1
						if (i == hmax) hmax = scala.math.max(hmin,hmax - 1)
						else           hmin = scala.math.min(hmin + 1,hmax)
					} else {
						hinc = 0
						winc = if (j == wmin) 1 else -1
						if (j == wmax) wmax = scala.math.max(wmin,wmax - 1)
						else           wmin = scala.math.min(wmin + 1,wmax)
					}
			}
			res.mkString(" ")
		})
		.foreach(println)
}
