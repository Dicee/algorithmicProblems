package miscellaneous.utils.math.geometry;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

public class TriMatrix<T> extends BaseMatrix<T> {
	private final Object[][] mat;
	
	public TriMatrix(int n, Class<T> clazz) {
		super(n,n,clazz);
		this.mat = new Object[n][];
		for (int i=0 ; i<n ; i++) this.mat[i] = new Object[n - i];
	}
	
	@Override
	protected T safeGet(int i, int j) {
		Pair<Integer,Integer> pos = getRealPos(i,j);
		return clazz.cast(mat[pos.getKey()][pos.getValue()]);
	}

	@Override
	protected Matrix<T> safeSet(int i, int j, T value) {
		Pair<Integer,Integer> pos = getRealPos(i,j);
		mat[pos.getKey()][pos.getValue()] = value;
		return this;
	}
	
	private Pair<Integer,Integer> getRealPos(int i, int j) {
		int tmp = j;
		j       = Math.max(i,j);
		i       = Math.min(i,tmp);
		return new Pair<>(i,j - i);
	}

	@Override
	protected List<T> safeGetRow(int i) {
		List<T> row = new ArrayList<>(mat.length);
		for (int k=0 ; k<mat.length ; k++) row.add(safeGet(i,k));
		return row;
	}

	@Override
	protected List<T> safeGetCol(int j) {
		List<T> col = new ArrayList<>(mat.length);
		for (int k=0 ; k<mat.length ; k++) col.add(safeGet(k,j));
		return col;
	}
	
	@Override
	public int nRows() {
		return mat.length;
	}
	
	@Override
	public int nCols() {
		return nRows();
	}
}
