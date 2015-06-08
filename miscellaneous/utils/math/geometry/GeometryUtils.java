package miscellaneous.utils.math.geometry;

import static miscellaneous.utils.math.MathUtils.isZero;

public final class GeometryUtils {
	public static Vector3D[] canonicBase = { new Vector3D(1,0,0),new Vector3D(0,1,0),new Vector3D(0,0,1) };
	
	public static double det(double[] c0, double[] c1, double[] c2) {
		  return   
			  c0[0]*(c1[1]*c2[2] - c2[1]*c1[2]) - 
			  c1[0]*(c0[1]*c2[2] - c2[1]*c0[2]) +
			  c2[0]*(c0[1]*c1[2] - c1[1]*c0[2]);
	}
	
	public static double cramer(double[] c0, double[] c1, double[] c2, double det) {
		  return det(c0,c1,c2) / det;
	}
	
	public static int mod(int n, int m) {
		  if (m < 0) m *= -1;
		  if (n < 0)
			  while (n < 0) n += m;
		  else
			  while (n >= m) n -= m;
		  return n;			  
	}	
	
	public static Vector3D[] adaptedBase(Vector3D normal) {
		boolean[] isZero = new boolean[3];
		Vector3D ez      = new Vector3D(normal);
		ez.normalize();
		
		int zeros  = (isZero[0] = isZero(ez.x)) ?         1 : 0;
		zeros      = (isZero[1] = isZero(ez.y)) ? zeros + 1 : zeros;
		zeros      = (isZero[2] = isZero(ez.z)) ? zeros + 1 : zeros;
		
		Vector3D ex = new Vector3D();
		switch (zeros) {
			case 0 :
				ex = new Vector3D(ez.y,- ez.x,0);
				break;
			case 1 :
				ex = isZero[0] ? new Vector3D(0,ez.z,- ez.y) :
					 isZero[1] ? new Vector3D(ez.z,0,- ez.x) : new Vector3D(ez.y,- ez.x,0);
				break;
			case 2 :
				ex = isZero[0] ? new Vector3D(1,0,0) :
					 isZero[1] ? new Vector3D(0,1,0) : new Vector3D(0,0,1);
				break;
			default :
				throw new IllegalArgumentException("A base cannot contain the zero vector");
		}
		ex.normalize();
		return new Vector3D[] { ex,ez.cross(ex),ez };
	}
	
	public static double axisDistance(Vector3D p, Vector3D dir, Vector3D m)	{		
		return new Vector3D(p,m).cross(dir).norm()/dir.norm();
	}
	
	public static Vector3D baseTransfer(Vector3D p, Vector3D[] vects, Vector3D o) {		
		Vector3D v = new Vector3D(o,p);
		return new Vector3D(v.dot(vects[0]),v.dot(vects[1]),v.dot(vects[2]));
	}
}
