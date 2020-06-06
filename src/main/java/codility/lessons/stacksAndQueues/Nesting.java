package codility.lessons.stacksAndQueues;

import java.util.*;

/**
 * Level : painless
 */
class Nesting {
    public int solution(String S) {
        Deque<Character> deque = new LinkedList<>();
        
        for (int i=0 ; i<S.length() ; i++) {
            char ch = S.charAt(i);
            if (ch == '(') 
                deque.push(ch);
            else {
                if (deque.isEmpty())
                    return 0;
                deque.pop();
            }
        }
        return deque.isEmpty() ? 1 : 0;
    }
}
