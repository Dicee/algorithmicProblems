package codility.lessons.maximumSliceProblem;

/**
 * Level : respectable
 */
object SolutionScala {
    def solution(A : Array[Int]) : Int = {
        val index = A.indexWhere(_ > 0) 
        
        if (index == -1) return A.max
                
        var sum = 0
        var max = 0
        
        for (i <- A.slice(index,A.length)) 
            if (sum + i > 0) {
                sum += i
                max  = Math.max(max,sum)
            } else
                sum = 0
                
        return max
    }
}


