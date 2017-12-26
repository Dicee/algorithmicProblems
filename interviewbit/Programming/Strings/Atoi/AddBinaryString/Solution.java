package interviewbit.Programming.Strings.Atoi.AddBinaryString;

import java.math.BigInteger;

import static java.math.BigInteger.ZERO;

public class Solution {
    public String addBinary(String a, String b) {
        BigInteger n = parseBinary(a).add(parseBinary(b));

        BigInteger two = BigInteger.valueOf(2);
        StringBuilder sb = new StringBuilder();
        do {
            sb.append(n.mod(two));
            n = n.shiftRight(1);
        } while (n.compareTo(ZERO) > 0);
        return sb.reverse().toString();
    }

    // you call that cheating ? Well ok that's cheating, but it's simple :)
    private static BigInteger parseBinary(String s) {
        return new BigInteger(s, 2);
    }
}
