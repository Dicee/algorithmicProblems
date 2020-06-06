package interviewbit.Programming.Arrays.MergeIntervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Difficulty: trivial

// https://www.interviewbit.com/problems/merge-intervals/
public class Solution {
    public static void main(String[] args) {
        System.out.println(insert(Arrays.asList(new Interval(1, 2), new Interval(3, 6)), new Interval(10, 8))); // [[1, 2], [3, 6], [8, 10]]
    }

    public static List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        List<Interval> newIntervals = new ArrayList<>();

        // for some reason the test cases even include what should be invalid intervals (reverse order)...
        Interval currentInterval = new Interval(Math.min(newInterval.start, newInterval.end), Math.max(newInterval.start, newInterval.end));

        for (Interval interval : intervals) {
            if (overlap(currentInterval, interval)) {
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