package codility.lessons.leader;

import java.util.*;

/**
 * Level : painless
 */
class Dominator {
    public int solution(int[] A) {
        Deque<Integer> deque = new LinkedList<>();
        int equalValues = 0;
        for (int i : A) {
            if (deque.isEmpty()) {
                deque.push(i);
                equalValues++;
            } else if (i == deque.peek()) 
                equalValues++;
             else  if (--equalValues == 0)
                deque.pop();            
        }
        
        if (deque.isEmpty())
            return -1;
        
        int candidate = deque.pop();
        int count     = 0;
        int index     = 0;
        for (int i=0 ; i<A.length ; i++) 
            if (A[i] == candidate) {
                count++;
                index = i;
            }
        
        return count > A.length/2 ? index : -1;
    }
}


