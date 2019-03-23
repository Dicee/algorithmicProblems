package hackerrank.interviewPreparationKit.dictionariesAndHashMaps.countTriplets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Difficulty: medium. Some thought required to figure out what information to store to maintain a linear complexity.

// https://www.hackerrank.com/challenges/count-triplets-1/problem?h_l=interview&playlist_slugs%5B%5D=interview-preparation-kit&playlist_slugs%5B%5D=dictionaries-hashmaps
public class Solution {
    public static long countTriplets(List<Long> values, long r) {
        Map<Long, Integer> countOccurrences = new HashMap<>();

        // (k, v) represents the number v of valid pairs encountered so far for which the middle element is k
        Map<Long, Long> countMatchingPairs = new HashMap<>();
        long count = 0;

        for(int i = 0; i < values.size(); i++) {
            long value = values.get(i);

            if (value % r == 0) {
                long leftMatches = countOccurrences.getOrDefault(value / r, 0);
                count += countMatchingPairs.getOrDefault(value / r, 0L);
                countMatchingPairs.merge(value, leftMatches, (a, b) -> a + b);
            }

            countOccurrences.merge(value, 1, (a, b) -> a + b);
        }
        return count;
    }
}
