package hackerrank.algorithms.sorting.fraudulentActivityNotifications;

import hackerrank.algorithms.sorting.fraudulentActivityNotifications.FailedSolution.SkipList;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class SkipListTest {
    @Test
    public void inserts() {
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
    public void insertsAndRemoves() {
        SkipList skipList = new SkipList(0.5f);
        List<Integer> expected = new ArrayList<>();

        for (Integer value : Arrays.asList(2, 8, 9, 5, 0, 3, -2, 4, -8, 0)) {
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
    public void removeNonExisting() {
        SkipList skipList = new SkipList(0.5f);

        skipList.insert(5);
        skipList.remove(8);

        List<Integer> expected = Arrays.asList(5);
        assertThat(skipList.toList(), equalTo(expected));
        assertThat(toListFromRandomAccessor(skipList), equalTo(expected));
    }

    // shows huge performance degradation when the number of distinct values inserted increases. That makes sense because
    // the set doesn't get any bigger when duplicates are added, while our specialized skip list needs to keep all duplicates.
    // Despite efforts to not construct links between nodes with equal values (except at the lowest level), it seems like
    // the resulting skip list loses its ability to "skip".
    //
    // To achieve better performance, we could perhaps implement a more
    // complicated version in which we only create nodes on higher levels for the first insertion of a given value, and then
    // always add duplicates at the head of the list of duplicates on the lowest level, so that the links that may have been
    // created on the higher levels can skip all duplicates straight to the last one. On the lowest level, we could have special
    // nodes with an additional pointer to the last occurrence of their value to avoid paying the high penalty of going through
    // all duplicates on this level.
    //
    // Another idea would be to hack the distances contained by the nodes to encode the presence of duplicates, but right
    // now it seems to me like it would be tricky/impossible to maintain correctness in lookup operations based on distance.
    @Test
    public void speed() {
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
