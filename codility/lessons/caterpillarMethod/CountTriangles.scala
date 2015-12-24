package codility.lessons.caterpillarMethod

/**
 * Level : painless
 */
object CountTriangles {
    object Solution {
        def solution(arr: Array[Int]): Int = {
            val sortedArr = arr.sorted
            
            var count = 0
            for (i <- 0 until arr.length - 2) {
                var (j, k)   = (i + 1, i + 2)
                var subcount = 0
                while (k < sortedArr.length) {
                    if (sortedArr(i) + sortedArr(j) > sortedArr(k)) { 
                        subcount = if (subcount == 0) 1 else (subcount * (k - j + 1)) / (k - j - 1) 
                        k       += 1
                    } else {
                        if (j == k - 1) k += 1
                        j       += 1
                        count   += subcount
                        subcount = 0
                    }
                } 
                count += subcount
            }
            count 
        }
    }
    
    def coeff(n: Int) = {
        var subcount = 0
        for (i <- 1 to n) {
            subcount = if (subcount == 0) 1 else (subcount * (i + 1)) / (i - 1)
            println(subcount)
        }
        subcount
    }
    
    def main(args: Array[String]): Unit = {
        assert(Solution.solution(Array(10, 2, 5, 1, 8, 12)) == 4)        
        coeff(10)
    }
}