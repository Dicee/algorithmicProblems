package easy;

import java.util.Scanner;

class TheChasm {

    public static void main(String args[]) {
        @SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);

        int beforeHole = in.nextInt();
        int holeLength = in.nextInt();
        in.nextInt();
        
        while (true) {
            int speed = in.nextInt();
            int x     = in.nextInt();
            
            String output;
            if (x == beforeHole - 1)
            	output = "JUMP";
            else if (x < beforeHole) 
            	output = speed <= holeLength ? "SPEED" : speed > holeLength + 1 ? "SLOW" : "WAIT";            		
             else
            	output = "SLOW";
            
            System.out.println(output);
        }
    }
}