// Difficulty: medium. Since Interviewbit is aimed at preparing interviews, I try to do these in interview-like conditions. 
//             I find this one has enough edge cases to make it fairly challenging to get exactly right in 45-60 minutes.

// https://www.interviewbit.com/problems/regular-expression-ii/
public class RegularExpressionIIBacktracking {
    public int isMatch(String regexp, String toMatch) {
        return backtrack(toMatch, regexp, 0, 0) ? 1 : 0;
    }

    private static boolean backtrack(String regexp, String toMatch, int index, int currentMatch) {
        if (index >= regexp.length()) return currentMatch >= toMatch.length();

        boolean canRepeat = false;
        if (index + 1 < regexp.length()) {
            char nextChar = regexp.charAt(index + 1);
            canRepeat = nextChar == '*';
        }

        if (currentMatch >= toMatch.length()) return canRepeat;

        char ch = regexp.charAt(index);
        boolean characterMatches = toMatch.charAt(currentMatch) == ch || ch == '.';

        if (characterMatches) {
            if (canRepeat) {
                return backtrack(regexp, toMatch, index, currentMatch + 1) ||
                       backtrack(regexp, toMatch, index + 2, currentMatch);
            }
            return backtrack(regexp, toMatch, index + 1, currentMatch + 1);
        }
        return canRepeat && backtrack(regexp, toMatch, index + 2, currentMatch);
    }
}
