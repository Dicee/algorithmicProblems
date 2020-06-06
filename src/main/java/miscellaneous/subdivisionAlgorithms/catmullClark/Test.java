package miscellaneous.subdivisionAlgorithms.catmullClark;

import java.io.File;

public class Test {
	public static void main(String[] args) {
		//Replace by your own path
		String path        = "C:/Users/David/Documents/MATLAB/Projet/P5/";	
		String[] fileNames = { "cube","parallelepipede","polyhedre","cubeCreux","T","lamp","violinCase","cow","dragon" };
		int[] nsub         = { 4,4,4,4,4,2,2,2,1 };		
		CatmullClark cc    = new CatmullClark();		
		int i              = 0;
		
		for (String name : fileNames) {
			String fullPath = path + name + ".obj";
			Mesh mesh       = Mesh.readInput(new File(fullPath));
			System.out.println(String.format("Test n°%d : %s\nInitial mesh : ",i,name));
			mesh.countElements();
			mesh            = cc.subdivide(nsub[i++],mesh,fullPath);
			System.out.println("Final mesh : ");
			mesh.countElements();
			System.out.println();
		}
	}
}
