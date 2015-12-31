package miscellaneous.subdivisionAlgorithms.catmullClark;

/**
 * This class enables to represent an Edge from two vertices. Nothing particular to say about it.
 * @author David Courtinot
 */

public class Edge {
	//We assume that this array has always only two elements
	private final Point[] vertices;
	
	/**
	 * Constructs an instance of Edge which vertices are p0 and p1.
	 * @param p0
	 * @param p1
	 */
	public Edge(Point p0, Point p1) {
		this.vertices = new Point[] { p0,p1 };
	}

	public Point[] getVertices() {
		return vertices;
	}

	/**
	 * Computes the center of the Edge.
	 * @return the center of the Edge
	 */
	public Point getCenter() {
		return Point.sum(vertices[0],vertices[1]).multScal(1d/2);
	}
	
	public boolean contains(Point p) {
		return vertices[0].equals(p) || vertices[1].equals(p);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int 	  sum   = 0;
		for (Point v : vertices)
			sum += v.hashCode();
		return prime + sum;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;		
		Point v0   = vertices[0];
		Point v1   = vertices[1];
		Point vo0  = other.vertices[0];
		Point vo1  = other.vertices[1];
		//Edge(v0,v1) and Edge(v1,v0) are considered to be the same edge
		return  (v0.equals(vo0) && v1.equals(vo1)) || 
				(v0.equals(vo1) && v1.equals(vo0));
	}	
	
	@Override
	public String toString() {
		return "Edge(" + (vertices[0].getId() + 1) + ", " + (vertices[1].getId() + 1) + ")";
	}
}
