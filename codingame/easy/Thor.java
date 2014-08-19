package codingame.easy;

import java.util.Scanner;
import java.awt.Point;

class Thor {
	
	public static Point voisin(Point p, String dir) {
		switch (dir) {
		case "N" :
			return new Point(p.x,p.y - 1);
		case "S" :
			return new Point(p.x,p.y + 1);
		case "W" :
			return new Point(p.x - 1,p.y);
		case "E" :
			return new Point(p.x + 1,p.y);
		case "NE" :
			return new Point(p.x + 1,p.y - 1);
		case "NW" :
			return new Point(p.x - 1,p.y - 1);
		case "SE" :
			return new Point(p.x + 1,p.y + 1);
		case "SW" :
			return new Point(p.x - 1,p.y + 1);
		default :
			throw new IllegalArgumentException();
		}
	}
	
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int lx = in.nextInt();
        int ly = in.nextInt();
        int tx = in.nextInt();
        int ty = in.nextInt();
        
        String dir;
        while (true) {
        	if (tx < lx)
        	    dir = ty < ly ? "SE" : ty == ly ? "E" : "NE";
        	else if (tx > lx)
        	    dir = ty < ly ? "SW" : ty == ly ? "W" : "NW";
        	else 
        	    dir = ty < ly ? "S" : "N";
        	Point pos = voisin(new Point(tx,ty),dir);
        	tx = pos.x;
        	ty = pos.y;
        	if (tx == lx && ty == ly)
        		in.close();
            System.out.println(dir);
        }
    }
}