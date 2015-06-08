package miscellaneous.utils.sort;

import java.util.Arrays;

public class InsertionSort<T extends Comparable<T>> implements SortAlgorithm<T> {
	@Override
	public void sort(T[] arr) {
		T[] result = Arrays.copyOf(arr,arr.length);
		result[0]  = arr[0];

		for (int i=1 ; i<arr.length ; i++) {
			int index = 0;
			while (index < i && result[index].compareTo(arr[i]) < 0)
				index++;
			
			T prec = result[index];
			T curr;
			for (int j=index+1 ; j<i+1 ; j++) {
				curr      = result[j];
				result[j] = prec;
				prec      = curr;
			}
			result[index] = arr[i];
		}
		
		for (int i=0 ; i<arr.length ; i++)
			arr[i] = (T) result[i];
	}
	
	@Override
	public String getName() {
		return "Insertion sort";
	}
}
