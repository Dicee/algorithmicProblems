package miscellaneous.utils.math.geometry;

import static miscellaneous.utils.math.MathUtils.isZero;

public final class Vector3D implements Rotatable<Vector3D>, Translatable<Vector3D> {
	public double x, y, z;

	public Vector3D(Vector3D v) {
		this(v.x,v.y,v.z);
	}
	
	public Vector3D() {
		this(0,0,0);
	}
	
	public Vector3D(Vector3D from, Vector3D to) {
		setLocation(to.x - from.x,to.y - from.y,to.z - from.z);
	}

	public Vector3D(double x, double y, double z) {
		setLocation(x,y,z);
	}

	public void setLocation(Vector3D v) {
		setLocation(v.x,v.y,v.z);
	}
	
	public void setLocation(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double norm() {
		return Math.sqrt(sqrNorm());
	}

	public double sqrNorm() {
		return x*x + y*y + z*z;
	}
	
	public Vector3D cross(Vector3D v) {
		return new Vector3D(y * v.z - z * v.y,z * v.x - x * v.z,x * v.y - y * v.x);
	}
	
	public double dot(Vector3D v) {
		return x*v.x + y*v.y + z*v.z;
	}

	public void normalize() {
		setLocation(scale(1/norm()));
	}

	public Vector3D plus(Vector3D v) {
		return new Vector3D(x + v.x,y + v.y,z + v.z);
	}
	
	public Vector3D minus(Vector3D v) {
		return plus(v.scale(-1));
	}
	
	public Vector3D opposed() {
		return scale(-1);
	}
	
	public static Vector3D linearCombination(Vector3D v, Vector3D w, double lambda, double mu) {
		return v.scale(lambda).plus(w.scale(mu));
	}
	
	
	public Vector3D scale(double lambda) {
		return new Vector3D(lambda*x,lambda*y,lambda*z);
	}
	
	@Override
	public Vector3D translate(Vector3D v) {
		return plus(v);
	}

	@Override
	public Vector3D rotateX(double u) {
		Vector3D tmp = new Vector3D(x,y,z);
		tmp          = tmp.rotateX(u);
		return new Vector3D(tmp.x,tmp.y,tmp.z);
	}

	@Override
	public Vector3D rotateY(double u) {
		Vector3D tmp = new Vector3D(x,y,z);
		tmp          = tmp.rotateY(u);
		return new Vector3D(tmp.x,tmp.y,tmp.z);
	}

	@Override
	public Vector3D rotateZ(double u) {
		Vector3D tmp = new Vector3D(x,y,z);
		tmp          = tmp.rotateZ(u);
		return new Vector3D(tmp.x,tmp.y,tmp.z);
	}

	@Override
	public Vector3D rotateXYZ(double u, double v, double w) {
		return this.rotateX(u).rotateY(v).rotateZ(w);
	}
	
	@Override
	public String toString() {		  
		return String.format("Point(%f,%f,%f)",x,y,z);
	}
	
	public Vector3D clone() {
		return new Vector3D(x,y,z);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return false;
		if (!(o instanceof Vector3D))
			return false;
		Vector3D other = (Vector3D) o;
		return isZero(minus(other).sqrNorm());
	}
}
