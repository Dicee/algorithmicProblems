package codility.lessons.timeComplexity;

/**
 * Level : painless
 */
class TapeEquilibrium {
    public int solution(int[] A) {
        int lsum = 0;
        int rsum = A[A.length - 1];        
        
        for (int i=0 ; i<A.length - 1 ; lsum += A[i++]);
        int min = Math.abs(lsum - rsum);
        
        for (int i=1 ; i<A.length - 1 && min != 0 ; i++) {
            lsum -= A[A.length - 1 - i];
            rsum += A[A.length - 1 - i];
            min   = Math.min(min,Math.abs(lsum - rsum));
        }
        
        return min;
    }
}
