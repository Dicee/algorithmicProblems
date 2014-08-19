package codingame.medium;

import java.awt.Point;
import java.util.Scanner;

public class Solution {

	public static final double	G						= 3.711;
	public static final int		verticalLandingSpeed	= 40;
	public static final double	horizontalLandingSpeed	= 20;

    @SuppressWarnings({ "unused", "resource" })
    public static void main(String args[]) {
        Scanner in     = new Scanner(System.in);
        int n          = in.nextInt();
        Point[] floor  = new Point[n];
        int xbPlatform = -1;
        int xePlatform = -1;
        
        for (int i=0 ; i<n ; i++) {
        	Point p  = new Point(in.nextInt(),in.nextInt());
            floor[i] = p;
            if (xbPlatform == -1 && i > 0 && p.y == floor[i - 1].y)
            	xbPlatform = floor[i - 1].x;
            else if (xbPlatform != -1 && p.y != floor[i - 1].y)
            	xePlatform = floor[i - 1].x;
        }
    
        int xgoal = (xePlatform + xbPlatform)/2;
        while (true) {
            Point pos = new Point(in.nextInt(),in.nextInt());
            Point v   = new Point(in.nextInt(),in.nextInt());
            int fuel  = in.nextInt();
            int angle = in.nextInt();
			int power = in.nextInt();
            
			int nextAngle, nextPower; 
			if (Math.abs(pos.x - xgoal) > 50) {
			    if (- v.y > verticalLandingSpeed || pos.y < 2500) {
			        nextAngle = Math.max((int) Math.signum(pos.x - xgoal)*10,angle - (int) Math.signum(angle)*15);
			        nextPower = 4;
			    } else {
				    nextAngle = (int) Math.signum(pos.x - xgoal)*Math.max(angle - (int) Math.signum(angle)*15,5);
				    nextAngle = v.x > horizontalLandingSpeed ? Math.max(0,nextAngle - 15) : nextAngle;
			        nextPower = v.x > horizontalLandingSpeed ? 4 : 3;
			    }
			    System.err.println("if");
			} else {
			    System.err.println("else");
				nextAngle = Math.abs(angle) > 15             ? angle - (int) Math.signum(angle)*15 : 0;
				nextPower = - v.y > verticalLandingSpeed - 5 ? 4                                   : 3;
			}
            System.out.println(nextAngle + " " + nextPower);
        }        
    }
}