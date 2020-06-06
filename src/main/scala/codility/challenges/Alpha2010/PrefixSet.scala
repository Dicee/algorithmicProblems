/**
 * Level: painless
 */
object Solution {
    def solution(arr: Array[Int]) = {
        val set = Array.ofDim[Boolean](arr.length);
        var sol = 0
        for (i <- 0 until arr.length) {
            if (!set(arr(i))) {
                sol         = i
                set(arr(i)) = true
            }
        }
        sol
    }
}
