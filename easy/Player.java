package codingame.easy;
import java.util.*;
import java.io.*;
import java.math.*;

class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        int nNodes = in.nextInt();
        int nLinks = in.nextInt();
        int nExits = in.nextInt();
        
        int[]   exits = new int[nExits];
        int[][] links = new int[nLinks][];
        
        for (int i=0 ; i<nLinks ; i++)
        	links[i] = new int[] { in.nextInt(),in.nextInt() };
        
        for (int i=0 ; i<nExits ; i++)
        	exits[i] = in.nextInt();
        
        while (true) {
        	//Determine the closest accessible exit
        	int[] steps = new int[nLinks];
        	boolean updated;
        	do {
        		updated = false;
        		for (int i=0 ; i<nNodes ; i++)
        			for (int j=0 ; j<nLinks ; j++)
        				if (links[j][0] == i) {
        					updated = true;
        					steps[j]++;
        				}        					
        	} while (updated);
        	
        	int min  = Integer.MAX_VALUE;
        	int imin = 0;
        	for (Integer exit : exits)
        		if (steps[exit] < min) {
        			min  = steps[exit];
        			imin = exit;
        		}
            System.out.println("0 1");
        }
    }
}