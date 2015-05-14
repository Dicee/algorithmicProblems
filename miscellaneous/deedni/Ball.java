package miscellaneous.deedni;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import miscellaneous.CollectionUtils;

public class Ball {
	public static void main(String[] args) {
		test("input/ball_input1.txt");
		test("input/ball_input2.txt");
	}

	private static void test(String path) {
		long start = System.currentTimeMillis();
		solve(path);
		System.out.println((System.currentTimeMillis() - start) + " ms");
	}

	private static void solve(String path) {
		Scanner sc = new Scanner(Rotation.class.getResourceAsStream(path));
		
		int    n = sc.nextInt();
		int    k = sc.nextInt();
		
		int[] balls = { 1,2,3,4,5,6,7,8 };
		for (int i=0 ; i<n ; i++) {
			int l = sc.nextInt() - 1;
			int r = sc.nextInt() - 1;
			CollectionUtils.swap(balls,l,r);
		}
		
		StringBuilder sb = new StringBuilder();
		for (int i=0 ; i<balls.length ; i++) sb.append(getPosAtIteration(balls,i,k)).append(" ");
		System.out.println(sb.toString().trim());
		sc.close();
	}

	private static int getPosAtIteration(int[] balls, int index, int it) {
		List<Integer> serie = new ArrayList<>();
		int current = balls[index];
		do {
			serie.add(current);
			current = balls[current - 1];
		} while (current != serie.get(0));
		
		int pos = serie.get((it - 1) % serie.size());
		return pos;
	}
}
