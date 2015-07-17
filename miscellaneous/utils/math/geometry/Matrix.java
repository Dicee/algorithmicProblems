package miscellaneous.utils.math.geometry;

import java.util.List;

public interface Matrix<T> {
	public         T  get   (int i, int j);
	public Matrix <T> set   (int i, int j, T value);
	public List   <T> getRow(int i);
	public List   <T> getCol(int j);
	public int        nRows ();
	public int        nCols ();
}
