package miscellaneous.subdivisionAlgorithms.catmullClark;

import java.util.ArrayList;
import java.util.List;

/**
 * This class enables to represent a Face composed with any number of vertices. 
 * @author David Courtinot
 */

public class Face {
	private final ArrayList<Point> vertices = new ArrayList<>();
	private final ArrayList<Edge>  edges 	= new ArrayList<>();

	/**
	 * Add a vertice to the Face and create a corresponding Edge if appliable.
	 * This should be the only way to add a vertice to a Face.
	 * @param vertice
	 * @param lastVertice indicates whether this vertice is the last to be added
	 * 	      or not. It is required in order to create the last edge.
	 */
	public void addVertice(Point vertice, boolean lastVertice) {
		vertices.add(vertice);
		int lastIndex = vertices.size() - 1;
		if (lastIndex >= 1) {
			edges.add(new Edge(vertices.get(lastIndex - 1),vertices.get(lastIndex)));
			if (lastVertice)
				edges.add(new Edge(vertices.get(lastIndex),vertices.get(0)));				
		}
	}
	
	/**
	 * Computes the center of the Face.
	 * @return center of the Face
	 */
	public Point getCenter() {
		Point result = new Point();
		for (Point p : vertices)
			result = result.sum(p);
		return result.multScal(1d/vertices.size());
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("f ");
		for (Point p : vertices)
			sb.append((p.getId() + 1) + " ");
		return sb.toString().trim();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((vertices == null) ? 0 : vertices.hashCode());
		return result;
	}	
	
	@Override
	public Object clone() {
		Face result = new Face();
		result.vertices.addAll(vertices);
		return result;
	}
	
	/**
	 * Vertices getter. This list should NEVER be modified and is meant to be
	 * used for read-only operations.
	 * @return the vertices of the face
	 */
	public List<Point> getVertices() {
		return vertices;
	}
	
	public List<Edge> getEdges() {
		return edges;
	}
}
