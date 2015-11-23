package catmullClark;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This class enables to represent a surface Mesh, create a Mesh from a .obj file and save a Mesh
 * to a .obj file. This implementation stores a lot of redundant data (faces, vertices, edges and
 * faces surrounding a vertice) in order to ensure a quick access to topologic information.
 * @author David Courtinot
 */

public class Mesh {
	private int						currentId		= 0;
	private List<Face>					faces			= new ArrayList<>();
	private List<Point>					pts			= new ArrayList<>();
	private List<ArrayList<Integer>>			neighbourFaces		= new ArrayList<>();
	private Set<Edge>					edges			= new HashSet<>();

	public Mesh() { }
	
	/**
	 * Registers a vertice to the Mesh, meaning that the point will be given
	 * an id and stored in the vertices list
	 * @param p
	 */
	public void register(Point p) {
		p.setId(currentId++);
		pts.add(p);
	}
	
	/**
	 * Registers a vertice to the Mesh, meaning that the point will be given the specified
	 * an id and stored in the vertices list
	 * @param p
	 * @param id
	 */
	public void register(Point p, int id) {
		p.setId(id);
		pts.add(p);
	}
	
	/**
	 * Build a Mesh from a .obj file.
	 * @param f
	 * @return the Mesh corresponding to the .obj description in the file
	 */
	public static Mesh readInput(File f) {
		BufferedReader br     = null;
		Mesh           result = null;
		try {
			br                = new BufferedReader(new FileReader(f));
			result            = new Mesh();			
			List<Face> faces  = result.getFaces();
			int        nFaces = 0;
			
			String matchesDouble  = "(-)?[0-9]+(\\.[0-9]*)?+(E(-)?[0-9]*)?+";
			String matchesFace    = String.format("f(\\s+%s)+","[0-9]+");
			String matchesVertice = String.format("v(\\s+%s){3}+",matchesDouble);
			
			String line;
			while ((line = br.readLine()) != null) 
				if (line.matches(matchesVertice)) {
					result.register(readVertice(line));
					result.neighbourFaces.add(new ArrayList<Integer>());
				} else if (line.matches(matchesFace)) {
					Face face = readFace(line,result.getPoints());
					faces.add(face);	
					result.edges.addAll(face.getEdges());
					for (Point vertice : face.getVertices())
						result.neighbourFaces.get(vertice.getId()).add(nFaces);
					nFaces++;
				} else 
					System.out.println(String.format("Ignored : %s",line));
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)	br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
		return result;
	}	
	
