import java.util.*;

/**
 * Level : painless
 */
class PermMissingElements {
    public int solution(int[] A) {
        Set<Integer> set = new HashSet<>(A.length + 1);
        for (int i=1 ; i<=A.length+1 ; set.add(i++));
        for (int i=0 ; i< A.length   ; set.remove(A[i++]));
        return new ArrayList<>(set).get(0);
    }
}

