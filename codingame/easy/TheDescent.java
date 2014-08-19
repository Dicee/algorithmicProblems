package easy;

import java.util.Scanner;

class TheDescent {

	public static int sx;
	public static int sy;
	
	public static final int BOUND_RIGHT = 7;
	public static final int BOUND_LEFT = 0;
	
    public static void main(String args[]) {
        @SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
        int nMountains = 8;
        int[] mountains = new int[nMountains];
        
        
        boolean directionRight = true;
        boolean fired = false;
        
        while (true) {
            int x = in.nextInt();
            int y = in.nextInt();
            
            boolean changeLine = y < sy;
            
            if (changeLine)
            	fired = false;
            
            sx = x;
            sy = y;
            
            int indexHighest = 0;
            int highest = 0;
            
            for (int i=0 ; i<nMountains ; i++) {
            	mountains[i] = in.nextInt();
            	if (mountains[i] > highest) 
            		highest = mountains[indexHighest = i];            	
            }
            
            String output = "HOLD";
            if (!fired) {
            	int flyingOverIndex = directionRight ? sx : nMountains - 1 - sx;
            	output = flyingOverIndex == indexHighest ? "FIRE" : output;            		
            }

            System.out.println(output);
        }
    }
}