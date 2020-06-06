package hackerrank.algorithms.search.connectedCellsInAGrid;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.IntStream;

// Difficulty: classic flood-fill, so not too hard.

// https://www.hackerrank.com/challenges/connected-cell-in-a-grid/problem
public class Solution {
    private static int connectedCell(int[][] matrix) {
        Set<Point> explored = new HashSet<>();

        // in theory the product of two ints nxm can be bigger than Integer.MAX_VALUE but in our case they're both lower than 10 so an int is sufficient
        int max = Integer.MIN_VALUE;

        // ArrayDeque is typically faster than LinkedList as a Queue. I'm re-using a single queue
        // in order to reduce the likelihood that we need to dynamically grow the backing array
        // to fit new elements in later iterations
        Queue<Point> toExplore = new ArrayDeque<>();

        for (int i = 0; i < matrix.length - 1; i++) {
            for(int j = 0; j < matrix[0].length - 1; j++) {
                int count = floodFill(matrix, explored, toExplore, new Point(i, j));
                max = Math.max(max, count);
                toExplore.clear();
            }
        }
        return max;
    }

    private static int floodFill(int[][] matrix, Set<Point> explored, Queue<Point> toExplore, Point origin) {
        if (!shouldExplore(matrix, explored, origin)) return 0;

        int count = 0;
        toExplore.add(origin);
        explored.add(origin);

        while (!toExplore.isEmpty()) {
            count++;

            Point point = toExplore.remove();
            IntStream.rangeClosed(-1, 1)
                     .boxed()
                     .flatMap(di -> IntStream.rangeClosed(-1, 1).mapToObj(dj -> new Point(point.i + di, point.j + dj)))
                     .filter(pt -> pt.i != point.i || pt.j != point.j)
                     .filter(pt -> pt.i >= 0 && pt.j >= 0 && pt.i < matrix.length && pt.j < matrix[0].length)
                     .filter(pt -> shouldExplore(matrix, explored, pt))
                     .forEach(toExplore::add);
        }
        return count;
    }

    private static boolean shouldExplore(int[][] matrix, Set<Point> explored, Point point) {
        return matrix[point.i][point.j] == 1 && explored.add(point);
    }

    private static class Point {
        // wouldn't normally do this, I would use Lombok to write the boilerplate getters/setters for me
        public int i;
        public int j;

        public Point(int i, int j) {
            this.i = i;
            this.j = j;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Point)) return false;
            Point that = (Point) o;
            return i == that.i && j == that.j;
        }

        @Override
        public int hashCode() {
            return Objects.hash(i, j);
        }
    }
}
