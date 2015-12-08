package codility.lesson13

/**
 * Level : painless
 */
object AbsDistinctNew {
    object Solution {
        import Math.abs
        
        def solution(arr: Array[Int]): Int = {
            var (i, j)      = (0, arr.length - 1)
            var distinct    = 0
            val absoluteArr = arr.map(_.toLong).map(abs(_))
            
            while (i <= j) {
                val (x, y) = (absoluteArr(i), absoluteArr(j))
                if (x >= y) do { i += 1 } while (i <= j && absoluteArr(i) == x) 
                if (x <= y) do { j -= 1 } while (j >= i && absoluteArr(j) == y)
                distinct += 1
            }
            return distinct
        }
    }
    
    def main(args: Array[String]): Unit = {
        assert(Solution.solution(Array(-5, -3, -1, 0, 3, 6)) == 5)
        assert(Solution.solution(Array(-5, -3, -2, -1, 2, 3, 6, 7)) == 6)
        assert(Solution.solution(Array(-5, -2)) == 2)
        assert(Solution.solution(Array(-5, 0)) == 2)
        assert(Solution.solution(Array(-5, 5)) == 1)
        assert(Solution.solution(Array(-5, 8)) == 2)
        assert(Solution.solution(Array(-5, -5, -5, -5)) == 1)
    }
}