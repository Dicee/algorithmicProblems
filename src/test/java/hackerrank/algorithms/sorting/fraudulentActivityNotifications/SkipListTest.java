package hackerrank.algorithms.sorting.fraudulentActivityNotifications;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import hackerrank.algorithms.sorting.fraudulentActivityNotifications.SkipListSolutionForFun.SkipList;

class SkipListTest {
    @Test
    void inserts() {
        SkipList skipList = new SkipList(0.5f);
        List<Integer> expected = new ArrayList<>();

        for (Integer value : Arrays.asList(2, 8, 9, 5, 0, 3, 2, 4, 8, 0)) {
            skipList.insert(value);

            expected.add(value);
            expected.sort(Comparator.naturalOrder());

            assertThat(skipList.toList(), equalTo(expected));
            assertThat(toListFromRandomAccessor(skipList), equalTo(expected));
        }
    }

    @Test
    void insertsAndRemoves() {
        SkipList skipList = new SkipList(0.5f);
        List<Integer> expected = new ArrayList<>();

        for (Integer value : Arrays.asList(2, 8, 2, 9, 5, 0, 3, -2, 4, -8, 0)) {
            if (value < 0) {
                skipList.remove(- value);
                expected.remove((Integer) (- value));
            } else {
                skipList.insert(value);
                expected.add(value);
            }
            expected.sort(Comparator.naturalOrder());

            assertThat(skipList.toList(), equalTo(expected));
            assertThat(toListFromRandomAccessor(skipList), equalTo(expected));
        }
    }

    @Test
    void removeNonExisting() {
        SkipList skipList = new SkipList(0.5f);

        skipList.insert(5);
        skipList.remove(8);

        List<Integer> expected = Arrays.asList(5);
        assertThat(skipList.toList(), equalTo(expected));
        assertThat(toListFromRandomAccessor(skipList), equalTo(expected));
    }

    // Example output (not quite as fast as java.util.TreeSet but has random access and is still pretty good !):
    //
    // Set.add performed in 50 ms
    // Set.get performed in 13 ms
    // Set.remove performed in 6 ms
    // ===
    // SkipList.add performed in 372 ms
    // SkipList.get performed in 41 ms
    // SkipList.remove performed in 152 ms
    // ===
    // SkipList.add performed in 201 ms
    // SkipList.get performed in 18 ms
    // SkipList.remove performed in 75 ms
    // ===
    // Set.add performed in 29 ms
    // Set.get performed in 67 ms
    // Set.remove performed in 10 ms
    @Test
    void speed() {
        Collection<Integer> values = randomValues(100);

        SkipList skipList = new SkipList(0.5f);
        TreeSet<Integer> set = new TreeSet<>();

        testSpeedWith(values, set::add, set::contains, set::remove, "Set");
        testSpeedWith(values, skipList::insert, skipList::get, skipList::remove, "SkipList");

        skipList = new SkipList(0.5f);
        testSpeedWith(values, skipList::insert, skipList::get, skipList::remove, "SkipList");

        set = new TreeSet<>();
        testSpeedWith(values, set::add, set::contains, set::remove, "Set");
    }

    private void testSpeedWith(Collection<Integer> values, Consumer<Integer> add, Consumer<Integer> get, Consumer<Integer> remove, String name) {
        long start = System.currentTimeMillis();
        for (Integer value : values) {
            add.accept(value);
        }
        System.out.println(name + ".add performed in " + (System.currentTimeMillis() - start) + " ms");

        start = System.currentTimeMillis();
        for (Integer value : values) {
            get.accept(value);
        }
        System.out.println(name + ".get performed in " + (System.currentTimeMillis() - start) + " ms");

        start = System.currentTimeMillis();
        for (Integer value : values) {
            remove.accept(value);
        }
        System.out.println(name + ".remove performed in " + (System.currentTimeMillis() - start) + " ms");
        System.out.println("===");
    }

    private static Collection<Integer> randomValues(int maximumValue) {
        int n = 100_000;
        Random rd = new Random(109);
        List<Integer> ints = new ArrayList<>();

        while (ints.size() < n) {
            int random = rd.nextInt(maximumValue);
            ints.add(random);
        }

        return ints;
    }

    // this can differ from using the normal iteration over the skip list because the distances contained in the node
    // could be completely broken while the lowest level contains the right values
    private static List<Integer> toListFromRandomAccessor(SkipList skipList) {
        return IntStream.range(0, skipList.size()).mapToObj(skipList::get).collect(Collectors.toList());
    }
}