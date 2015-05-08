package codility.lesson2;

import java.util.*;
import java.util.stream.*;

/**
 * Level : respectable
 */
class MissingIntegerJava {
	class Pair {
        public final int key;
        public final boolean value;

        public Pair(int key, boolean value) {
            this.key   = key;
            this.value = value;
        }
    }

    public int solution(int[] A) {
        int N = A.length;

        boolean[] tab = new boolean[N];


        for (int i=0; i < N; i++) {
            if (A[i] <= N && A[i] > 0)
                tab[A[i]-1] = true;
        }

        // IntStream.rang upper bound is exclusive so we need to use N+1
        Optional<Pair> option = IntStream.range(1,N + 1)
            .mapToObj(i -> new Pair(i,tab[i - 1]))
            .filter(x -> !x.value)
            .findFirst();

        return option.isPresent() ? option.get().key : N + 1;
    }
}
