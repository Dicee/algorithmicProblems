package interviewbit.Programming.BinarySearch.ImplementPowerFunction;

// Difficulty: medium. Fast exponentiation is a well-known problem, and simple maths tell us that
//             (a * b) % m == ((a % m) * (b % m)) % m, which allows applying the modulo operator
//             at every step to avoid overflows. The size of the problem made it a bit more difficult,
//             as Java doesn't support tail-recursion optimization and I had never implemented fast
//             exponentiation iteratively.

// https://www.interviewbit.com/problems/implement-power-function/
public class Solution {
    public static void main(String[] args) {
        System.out.println(pow(2, 3, 3)); // 2
        System.out.println(pow(2, 3, 5)); // 3
        System.out.println(pow(2, 99, 17)); // 8
        System.out.println(pow(3, 80, 5)); // 1
        System.out.println(pow(123456, 0, 10)); // 1
        System.out.println(pow(123456, 0, 1)); // 0
        System.out.println(pow(0, 0, 1)); // 0
        System.out.println(pow(-1, 1, 20)); // 19
        System.out.println(pow(71045970, 41535484, 64735492)); // 20805472
        System.out.println(pow(79161127, 99046373, 57263970)); // 47168647
    }

    public static int pow(int x, int n, int d) {
        long acc = 1;
        long pow = x;

        while (n > 0) {
            if ((n & 1) == 1) { // odd
                acc = positiveMod(pow * acc, d);
                n--;
            }
            pow = positiveMod(pow * pow, d);
            n >>>= 1;
        }

        return (int) positiveMod(acc, d);
    }

    private static long positiveMod(long n, int d) {
        long mod = n % d;
        return mod < 0 ? mod + d : mod;
    }
}