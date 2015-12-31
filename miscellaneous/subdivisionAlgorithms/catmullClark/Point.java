package miscellaneous.subdivisionAlgorithms.catmullClark;

import java.util.List;

/**
 * This class is a basic implementation of a 3D point (double precision). The only noteworthy point
 * is that it also has an id. Using the id is optionnal (for example it is most of the time useless
 * when processing geometric operations) but it enables to refer easily to a Mesh mapping if the point
 * has been registered to the Mesh.
 * @author David Courtinot
 */

public class Point {
	public double x, y, z;
	private int id;
	
	/**
	 * Constructs a Point with its x, y, z coordinates.
	 * @param x
	 * @param y
	 * @param z
	 */
	public Point(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * new Point() is equivalent to new Point(0,0,0).
	 */
	public Point() {
		this(0,0,0);
	}
	
	/**
	 * Constructs a Point with the coordinates of another Point.
	 * @param p
	 */
	public Point(Point p) {
		this(p.x,p.y,p.z);
	}
	
	public Point sum(Point p) {
		return sum(p.x,p.y,p.z);
	}
	
	public Point sum(double x, double y, double z) {
		return new Point(this.x + x,this.y + y,this.z + z);
	}
	
	public Point multScal(double lambda) {
		return new Point(lambda*x,lambda*y,lambda*z);
	}
	
	public void setLocation(Point p) {
		x = p.x;
		y = p.y;
		z = p.z;
	}
	
	public static Point sum(Point p, Point q) {
		return p.sum(q);
	}
	
	public static Point getBarycenter(List<Point> pts) {
		Point barycenter = new Point();
		for (Point p : pts)
			barycenter = barycenter.sum(p);
		return barycenter.multScal(1d/pts.size());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return x + " " + y + " " + z;
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}
}
