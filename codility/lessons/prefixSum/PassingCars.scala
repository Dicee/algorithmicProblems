package codility.lessons.prefixSum

/**
 * Level : painless
 */
object PassingCarsScala {
    object Solution {
        private val MAX = 1000000000
        
        def solution(arr: Array[Int]): Int = {
            var (eastCars, passingCars) = (0, 0)
            for (car <- arr) {
                if (car == 0) eastCars += 1
                else  { 
                    passingCars += eastCars
                    if (passingCars > MAX) return -1
                }
            }
            passingCars 
        }
    }
}