/**
 * Level : painless
 */
class Solution {
    public int solution(int N) {
        boolean counting = false;
        int     max      = 0;
        int     count    = 0;
        int     last     = -1;
        while (N > 0) {
            int digit = N % 2;
            
            if (digit == 1) {
                max   = Math.max(max,count);
                count = 0;
            } else if (count > 0 || last == 1)
                count++;
            
            N   /= 2;  
            last = digit;
        }
        return max;
    }
}


