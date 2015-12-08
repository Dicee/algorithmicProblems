package codility.lesson13

/**
 * Level : painless
 */
object CountDistinctSlices {
    object Solution {
        private val MAX = 1000000000
        
        def sumInts(n: Long) = (n * (n + 1)) / 2
        
        def solution(m: Int, arr: Array[Int]): Int = {
            if (m == 0) return arr.length
            
            val set = Array.ofDim[Boolean](m + 1)
            var (res, i) = (0L, 0)
            
            for (j <- 0 until arr.length) {
                if (set(arr(j))) {
                    res += sumInts(j - i)
                    while (i < j && set(arr(j))) { 
                        set(arr(i)) = false 
                        i          += 1
                    }
                    res -= sumInts(j - i) 
                    if (res > MAX) return MAX
                }
                set(arr(j)) = true
            }
            Math.max(MAX, res + sumInts(arr.length - i)).toInt
        }
    }
    
    def main(args: Array[String]): Unit = {
        assert(Solution.solution(6, Array(3, 4, 5, 5, 2)) == 9)
        assert(Solution.solution(0, Array(0)) == 1)
        assert(Solution.solution(2, Array(1, 1, 1)) == 3)
        assert(Solution.solution(5, Array(5, 5, 5, 5)) == 4)
        assert(Solution.solution(5, Array(0, 1, 3, 3, 3, 1, 2)) == 13)
        assert(Solution.solution(5, Array(0, 1, 3)) == 6)
        assert(Solution.solution(5, Array(0, 1, 3, 1)) == 8)
        assert(Solution.solution(5, Array(0, 1, 3, 1, 2)) == 11)
        assert(Solution.solution(5, Array(0, 1, 3, 1, 2, 1)) == 13)
        assert(Solution.solution(5, Array(0, 1, 3, 1, 2, 1, 2)) == 15)
        assert(Solution.solution(5, Array(0, 1, 3, 1, 2, 1, 2, 3)) == 18)
    }
}