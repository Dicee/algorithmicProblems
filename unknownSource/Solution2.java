package unknownSource;

import java.util.HashSet;
import java.util.Set;

public class Solution2 {
    public int solution(int[] weights, int[] targetFloors, int lastFloor, int maxCapacity, int maxWeight) {
        int i = 0, stops = 0;

        while (i < weights.length) {
            int usedCapacity = 0, currentWeight = 0;
            Set<Integer> distinctFloors = new HashSet<>();

            // fill the elevator
            while (i < weights.length && usedCapacity < maxCapacity && currentWeight + weights[i] <= maxWeight) {
                if (distinctFloors.add(targetFloors[i])) stops++;
                currentWeight += weights[i++];
                usedCapacity++;
            }

            // account for the last stop to ground floor
            stops++;
        }

        return stops;
    }
}