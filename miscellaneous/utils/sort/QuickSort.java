package utils.sort;

import java.util.Arrays;
import java.util.Random;

import utils.various.Sofia;

public class QuickSort implements SortAlgorithm {
	private Random	rd;

	public QuickSort() {
		this.rd = new Random();
	}
	
	@Override
	public <T extends Comparable<T>> void sort(T[] arr) {
		sort(arr,0,arr.length - 1);
	}
	
	private <T extends Comparable<T>> void sort(T[] arr, int start, int end) {
		if (start < end) {
			int index = pivot(arr,start,end);
			sort(arr,start,index - 1);
			sort(arr,index + 1,end);
		}
	}
	
	private <T extends Comparable<T>> int pivot(T[] arr, int start, int end) {
		T pivot    = arr[start + rd.nextInt(end - start + 1)];
		int result = start;
		for (int i=start ; i<=end ; i++) 
			if (arr[i].compareTo(pivot) < 0)
				Sofia.swap(arr,i,result++);
		Sofia.swap(arr,result,end);
		return result;
	}
	
	@Override
	public String getName() {
		return "Quicksort";
	}
}
