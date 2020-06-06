package unknownSource;

import java.util.HashSet;
import java.util.Set;

public class Solution3 {
    // Note: the longest path from root to leaf is 3500. In Java, you are allowed something between
    //       5000 and 7000 recursive calls before it blows up. I'll go for a recursive solution.
    public int solution(Tree tree) {
        return recSol(tree, new HashSet<>());
    }

    private static int recSol(Tree tree, Set<Integer> distinctValues) {
        if (tree == null) return distinctValues.size();

        boolean added = distinctValues.add(tree.x);
        int sol = Math.max(recSol(tree.l, distinctValues), recSol(tree.r, distinctValues));

        // clean the state, being careful not to remove a value added higher up in the tree
        if (added) distinctValues.remove(tree.x);

        return sol;
    }

    private static class Tree {
        public int x;
        public Tree l;
        public Tree r;
    }
}