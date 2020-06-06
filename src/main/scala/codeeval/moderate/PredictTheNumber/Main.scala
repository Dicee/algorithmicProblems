package codeeval.moderate.PredictTheNumber

object Main extends App {
    scala.io.Source.fromFile(args(0))
        .getLines()
        .map(_.toLong)
        .map(n => {
            var powerOfTwo = closestUpperPowerOfTwo(n)
            var (res, m)   = (0, n + 1)

            while (powerOfTwo > 1) {
                var nextPower = powerOfTwo / 2
                if (m > nextPower) {
                    res = (res + 1) % 3
                    m  -= nextPower
                }
                powerOfTwo = nextPower
            }
            res
        })
        .foreach(println)

    def closestUpperPowerOfTwo(n: Long) = {
        var (m, pow) = (n, 0)
        while (m > 0) {
            pow += 1
            m  >>= 1
        }
        1L << pow
    }
}