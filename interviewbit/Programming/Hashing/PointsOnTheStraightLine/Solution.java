package interviewbit.Programming.Hashing.PointsOnTheStraightLine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.Comparator.naturalOrder;

// Difficulty: medium. Not extremely difficult, but I had to think a little on how to represent lines in a unique and reliably hashable
//             form, as well as how to handle cases such as duplicate points. It's a shame that I couldn't use Scala for this problem
//             (the site doesn't allow it), it would have been a lot more concise otherwise =(. I found it interesting overall.

// https://www.interviewbit.com/problems/points-on-the-straight-line/
public class Solution {
    public static void main(String[] args) {
        System.out.println(maxPoints(Arrays.asList(1, 2), Arrays.asList(2, 2))); // 2
        System.out.println(maxPoints(Arrays.asList(1, 2), Arrays.asList(1, 2))); // 2
        System.out.println(maxPoints(Arrays.asList(1, 2, 5), Arrays.asList(1, 2, 5))); // 3
        System.out.println(maxPoints(Arrays.asList(0, 0, 1, 2, 5, 5, 6), Arrays.asList(0, 0, 1, 2, 5, 5, 5))); // 6
        System.out.println(maxPoints(Arrays.asList(0, 0, 1, 4, 2, 5, 5, 6), Arrays.asList(0, 0, 1, 3, 2, 5, 5, 5))); // 6
        System.out.println(maxPoints(Arrays.asList(0, 5, 10, 0, 5), Arrays.asList(1, 11, 21, 1, 16))); // 4
        System.out.println(maxPoints(Arrays.asList(0, 5, 10, 0, 5, 10), Arrays.asList(1, 11, 21, 1, 16, 31))); // 4
        System.out.println(maxPoints(Arrays.asList(0, 5, 10, 0, 5, 10, 15), Arrays.asList(1, 11, 21, 1, 16, 31, 46))); // 5
        System.out.println(maxPoints(Arrays.asList(0, 0, 0, 10), Arrays.asList(0, -200, 1000, 17))); // 3
        System.out.println(maxPoints(Arrays.asList(-1), Arrays.asList(13))); // 3
    }

    public static int maxPoints(List<Integer> xs, List<Integer> ys) {
        Map<Line, Set<Point>> lines = new HashMap<>();
        Map<Point, Integer> pointsOccurrences = new HashMap<>();

        for (int i = 0; i < xs.size(); i++) {
            pointsOccurrences.merge(new Point(xs.get(i), ys.get(i)), 1, (a, b) -> a + b);

            for (int j = i + 1; j < xs.size(); j++) {
                Point p1 = new Point(xs.get(i), ys.get(i));
                Point p2 = new Point(xs.get(j), ys.get(j));
                if (p1.equals(p2)) continue;

                Line line = Line.between(p1, p2);
                Set<Point> points = lines.computeIfAbsent(line, ignored -> new HashSet<>());
                points.add(p1);
                points.add(p2);
            }
        }

        return lines.values().stream()
                .map(points -> points.stream()
                .mapToInt(pointsOccurrences::get).sum())
                .max(naturalOrder())
                // if there's only one distinct point, count its number of occurrences. If there are none, count as 0.
                // In my opinion it's not what this should return for the case of a single point because there would be
                // but that's what it takes to pass all the tests...
                .orElse(pointsOccurrences.values().stream().max(naturalOrder()).orElse(0));
    }

    private static class Line {
        public static Line between(Point p1, Point p2) {
            if (p1.equals(p2)) throw new IllegalArgumentException("Can't determine a unique line between two identical points");
            if (p1.x == p2.x) return new Line(1, 0, -p1.x);

            int a = p2.y - p1.y;
            int b = p1.x - p2.x;

            // force a positive sign on a or b if a is zero, in order to reduce multiple equivalent line representations to a single one
            if (a < 0 || a == 0 && b < 0) {
                a *= -1;
                b *= -1;
            }

            // normalize by the gcd rather than the norm of the directing vector, which is likely not an integer and may not even be a
            // rational number, hence impossible to rely on in a hashmap due to the finite precision of floating point numbers
            int gcd = gcd(abs(max(a, b)), abs(min(a, b)));
            a /= gcd;
            b /= gcd;

            return new Line(a, b, -a * p1.x - b * p1.y);
        }

        private final int a, b, c;

        public Line(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        private static int gcd(int a, int b) {
            if (a == 0) return b;
            if (b == 0) return a;

            int r = a % b;
            while (r > 0) {
                a = b;
                b = r;
                r = a % b;
            }

            return b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Line line = (Line) o;
            return a == line.a && b == line.b && c == line.c;
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b, c);
        }

        @Override
        public String toString() {
            return String.format("Line(%d, %d, %d)", a, b, c);
        }
    }

    private static class Point {
        private final int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return String.format("Point(%d, %d)", x, y);
        }
    }
}
