package interviewbit.Programming.DynamicProgramming;

// Difficulty: easy. My solution isn't optimal as I simply tried to solve it as quickly as possible
//             in interview-like conditions (no compiler, no execution). Could use DP to make it more
//             efficient.

// https://www.interviewbit.com/problems/interleaving-strings/
public class InterleavingStrings {
    public int isInterleave(String s1, String s2, String target) {
            return backtrack(s1, s2, 0, 0, target) ? 1 : 0;
        }

    private boolean backtrack(String s1, String s2,  int i1, int i2, String target) {
        int i = i1 + i2;
        if (i > target.length()) return false;
        if (i == target.length()) return i1 == s1.length() && i2 == s2.length();

        boolean isSuitable1 = i1 < s1.length() && s1.charAt(i1) == target.charAt(i);
        boolean isSuitable2 = i2 < s2.length() && s2.charAt(i2) == target.charAt(i);

        if (isSuitable1 && backtrack(s1, s2, i1 + 1, i2    , target)) return true;
        if (isSuitable2 && backtrack(s1, s2, i1    , i2 + 1, target)) return true;

        return false;
    }
}
