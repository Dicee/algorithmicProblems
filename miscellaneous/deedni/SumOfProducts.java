package miscellaneous.deedni;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import javafx.util.Pair;

public class SumOfProducts {
	
	public static void main(String[] args) {
		test("input/sumOfProducts_input1.txt");
//		test("input/sumOfProducts_input2.txt");
//		test("input/sumOfProducts_input3.txt");
	}

	private static void test(String path) {
		long start = System.currentTimeMillis();
		solve(path);
		System.out.println((System.currentTimeMillis() - start) + " ms");
	}
	
	private static void solve(String path) {
		Scanner sc = new Scanner(SumOfProducts.class.getResourceAsStream(path));
		int n = sc.nextInt();
		
		Integer[] arr = new Integer[n];
		LinkedList<Integer> toPlace = new LinkedList<>();
		LinkedList<Pair<Integer,Integer>> fixedValuesWithIndex = new LinkedList<>();
		
		for (int i=0 ; i<n ; i++) {
			int a = sc.nextInt();
			int d = sc.nextInt();
			if (d == -1) toPlace.add(a);
			else {
				arr[d] = a;
				fixedValuesWithIndex.add(new Pair<>(a,d));
			}
		}
		Collections.sort(toPlace);
		Collections.sort(fixedValuesWithIndex,(p1,p2) -> - Integer.compare(p1.getKey(),p2.getKey()));
		
		int highest = fixedValuesWithIndex.isEmpty() ? 0 : fixedValuesWithIndex.peek().getValue();
		while (!toPlace.isEmpty()) {
			boolean leftAvailable  = isAvailable(highest - 1,arr);
			boolean rightAvailable = isAvailable(highest + 1,arr);
			if      ( leftAvailable && !rightAvailable) highest = place(highest - 1,arr,toPlace);
			else if (!leftAvailable &&  rightAvailable) highest = place(highest + 1,arr,toPlace);
			else if (!leftAvailable && !rightAvailable) highest = 0;
			update(highest,toPlace,fixedValuesWithIndex);
		}
		
		sc.close();
	}
	
	private static void update(int highest, LinkedList<Integer> toPlace, LinkedList<Pair<Integer, Integer>> fixedValuesWithIndex) {
		// TODO Auto-generated method stub
		
	}

	private static int place(int i, Integer[] arr, LinkedList<Integer> toPlace) {
		// TODO Auto-generated method stub
		return 0;
	}

	private static boolean isAvailable(int index, Integer[] arr) {
		return 0 <= index && index < arr.length && arr[index] == null;
	}
}
