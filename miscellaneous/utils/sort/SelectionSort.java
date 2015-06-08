package miscellaneous.utils.sort;

import miscellaneous.utils.collection.CollectionUtils;

public class SelectionSort<T extends Comparable<T>> implements SortAlgorithm<T> {
	@Override
	public void sort(T[] arr) {
		for (int i=0 ; i<arr.length ; i++) {
			int min = i;
			for (int j=i+1 ; j<arr.length ; j++) 
				min = arr[j].compareTo(arr[min]) < 0 ? j : min;
			CollectionUtils.swap(arr,i,min);
		}	
	}
	
	@Override
	public String getName() {
		return "Selection sort";
	}
}
