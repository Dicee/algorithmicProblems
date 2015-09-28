package catmullClark;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CatmullClark {
	private Mesh mesh;
	private Map<Edge,Point> edgePoints;
	private Map<Face,Point> facePoints;
	private List<ArrayList<Integer>> neighbourFaces;
	
	public CatmullClark() { }

	private Face[] getNeighbourFaces(Edge edge) {
		Point[] vertices      = edge.getVertices();
		Face[] neighbourFaces = new Face[2];		
		List<Face> faces      = mesh.getFaces();
		for (int i=0 ; i<2 ; i++) {
			ArrayList<Integer> facesIndexes = mesh.getNeighbourFaces().get(vertices[i].getId());
			for (Integer faceIndex : facesIndexes) {
				Face face = faces.get(faceIndex);
				if (face.getEdges().contains(edge)) {
					if (i == 0 || (i == 1 && !face.equals(neighbourFaces[0]))) {
						neighbourFaces[i] = face;
						break;
					}
				}
			}
		}
		return neighbourFaces;
	}
	
	private void computeEdgePoints() {
		edgePoints = new HashMap<>();
		for (Edge edge : mesh.getEdges()) {
			Face[] neighbourFaces = getNeighbourFaces(edge);			
			List<Point> pts       = new ArrayList<>();
			
			pts.add(neighbourFaces[0].getCenter());
			pts.add(neighbourFaces[1].getCenter());
			pts.add(edge.getCenter());
			
			Point barycenter = Point.getBarycenter(pts);
			mesh.register(barycenter);
			edgePoints.put(edge,barycenter);
		} 
	}	
	
	private void computeFacePoints() {
		facePoints = new HashMap<>();
		for (Face face : mesh.getFaces()) {			
			Point facePoint = face.getCenter();
			mesh.register(facePoint);
			facePoints.put(face,facePoint);
		}
	}
	
	private List<Face> createNewFaces() {
		neighbourFaces       = new ArrayList<>();
		List<Face>  newFaces = new ArrayList<>();
		List<Point> pts      = mesh.getPoints();
		
		//Initialize the new vertice-faces mapping
		int nPts = pts.size();
		for (int i=0 ; i<nPts ; i++)
			neighbourFaces.add(new ArrayList<Integer>());
		
		int k = 0;
		for (Face face : mesh.getFaces()) { 
			//Retrieve the surrounding (registered to the mesh) edge points
			List<Point> surroundingEP = new ArrayList<>();
			for (Edge edge : face.getEdges())
				surroundingEP.add(edgePoints.get(edge));		
						
			List<Point> vertices = face.getVertices();
			int   n              = vertices.size();
			for (int i=0 ; i<n ; i++) {
				Face newFace = new Face();
				
				Point p = vertices.get(i);
				newFace.addVertice(p,false);
				neighbourFaces.get(p.getId()).add(k);
				
				p = surroundingEP.get(i);
				newFace.addVertice(p,false);
				neighbourFaces.get(p.getId()).add(k);				
				
				p = facePoints.get(face);
				newFace.addVertice(p,false);
				neighbourFaces.get(p.getId()).add(k);
				
				p = surroundingEP.get(i == 0 ? surroundingEP.size() - 1 : i - 1);
				newFace.addVertice(p,true);
				neighbourFaces.get(p.getId()).add(k);
				
				newFaces.add(newFace);
				k++;
			}
		}
		return newFaces;
	}
	
	private void updateFormerPoints(int indexFormerPoints) {
		List<Face> faces        = mesh.getFaces();
		List<Point> newVertices = new ArrayList<>();
		
		for (int i=0 ; i<indexFormerPoints ; i++) {	
			Point p = mesh.getPoint(i);
			
			//Compute the barycenters of the surrounding face and edge points
			ArrayList<Integer> facesIndexes     = mesh.getNeighbourFaces().get(i);
			Set<Edge>          surroundingEdges = new HashSet<>(); 
			int 			   totalWeight      = facesIndexes.size();
			Point fpBar = new Point();
			Point epBar = new Point();
			for (Integer faceIndex : facesIndexes) {
				Face face = faces.get(faceIndex);
				fpBar     = fpBar.sum(face.getCenter());
				for (Edge edge : face.getEdges())
					if (edge.contains(p))
							surroundingEdges.add(edge);
			}
			fpBar = fpBar.multScal(1d/totalWeight);
			
			//There's still to compute the barycenter of the edge points, which is
			//easy now that we know all the surrounding edges
			for (Edge edge : surroundingEdges) 
				epBar = epBar.sum(edgePoints.get(edge));
			epBar = epBar.multScal(2d/totalWeight);
			
			Point newVertice = new Point();
			newVertice       = newVertice.sum(epBar);
			newVertice       = newVertice.sum(fpBar);
			newVertice       = newVertice.sum(p.multScal(totalWeight - 3));
			newVertice       = newVertice.multScal(1d/totalWeight);
			newVertices.add(newVertice);
		}
		
		//We finally replace the former coordinates of the old vertices by their new coordinates
		List<Point> vertices = mesh.getPoints();
		for (int i=0 ; i<indexFormerPoints ; i++) 
			vertices.get(i).setLocation(newVertices.get(i));		
	}
	
	private void updateMeshFaces(List<Face> newFaces) {
		Set<Edge> newEdges = new HashSet<>();
		for (Face face : newFaces) 
			newEdges.addAll(face.getEdges());
		mesh.setFaces(newFaces);
		mesh.setEdges(newEdges);
		mesh.setNeighbourFaces(neighbourFaces);
	}
	
	public Mesh subdivide(int steps, Mesh mesh, String path) {
		this.mesh = mesh;
		for (int i=0 ; i<steps ; i++) {
			int indexFormerPoints = mesh.getPoints().size();
			computeFacePoints();
			computeEdgePoints();
			
			List<Face> newFaces = createNewFaces();
			updateFormerPoints(indexFormerPoints);
			updateMeshFaces(newFaces);
			
			String name = path.substring(0,path.lastIndexOf('.'));
			mesh.save(new File(name + "+" + i + ".obj"));
		}			
		return mesh;		
	}
	
	public static void main(String[] args) {
		String path = "C:/Users/David/Documents/MATLAB/Projet/P5/trumpet.obj";
		Mesh mesh = Mesh.readInput(new File(path));
		CatmullClark cc = new CatmullClark();
		cc.subdivide(2,mesh,path);
	}
}
