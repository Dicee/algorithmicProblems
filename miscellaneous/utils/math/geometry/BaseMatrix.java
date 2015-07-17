package miscellaneous.utils.math.geometry;

import java.util.List;

public abstract class BaseMatrix<T> implements Matrix<T> {
	protected abstract List<T>   safeGetRow   (int i);
	protected abstract List<T>   safeGetCol   (int j);
	protected abstract T         safeGet      (int i, int j);
	protected abstract Matrix<T> safeSet      (int i, int j, T value);
	
	protected final Class<T> clazz;
	
	public BaseMatrix(int n, int m, Class<T> clazz) {
		checkSize(n,m);
		this.clazz = clazz;
	}
	
	private final void checkSize(int n, int m) {
		if (n <= 0 || m <= 0) throw new IllegalArgumentException("A matrix has at least 1 line and 1 column");
	}
	
	@Override
	final public T get(int i, int j) {
		checkInBounds(i,j);
		return safeGet(i,j);
	}
	
	@Override
	final public Matrix<T> set(int i, int j, T value) {
		checkInBounds(i,j);
		return safeSet(i,j,value);
	}
	
	@Override
	final public List<T> getRow(int i) {
		checkInBounds(i,0);
		return safeGetRow(i);
	}
	
	@Override
	final public List<T> getCol(int j) {
		checkInBounds(0,j);
		return safeGetCol(j);
	}

	protected void checkInBounds(int i, int j) {
		if (i < 0 || nRows() <= i || j < 0 || nCols() <= j)
			throw new IndexOutOfBoundsException(String.format("(%d,%d) out of bounds",i,j));
	}
	
	@Override 
	public String toString() {
		int maxWidth = computeMaxWidth();
		StringBuilder sb = new StringBuilder();
		for (int i=0 ; i<nRows() ; i++) {
			for (int j=0 ; j<nCols() ; j++) {
				String toString = nullableToString(safeGet(i,j));
				sb.append(toString).append(getSpaces(maxWidth - toString.length() + 1));
			}
			sb.append("\n");
		}
		return sb.toString().trim();
	}
	
	private int computeMaxWidth() {
		int maxWidth = 0;
		for (int i=0 ; i<nRows() ; i++)
			for (int j=0 ; j<nCols() ; j++) 
				maxWidth = Math.max(maxWidth,nullableToString(safeGet(i,j)).length());
		return maxWidth;
	}
	
	public String nullableToString(Object o) {
		return String.valueOf(o);
	}
	
	private StringBuilder getSpaces(int n) {
		StringBuilder sb = new StringBuilder();
		for (int i=0 ; i<n ; i++) sb.append(" ");
		return sb;
	}
}
