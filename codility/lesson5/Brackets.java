package codility.lesson5;

import java.util.*;

/**
 * Level : painless
 */
class Brackets {
    public int solution(String S) {
        Deque<Character> deque = new LinkedList<>();
        Map<Character,Character> couples = new HashMap<>();
        couples.put(')','(');
        couples.put(']','[');
        couples.put('}','{');
        
        for (int i=0 ; i<S.length() ; i++) {
            char ch = S.charAt(i);
            if (ch == '(' || ch == '[' || ch == '{') 
                deque.push(ch);
            else {
                Character c = deque.peek();
                if (c == null || c != couples.get(ch))
                    return 0;
                deque.pop();
            }
        }
        return deque.isEmpty() ? 1 : 0;
    }
}
