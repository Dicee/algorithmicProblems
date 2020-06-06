package interviewbit.Programming.Arrays.SpiralOrderMatrixII;

import java.util.ArrayList;
import java.util.List;

/**
 * Trivial if you have already solved Spiral Order Matrix I, otherwise same difficulty, it's a purely equivalent problem.
 */
public class Solution {
    public List<List<Integer>> generateMatrix(int n) {
        List<List<Integer>> result = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            result.add(new ArrayList<>(n));
            for (int j = 0; j < n; j++) {
                result.get(i).add(-1);
            }
        }

        int[] pos   = { 0, 0 };
        int[] delta = { 1, 0 };
        int[] min   = { 0, 0 };
        int[] max   = { n - 1, n - 1 };

        int movingCoord = 0;
        int count       = 1;

        while (count <= n * n) {
            while (min[movingCoord] <= pos[movingCoord] && pos[movingCoord] <= max[movingCoord]) {
                result.get(pos[1]).set(pos[0], count++);
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