	/**
	 * Save a Mesh in a .obj file.
	 * @param f
	 */
	public void save(File f) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(f));		
			for (Point p : pts) 
				bw.write("v " + p.toString()  + "\n");
			
			for (Face face : faces)
				bw.write(face.toString() + "\n");
		} catch (IOException e) {
			e.printStackTrace();			
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Converts a quad mesh into a tri mesh and saves it in a file which name
	 * is the same that the input one's, appended with "Tri".
	 * @param f file containing a .obj representation of a quad mesh
	 * @return a file containing an equivalent tri mesh
	 */
	public static File quadToTri(File f) {
		BufferedReader br = null;
		BufferedWriter bw = null;
		File result       = null;
		try {
			br                    = new BufferedReader(new FileReader(f));
			String path           = f.getAbsolutePath().substring(0,f.getAbsolutePath().lastIndexOf("."));
			bw                    = new BufferedWriter(new FileWriter(result = new File(path + "Tri.obj")));
			
			String matchesDouble  = "(-)?[0-9]+(\\.[0-9]*)?+(E(-)?[0-9]*)?+";
			String matchesFace    = String.format("f(\\s+%s){4}+","[0-9]+");
			String matchesVertice = String.format("v(\\s+%s){3}+",matchesDouble);
			List<Point> vertices  = new ArrayList<>();			
			int id                = 0;
			
			String line;
			while ((line = br.readLine()) != null) {
				if (line.matches(matchesVertice)) {
					Point vertice = readVertice(line);
					vertice.setId(id++);
					vertices.add(vertice);
					bw.write(String.format("v %s\n",vertice.toString()));
				} else if (line.matches(matchesFace)) {
					Face   quad = readFace(line,vertices);
					Face[] tris = { new Face(),new Face() };					
					int    size = quad.getVertices().size();
					
					for (int i=0 ; i<size ; i++) {
						Point p = quad.getVertices().get(i);
						if (i < size - 1) 
							tris[0].addVertice(p,i == size - 2);
						if (i > 1) {
							tris[1].addVertice(p,false);
							if (i == size - 1)
								tris[1].addVertice(quad.getVertices().get(0),true);
						}
					}
					for (Face tri : tris)
						bw.write(tri.toString() + "\n");
				} else 
					System.out.println(String.format("Ignored : %s",line));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)	br.close();
				if (bw != null)	bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * Converts a tri mesh from a .obj file to a colored tri mesh in format .off.
	 * The vertices get their colors according to their coordinates relatively to
	 * the other vertices.
	 * @param f input file containing a tri mesh
	 * @return file containing a .obj representation of a colored tri mesh
	 */
	public static File triToOff(File input, File output) {
		BufferedReader br         = null;
		BufferedWriter bw         = null;
		try {
			br                    = new BufferedReader(new FileReader(input));
			
			String matchesDouble  = "(-)?[0-9]+(\\.[0-9]*)?+((E|e)(-)?[0-9]*)?+";
			String matchesFace    = String.format("f(\\s+%s){3}+","[0-9]+");
			String matchesVertice = String.format("v(\\s+%s){3}+",matchesDouble);
			List<Point> vertices  = new ArrayList<>();			
			List<Face> faces      = new ArrayList<>();			
			int id                = 0;
			
			double[] maxPosCoords = { Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE };
			double[] maxNegCoords = { Double.MIN_VALUE,Double.MIN_VALUE,Double.MIN_VALUE };
			
			String line;
			while ((line = br.readLine()) != null) 
				if (line.matches(matchesVertice)) {
					Point vertice = readVertice(line);
					vertice.setId(id++);
					vertices.add(vertice);
					
					double[] coords = { vertice.x,vertice.y,vertice.z };
					for (int i=0 ; i<maxPosCoords.length ; i++)
						if (coords[i] >= 0)
							maxPosCoords[i] = Math.max(maxPosCoords[i],coords[i]);
						else
							maxNegCoords[i] = Math.max(maxNegCoords[i],- coords[i]);
				} else if (line.matches(matchesFace)) {
					faces.add(readFace(line,vertices));					
				} else 
					System.out.println(String.format("Ignored : %s",line));
			
			//Once all the vertices and faces have been retrieved, we can finally count them and
			//write them in the file
			bw                    = new BufferedWriter(new FileWriter(output));
			bw.write("OFF\n");
			bw.write(vertices.size() + " " + faces.size() + "\n");
						
			double[] coordsInc = { 0,0,0 };
			double[] maxCoords = new double[3];
			
			/*
			 * For each coordinate x, y, z, we determined the interval [ - maxNeg,maxPos ] which
			 * contains all the values. We then have to compute max = max(maxNeg,maxPos) to move all the
			 * values in [ 0,maxPos + max ]. Finally, we normalize by maxPos + max to obtain the
			 * vertice's color.
			 */
			for (int i=0 ; i<maxPosCoords.length ; i++) {
				double maxPos = maxPosCoords[i];
				double maxNeg = maxNegCoords[i];
				coordsInc[i]  = Math.max(maxPos,maxNeg);
				maxCoords[i]  = maxPos + coordsInc[i];
			}

			for (Point vertice : vertices) {
				double[] coords = new double[3];
				coords[0]       = vertice.x;
				coords[1]       = vertice.y;
				coords[2]       = vertice.z;					
				String colors   = "";
			
				for (int i=0 ; i<coords.length ; i++) {
					coords[i] += coordsInc[i];
					coords[i] /= maxCoords[i];
					colors    += coords[i] + " ";
				}
				
				bw.write(String.format("%s %s\n",vertice.toString(),colors.trim()));
			}
			
			for (Face face : faces) {
				String coords = "3";
				for (Point p : face.getVertices())
					coords += " " + p.getId();
				bw.write(coords + "\n");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)	br.close();
				if (bw != null)	bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output;
	}
	
	public static File triToOff(File f) {
		String path = f.getAbsolutePath().substring(0,f.getAbsolutePath().lastIndexOf("."));
		return triToOff(f,new File(path + ".off"));
	}
	
	private static Face readFace(String faceExpr, List<Point> pts) {
		Face     face   = new Face();
		String[] split  = faceExpr.split("\\s+");
		
		for (int i=1 ; i<split.length ; i++) 
			face.addVertice(pts.get(Integer.parseInt(split[i]) - 1),i == split.length - 1);		
		return face;
	}
	
	private static Point readVertice(String verticeExpr) {
		String[] split  = verticeExpr.split("\\s+");
		double[] coords = new double[3];
		
		for (int i=1 ; i<split.length ; i++)
			coords[i - 1] = Double.parseDouble(split[i]);
		return new Point(coords[0],coords[1],coords[2]);
	}
	
	public void countElements() {
		System.out.println(String.format("There are :\n- %d vertices\n- %d edges\n- %d faces",
				pts.size(),edges.size(),faces.size()));
	}
		
	public List<Face> getFaces() {
		return faces;
	}
	
	public Point getPoint(int id) {
		return pts.get(id);
	}
	
	public List<Point> getPoints() {
		return pts;
	}

	public List<ArrayList<Integer>> getNeighbourFaces() {
		return neighbourFaces;
	}

	public Set<Edge> getEdges() {
		return edges;
	}

	public void setFaces(List<Face> faces) {
		this.faces = faces;
	}

	public void setEdges(Set<Edge> edges) {
		this.edges = edges;
	}

	public void setNeighbourFaces(List<ArrayList<Integer>> neighbourFaces) {
		this.neighbourFaces = neighbourFaces;
	}
	
	public static void main(String[] args) {
		String inputPath  = "C:\\Users\\David\\Documents\\MATLAB\\Projet\\P5\\trumpet+1.obj";
		String outputPath = "C:\\Java work\\Rendu\\src\\data\\trumpet.off";
		boolean quadMesh  = true;
	
		if (quadMesh)
			//J'ai pas voulu faire une méthode qui passe directement de quad à off,
			//donc on perdra un peu de temps à relire tous les sommets et faces
			Mesh.triToOff(Mesh.quadToTri(new File(inputPath)),new File(outputPath));
		else
			Mesh.triToOff(new File(inputPath),new File(outputPath));
	}
}
