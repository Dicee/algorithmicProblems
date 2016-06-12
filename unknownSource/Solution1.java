package unknownSource;

public class Solution1 {
    public String solution(String s) {
        String onlyNumbers = s.replace("-", "").replace(" ", "");

        int length = onlyNumbers.length();
        boolean smallerLastTwoGroups = length % 3 == 1;

        StringBuilder sb = new StringBuilder(4 * length / 3);
        for (int i = 0; i < length; i++) {
            int remaining = length - i;

            boolean first = i == 0;
            boolean separate = i % 3 == 0 && remaining >= 2;
            boolean separateSpecialCase = smallerLastTwoGroups && remaining == 2;

            if (!first && (separate || separateSpecialCase)) sb.append("-");
            sb.append(onlyNumbers.charAt(i));
        }

        return sb.toString();
    }
}
