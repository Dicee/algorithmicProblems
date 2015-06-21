package miscellaneous.utils.math.geometry.geometry3D;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import miscellaneous.utils.check.Check;
import miscellaneous.utils.math.geometry.BaseMatrix;

public class Matrix3D extends BaseMatrix<Double> {
	protected double[][] mat;
	
	public Matrix3D() {
		super(3,3,Double.class);
		this.mat = new double[3][3];
	}
	
	public Matrix3D(Vector3D v1, Vector3D v2, Vector3D v3) {
		super(3,3,Double.class);
		this.mat  = new double[][] { 
			new double[] { v1.x,v2.x,v3.x },
			new double[] { v1.y,v2.y,v3.y }, 
			new double[] { v1.z,v2.z,v3.z }
		};
	}
	
	public Matrix3D setCol(int j, Vector3D v) {
		checkInBounds(0,j);
		return safeSet(0,j,v.x).safeSet(1,j,v.y).safeSet(2,j,v.z);
	}
	
	public static Matrix3D transferMatrix(Vector3D[] vects1, Vector3D[] vects2) {
		double x    = vects1[0].dot(vects2[0]);
		double y    = vects1[0].dot(vects2[1]);
		double z    = vects1[0].dot(vects2[2]);
		Vector3D c1 = new Vector3D(x,y,z);
		
		x           = vects1[1].dot(vects2[0]);
		y           = vects1[1].dot(vects2[1]);
		z           = vects1[1].dot(vects2[2]);
		Vector3D c2 = new Vector3D(x,y,z);
		
		 x          = vects1[2].dot(vects2[0]);
		 y          = vects1[2].dot(vects2[1]);
		 z          = vects1[2].dot(vects2[2]);
		Vector3D c3 = new Vector3D(x,y,z);
		
		return new Matrix3D(c1,c2,c3);
	}
	
	public Matrix3D mult(Matrix3D matrix) {		
		Check.areEqual(mat[0].length,matrix.mat.length,String.format("Incompatible dimensions : (%d,%d) x (%d,%d)",
																	 mat.length,mat[0].length,matrix.mat.length,matrix.mat[0].length));
		
		Matrix3D result = new Matrix3D();		
		for (int i=0 ; i<mat.length ; i++) multRowWithWholeCols(matrix,result,i);
		return result;
	}
	
	public Vector3D mult(Vector3D v) {		
//		;
		Matrix3D matrix = multRowWithWholeCols(new Matrix3D().setCol(0,v),new Matrix3D(),0);
		return new Vector3D(matrix.mat[0][0],matrix.mat[1][0],matrix.mat[2][0]);
	}
	
	private Matrix3D multRowWithWholeCols(Matrix3D matrix, Matrix3D out, int row) {
		for (int j=0 ; j<matrix.mat[0].length ; j++) multRowWithCol(matrix,out,row,j);
		return out;
	}
	
	private Matrix3D multRowWithCol(Matrix3D matrix, Matrix3D out, int row, int col) {
		double sum = 0;
		for (int k=0 ; k<matrix.mat.length ; k++) sum += get(row,k)*matrix.get(k,col);				
		out.set(row,col,sum);
		return out;
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
	protected List<Double> safeGetRow(int i) {
		return DoubleStream.of(mat[i]).boxed().collect(toList());
	}

	@Override
	protected List<Double> safeGetCol(int j) {
		return IntStream.range(0,nRows()).mapToDouble(i -> mat[i][j]).boxed().collect(toList());
	}

	@Override
	protected Double safeGet(int i, int j) {
		return mat[i][j];
	}

	@Override
	protected Matrix3D safeSet(int i, int j, Double value) {
		mat[i][j] = value;
		return this;
	}
}
