package utils.sort;

public interface SortAlgorithm {
	public <T extends Comparable<T>> void sort(T[] arr);
	public String getName();
}
