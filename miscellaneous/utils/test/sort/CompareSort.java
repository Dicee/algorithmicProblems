package utils.sort;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CompareSort {
	public static void main(String[] args) {
		int n = 100000;
		Random rd = new Random();
		Integer[] arr = new Integer[n];
		for (int i=0 ; i<n ; i++)
			arr[i] = rd.nextInt(150);
		
		List<SortAlgorithm> algorithms = Arrays.asList(new SelectionSort(),new InsertionSort(),new QuickSort());
		for (SortAlgorithm algorithm : algorithms) {
			Integer[] test = Arrays.copyOf(arr,arr.length);
			long start     = System.nanoTime();
			algorithm.sort(test);
			System.out.println(String.format("Time elapsed with %s : %f ms",
					algorithm.getName(),(System.nanoTime() - start)/1e6));
		}
	}
}
