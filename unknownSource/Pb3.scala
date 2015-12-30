package unknownSource

object Pb3 {
    // arr(p) - p has to be maximal on arr[0:q] which means we only need to compute max(max(arr - [0:q]), arr[q] + q) for q in [0..n-1].
    def solution(arr: Array[Int]) = {
        var (maxSoFar, res) = (Int.MinValue, Int.MinValue)
            for (i <- 0 until arr.length) {
                maxSoFar = Math.max(maxSoFar, arr(i) - i)
                res  = Math.max(res, maxSoFar + arr(i) + i)
            }
            res
        }
}
