// Difficulty: medium. Since Interviewbit is aimed at preparing interviews, I try to do these in interview-like conditions. 
//             I find this one has enough edge cases to make it fairly challenging to get exactly right in 45-60 minutes.

// https://www.interviewbit.com/problems/regular-expression-ii/
public class RegularExpressionIIBacktracking {
    public static void main(String[] args) {
        System.out.println(isMatch("ac", "ab*c")); // 1
        System.out.println(isMatch("abcbcd", "a.*c.*d")); // 1
        System.out.println(isMatch("abbc", "ab*bbc")); // 1
        System.out.println(isMatch("efwihfioghih35i", ".*")); // 1
        System.out.println(isMatch("abc", "abcc*c*c*c*")); // 1
        System.out.println(isMatch("abc", "abcc*c*c*c")); // 0
    }

    public static int isMatch(String regexp, String toMatch) {
        return backtrack(toMatch, regexp, 0, 0) ? 1 : 0;
    }

    private static boolean backtrack(String regexp, String toMatch, int index, int currentMatch) {
        if (index >= regexp.length()) return currentMatch >= toMatch.length();

        boolean canRepeat = false;
        if (index + 1 < regexp.length()) {
            char nextChar = regexp.charAt(index + 1);
            canRepeat = nextChar == '*';
        }

        char ch = regexp.charAt(index);
        boolean characterMatches = currentMatch < toMatch.length() && (toMatch.charAt(currentMatch) == ch || ch == '.');

        if (canRepeat && backtrack(regexp, toMatch, index + 2, currentMatch)) return true;
        if (characterMatches) {
            if (canRepeat && backtrack(regexp, toMatch, index, currentMatch + 1)) return true;
            return backtrack(regexp, toMatch, index + 1, currentMatch + 1);
        }
        return false;
    }
}
