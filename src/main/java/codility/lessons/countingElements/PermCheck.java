package codility.lessons.countingElements;

import java.util.*;

/**
 * Level : painless
 */
class PermCheck {
    public int solution(int[] A) {
        Set<Integer> set = new HashSet<>();
        for (int i : A) {
            if (i < 1 || i > A.length || !set.add(i))
                return 0;
        }
        return 1;
    }
}
