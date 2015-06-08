package miscellaneous.utils.sort;

import static miscellaneous.utils.math.MathUtils.lowerThan;

import java.util.Arrays;

public class MergeSort<T extends Comparable<T>> implements SortAlgorithm<T> {
	@Override
	public void sort(T[] arr) {
		sort(arr,0,arr.length);
	}
	
	private void sort(T[] arr, int min, int max) {
		if (min + 1 < max) {
			int mid   = (min + max)/2;
			T[] left  = Arrays.copyOfRange(arr,min,mid); 
			T[] right = Arrays.copyOfRange(arr,mid,max);
			sort(left);
			sort(right);
			merge(left,right,arr,min,max);
		}
	}

	private void merge(T[] left, T[] right, T[] arr, int min, int max) {
		int l = 0, r = 0;
		for (int i=min ; i<max ; i++) 
			arr[i] = l >= left.length || (r < right.length && lowerThan(right[r],left[l])) ? right[r++] : left[l++];
	}
	
	@Override
	public String getName() {
		return "Merge sort";
	}
	
	public static void main(String[] args) {
		Integer[] arr = { 1,3,5,99,6,3,2,1,7,8 };
		(new MergeSort<Integer>()).sort(arr);
		System.out.println(Arrays.toString(arr));
	}
}
