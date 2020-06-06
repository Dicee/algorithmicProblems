package interviewbit.Programming.Arrays.SpiralOrderMatrixI;

import java.util.ArrayList;
import java.util.List;

/**
 * Classical and not too hard, but still took me longer than I wanted.
 */
public class Solution {
    public List<Integer> spiralOrder(final List<List<Integer>> a) {
        List<Integer> result = new ArrayList<>();
        if (a.isEmpty()) return result;

        int[] pos   = { 0, 0 };
        int[] delta = { 1, 0 };
        int[] min   = { 0, 0 };
        int[] max   = { a.get(0).size() - 1, a.size() - 1 };

        int movingCoord = 0;

        while (result.size() < a.size() * a.get(0).size()) {
            while (min[movingCoord] <= pos[movingCoord] && pos[movingCoord] <= max[movingCoord]) {
                result.add(a.get(pos[1]).get(pos[0]));
                pos[movingCoord] += delta[movingCoord];
            }

            pos[movingCoord] = pos[movingCoord] > max[movingCoord] ? max[movingCoord] : min[movingCoord];
            delta[movingCoord] = 0;
            movingCoord = (movingCoord + 1) % 2;

            if (pos[movingCoord] == min[movingCoord]) {
                delta[movingCoord] = 1;
                pos[movingCoord] = min[movingCoord] += delta[movingCoord];
            } else {
                delta[movingCoord] = -1;
                pos[movingCoord] = max[movingCoord] += delta[movingCoord];
            }
        }
        return result;
    }
}

