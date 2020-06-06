package interviewbit.Programming.Arrays.MergeOverlappingIntervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

// Difficulty: trivial

// https://www.interviewbit.com/problems/merge-overlapping-intervals/
public class Solution {
    public static void main(String[] args) {
        System.out.println(merge(new ArrayList<>(Arrays.asList(new Interval(1, 3), new Interval(2, 6), new Interval(8, 10), new Interval(15, 18))))); // [[1, 6], [8, 10], [15, 18]]
        System.out.println(merge(new ArrayList<>(Arrays.asList(new Interval(22, 43), new Interval(32, 53), new Interval(5, 97))))); // [[5, 97]]
    }

    public static List<Interval> merge(List<Interval> intervals) {
        intervals.sort(Comparator.comparingInt(interval -> interval.start));

        List<Interval> newIntervals = new ArrayList<>();
        Interval currentInterval = null;

        for (Interval interval : intervals) {
            if (currentInterval == null) currentInterval = interval;
            else if (overlap(currentInterval, interval)) {
                currentInterval = new Interval(Math.min(interval.start, currentInterval.start), Math.max(interval.end, currentInterval.end));
            } else if (currentInterval.start < interval.start) {
                newIntervals.add(currentInterval);
                currentInterval = interval;
            } else {
                newIntervals.add(interval);
            }
        }

        newIntervals.add(currentInterval);
        return newIntervals;
    }

    private static boolean overlap(Interval i1, Interval i2) {
        return Math.min(i1.end, i2.end) >= Math.max(i1.start, i2.start);
    }

    private static class Interval {
        private final int start;
        private final int end;

        private Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String toString() {
            return "[" + start + ", " + end + ']';
        }
    }
}
