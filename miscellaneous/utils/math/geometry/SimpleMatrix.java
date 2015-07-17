package miscellaneous.utils.math.geometry;

import java.util.ArrayList;
import java.util.List;

public class SimpleMatrix<T> extends BaseMatrix<T> {
	private final Object[][] mat;
	
	public SimpleMatrix(int n, int m, Class<T> clazz) {
		super(n,m,clazz);
		this.mat = new Object[n][m];				
	}
	
	@Override
	public int nRows() {
		return mat.length;
	}

	@Override
	public int nCols() {
		return mat[0].length;
	}

	@Override
	protected List<T> safeGetRow(int i) {
		List<T> row = new ArrayList<>(nCols());
		for (int j=0 ; j<row.size() ; j++) row.add(safeGet(i,j));
		return row;
	}

	@Override
	protected List<T> safeGetCol(int j) {
		List<T> col = new ArrayList<>(nRows());
		for (int i=0 ; i<col.size() ; i++) col.add(safeGet(i,j));
		return col;
	}

	@Override
	protected T safeGet(int i, int j) {
		return clazz.cast(mat[i][j]);
	}

	@Override
	protected Matrix<T> safeSet(int i, int j, T value) {
		mat[i][j] = value;
		return this;
	}
}
