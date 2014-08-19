package codingame.harder;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class Surface {
	
	private static int width, height;
	private static int[][] map;
	
	private enum Direction {
		SOUTH( 1, 0),
		EAST ( 0, 1),
		NORTH(-1, 0),
		WEST ( 0,-1);
		
		public int x, y;
		
		private Direction (int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public static boolean isLegal(Point p, Direction dir) {	    	
			switch (dir) {
				case SOUTH : 		
					return p.x < height - 1;
				case EAST  :
					return p.y < width - 1;
				case NORTH :
					return p.x > 0;
				default    :
					return p.y > 0;
			}
		}
	}	
	
	private static void fillLake(Point p) {
	    if (map[p.x][p.y] == -1) {
    	    HashSet   <Point> toMark  = new HashSet<>();
    	    LinkedList<Point> toCheck = new LinkedList<>();
    	    int               surface = 1;
    	
    	    toCheck.add(p);
    	    toMark.add(p);

    	    while (!toCheck.isEmpty()) {
    		    Point q = toCheck.pollFirst();
    		    for (Direction dir : Direction.values())
    			    if (Direction.isLegal(q,dir)) {
    				    Point next = new Point(q.x + dir.x,q.y + dir.y);
    			    	if (map[next.x][next.y] == -1 && toMark.add(next)) {
    					    toCheck.offerFirst(next);
    				    	surface++;
    				    }
    		    	}
    	    }
    	    for (Point q : toMark) 
    		    map[q.x][q.y] = surface;
	    }
    	System.out.println(map[p.x][p.y]);
    }   
	
	public static Point mapToArr(Point p) {
	    return new Point(p.y,p.x);
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		width      = in.nextInt();
		height     = in.nextInt();
		map        = new int[height][width];
		
		in.nextLine();
		for (int i=0 ; i<height ; i++) {
			String line = in.nextLine();
			for (int j=0 ; j<width ; j++)
				map[i][j] = line.charAt(j) == 'O' ? -1 : 0;
		}
		
		int n = in.nextInt();
		for (int i=0 ; i<n ; i++) {
		    int x = in.nextInt();
		    int y = in.nextInt();
			fillLake(mapToArr(new Point(x,y)));
		}
		in.close();
	}
}