package codility.lessons.maximumSliceProblem;

/**
 * Level : respectable
 */
object Solution {
    def solution(A : Array[Int]) : Int = {
        // we apply a variant of the Kadane's algorithm to the slice 
        // of A consisting in A from which we remove both ends
        val B = A.slice(1,A.length - 1)
        if (B.indexWhere(_ > 0)  == -1) 
            return 0
                
        var sum     = 0
        var max     = 0
        var ignored = 0;
        
        for (i <- B) {
            if (i > 0)
                sum += i
            else if (ignored > i && sum + ignored > 0) {
                sum    += ignored
                ignored = i
            } else {
                sum     = 0
                ignored = 0
            }
            max = Math.max(max,sum)
        }
                
        return max
    }
}


