package unknownSource;

import java.util.Arrays;
import java.util.Scanner;

// Difficulty: easy once you find the recurrence relation, but it took me a while to approach it in the right way to do so.

// http://poj.org/problem?id=2663
public class TriTiling {
    private static final int MAX_N = 30;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int[] solutions = new int[MAX_N + 1];
        Arrays.fill(solutions, -1);
        solutions[0] = 1;

        int[] oddSolutions = new int[MAX_N + 1];
        Arrays.fill(oddSolutions, -1);
        oddSolutions[1] = 1;

        int n;
        while ((n = Integer.parseInt(sc.nextLine())) != -1) {
            System.out.println(solveEven(n, solutions, oddSolutions));
        }
    }

    // returns the number of solutions for the regular problem with a full 3xn rectangle to fill
    private static int solveEven(int n, int[] solutions, int[] cornerSolutions) {
        if (n % 2 == 1) return 0;
        if (solutions[n] >= 0) return solutions[n];

        // to solve the problem of size n, we can either:
        // - stack 3 tiles horizontally and then solve the problem of size n-2
        // - use two tiles in an L shape and fill the remaning rectangle of size n-1 which has a 1x1 
        //   tile already filled at the bottom-left (let's call that the "odd" problem
        int sol = solveEven(n - 2, solutions, cornerSolutions) + 2 * solveOdd(n - 1, solutions, cornerSolutions);
        solutions[n] = sol;
        return sol;
    }

    // returns the number of solutions for a 3xn rectangle with a single 1x1 tile filled at any corner
    private static int solveOdd(int n, int[] solutions, int[] oddSolutions) {
        if (n % 2 == 0) return 0;
        if (oddSolutions[n] >= 0) return oddSolutions[n];

        // to solve the odd problem of size n, we can either:
        // - stack place a tile vertically to fill the gap of shape 2x1 left by the filled 1x1 corner and then solve 
        //    the even problem of size n-1
        // - place two horizontal tiles below the filled 1x1 corner, add another horizontal tile in the alignement of 
        //   the 1x1 corner and then solve the odd problem of size n-2
        int sol = solveEven(n - 1, solutions, oddSolutions) + solveOdd(n - 2, solutions, oddSolutions);
        oddSolutions[n] = sol;
        return sol;
    }
}
