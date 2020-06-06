package interviewbit.Programming.Strings.Atoi;

import java.math.BigInteger;

public class Solution {
    public int atoi(String s) {
        boolean startedParsing = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            if (!startedParsing) {
                if (Character.isWhitespace(ch)) continue;
                if (isSignum(ch)) {
                    sb.append(ch);
                    startedParsing = true;
                    if (i == s.length() - 1 || !Character.isDigit(s.charAt(i + 1))) return 0;
                    continue;
                }
                if (!Character.isDigit(ch)) return 0;
            }

            startedParsing = true;
            if (Character.isDigit(ch)) sb.append(ch);
            else break;
        }

        BigInteger n = new BigInteger(sb.toString());
        if (n.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) > 0) return Integer.MAX_VALUE;
        if (n.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) < 0) return Integer.MIN_VALUE;
        return n.intValue();
    }

    private static boolean isSignum(char ch) {
        return ch == '+'  || ch == '-';
    }
}
