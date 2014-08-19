package codingame.easy;

import java.awt.Point;
import java.util.Scanner;

class MarsLander1 {

	public static final double	G						= 3.711;
	public static final int		verticalLandingSpeed	= 40;
	public static final double	horizontalLandingSpeed	= 20;

    @SuppressWarnings({ "unused", "resource" })
    public static void main(String args[]) {
        Scanner in    = new Scanner(System.in);
        int n         = in.nextInt();
        Point[] floor = new Point[n];
        
        for (int i=0 ; i<n ; i++)
            floor[i] = new Point(in.nextInt(),in.nextInt());
    
        while (true) {
            Point pos = new Point(in.nextInt(),in.nextInt());
            Point v   = new Point(in.nextInt(),in.nextInt());
            int fuel  = in.nextInt();
            int angle = in.nextInt();
			int power = in.nextInt();
            
			int nextAngle, nextPower; 
            nextAngle = Math.abs(angle) > 15             ? angle - (int) Math.signum(angle)*15 : 0;
            nextPower = - v.y > verticalLandingSpeed - 5 ? 4                                   : 3;
            System.out.println(nextAngle + " " + nextPower);
        }        
    }
}