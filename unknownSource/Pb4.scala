package miscellaneous.unknownSource

object Pb4 extends App {
    /**  
     *  s(p, q) = arr(p) + arr(q) + p - q
     *      To maximise s(p, q), we want arr(p) and arr(q) as big as possible and p - q as close as possible to 0 (since it is negative).
     *      Both conditions are clearly reunited if p = q = index_max(arr)
     *      
     *  s(p, q) = arr(p) + arr(q) + q - p
     *      To maximise s(p, q), we want arr(p) and arr(q) as big as possible and q - p as big as possible.
     */
    def solution(arr: Array[Int]) = {
        var (maxSoFar, res) = (Int.MinValue, Int.MinValue)
        for (i <- 0 until arr.length) {
            maxSoFar = Math.max(maxSoFar, arr(i) - i)
            res      = Math.max(res, maxSoFar + arr(i) + i)
        } 
        res
    }
    
    private def test(arr: Array[Int], expected: Int) = {
        val sol = solution(arr)
        println(if (sol == expected) "Test passed" else s"For input ${arr.toList}, expected ${expected} but got ${sol}")
    }
    
    test(Array(2), 4)
    test(Array(0, 1, -4, -4), 2)
    test(Array(0, 2, -4, -4, -4, 5, -4), 11)
    test(Array(0, 2, -4, -4, -4, 7, -4), 14)
    test(Array(3, 1, 0, 0, 0, 0, 0, 2, 3), 14)    
    test(Array(2, 1, 1, 2, -6), 7)    
}