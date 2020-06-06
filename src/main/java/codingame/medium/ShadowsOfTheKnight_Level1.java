package codingame.medium;

import java.util.Scanner;

class ShadowsOfTheKnight_Level1 {

    public static void main(String args[]) {
        @SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
        int w = in.nextInt();
        int h = in.nextInt();
        in.nextInt();
        int x = in.nextInt();
        int y = in.nextInt();
        
        int xmin = 0, xmax = w - 1;
        int ymin = 0, ymax = h - 1;
        
        in.nextLine();
        while (true) {
        	String dir = in.nextLine();
        	
        	switch (dir) {
        		case "R" : 
        			xmin = x + 1;
        			ymin = y;
        			ymax = y;
        			break;
        		case "L" :
        			xmax = x - 1;
        			ymin = y;
        			ymax = y;
        			break;
        		case "U" :
        			ymax = y - 1;
        			xmin = x;
        			xmax = x;
        			break;
        		case "D" :
        			ymin = y + 1;
        			xmin = x;
        			xmax = x;
        			break;
        		case "UL" :
        			xmax = x - 1;
        			ymax = y - 1;
        			break;
        		case "UR" :
        			xmin = x + 1;
        			ymax = y - 1;
        			break;
        		case "DL" :
        			xmax = x - 1;
        			ymin = y + 1;
        			break;
        		case "DR" :
        			xmin = x + 1;
        			ymin = y + 1;
        			break;
        	}
        	x = (xmax + xmin)/2;
        	y = (ymax + ymin)/2;
        	
            System.out.println(String.format("%d %d",x,y));
        }
    }
}