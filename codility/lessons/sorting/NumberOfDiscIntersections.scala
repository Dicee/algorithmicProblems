package codility.lessons.sorting

/**
 * Level : respectable
 */
object NumberOfDiscIntersections {
    object Solution {
        def solution(arr: Array[Int]): Int = {
            val zippedWithIndex = arr.map(_.toLong).zipWithIndex
            val lowerEnds       = zippedWithIndex.map(x => x._2 - x._1).sorted
            val upperEnds       = zippedWithIndex.map(x => x._2 + x._1).sorted
            
            var (i       , j)      = (0, 0)
            var (overlaps, result) = (0, 0)
            for (k <- 0 until arr.length) {
                while (i < arr.length && lowerEnds(i) <= k) { 
                    i        += 1
                    result   += overlaps 
                    overlaps += 1
                    if (result > 10000000) return -1
                } 
                while (j < arr.length && upperEnds(j) <= k) { j += 1; overlaps -= 1 }
            }
            result
        }
    }
    
    def main(args: Array[String]) {
        assert(Solution.solution(Array(1, 5, 2, 1, 4, 0)) == 11)
    }
}