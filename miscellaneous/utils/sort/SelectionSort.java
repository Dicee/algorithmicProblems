package utils.sort;

import java.util.Arrays;

import utils.various.Sofia;

public class SelectionSort implements SortAlgorithm {
	@Override
	public <T extends Comparable<T>> void sort(T[] arr) {
		for (int i=0 ; i<arr.length ; i++) {
			int min = i;
			for (int j=i+1 ; j<arr.length ; j++) 
				min = arr[j].compareTo(arr[min]) < 0 ? j : min;
			Sofia.swap(arr,i,min);
		}	
	}
	
	@Override
	public String getName() {
		return "Selection sort";
	}
}
