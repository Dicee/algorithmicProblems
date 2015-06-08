package miscellaneous.utils.math.geometry;

public class Matrix {
	protected double[][] mat;
	
	public Matrix(int n, int m)	{
		this.mat = new double[n][m];
	}
	
	public Matrix(int n) {
		this.mat = new double[n][1];
	}
	
	public Matrix(Vector3D v) {
		this(3);
		this.mat[0][0] = v.x;
		this.mat[1][0] = v.y;
		this.mat[2][0] = v.z;
	}
	
	public Matrix(Vector3D v1, Vector3D v2, Vector3D v3) {
		this.mat  = new double[][] { new double[] { v1.x,v2.x,v3.x },
									 new double[] { v1.y,v2.y,v3.y }, 
									 new double[] { v1.z,v2.z,v3.z }
								   };
		
	}
	
	public static Matrix transferMatrix(Vector3D[] vects1, Vector3D[] vects2) {
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
		
		return new Matrix(c1,c2,c3);
	}
	
	public Matrix mult(Matrix matrix) {		
		if (mat[0].length != matrix.mat.length)
			throw new IllegalArgumentException(
					String.format("Incompatible dimensions : (%d,%d) x (%d,%d)",
							mat.length,mat[0].length,matrix.mat.length,matrix.mat[0].length));
		
		Matrix result = new Matrix(mat.length,matrix.mat[0].length);		
		for (int i=0 ; i<mat.length ; i++)
			for (int j=0 ; j<matrix.mat[0].length ; j++) {
				double sum = 0;
				for (int k=0 ; k<matrix.mat.length ; k++) 
					sum += get(i,k)*matrix.get(k,j);				
				result.set(i,j,sum);
			}
		return result;
	}
	
	public Vector3D mult(Vector3D v) {		
		if (mat[0].length != 3)
			throw new IllegalArgumentException(
					String.format("Incompatible dimensions : (%d,%d) x (3,1)",
							mat.length,mat[0].length));
		Matrix matrix   = mult(new Matrix(v));
		return new Vector3D(matrix.mat[0][0],matrix.mat[1][0],matrix.mat[2][0]);
	}
		
	public double get(int i, int j)	{
		return mat[i][j];
	}	
	
	public void set(int i, int j, double val) {
		mat[i][j] = val;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (int i=0 ; i<mat.length ; i++) {
			for (int j=0 ; j<mat[0].length ; j++)
				result.append(String.format("%f   ",get(i,j)));
			result.append("\n");
		}
		return result.toString();
	}
}
