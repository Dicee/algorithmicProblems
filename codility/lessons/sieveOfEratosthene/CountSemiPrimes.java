package codility.lessons.sieveOfEratosthene;

import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Level : painless
 */
public class CountSemiPrimes {
    public int[] solution(int N, int[] P, int[] Q) {
        int[] sieve = sieve(N);

        Map<Integer, Integer> semiPrimesCount = new HashMap<>();
        for (int i = 0; i < Q.length; i++) {
            semiPrimesCount.put(P[i], null);
            semiPrimesCount.put(Q[i], null);
        }

        int counter = 0;
        for (int i = 0; i < sieve.length; i++) {
            if (sieve[i] == 2)                  counter++;
            if (semiPrimesCount.containsKey(i)) {
                semiPrimesCount.put(i, counter);
            }
        }

        int[] result = new int[Q.length];
        for (int i = 0; i < result.length; i++) 
            result[i] = semiPrimesCount.get(Q[i]) - semiPrimesCount.get(P[i]) + (sieve[P[i]] == 2 ? 1 : 0);
        return result;
    }

    public int[] sieve(int N) {
        int[] result = new int[N + 1];
        result[0] = 1;
        result[1] = 1;
        for (int i = 2; i < result.length; i++)
            if (result[i] == 0) 
                for (int j = 2 * i; j < result.length; j += i)
                    for (int k = j; k % i == 0 && result[j] < 3; k /= i) result[j]++;
        return result;
    }
    
     @Test
     public void test() {
         CountSemiPrimes countSemiPrimes = new CountSemiPrimes();
         assertThat(countSemiPrimes.solution(26, new int[] { 1, 4, 16 }, new int[] { 26, 10, 20 }), Matchers.equalTo(new int[] { 10, 4, 0 }));
     }
}