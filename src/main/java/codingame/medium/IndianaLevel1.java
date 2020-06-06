package codingame.medium;

import java.awt.Point;
import java.util.Scanner;

class IndianaLevel1 {

	public enum Direction {
		TOP   ( 0,-1),
		BOTTOM( 0, 1),
		LEFT  (-1, 0),
		RIGHT ( 1, 0);
		
		public int x, y;		
		
		private Direction (int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public Point move(Point p) {
			return new Point(p.x + x,p.y + y);
		}
	}
	
	public enum Case {
		TYPE_0 ( 0),
		TYPE_1 ( 1),
		TYPE_2 ( 2),
		TYPE_3 ( 3),
		TYPE_4 ( 4),
		TYPE_5 ( 5),
		TYPE_6 ( 6),
		TYPE_7 ( 7),
		TYPE_8 ( 8),
		TYPE_9 ( 9),
		TYPE_10(10),
		TYPE_11(11),
		TYPE_12(12),
		TYPE_13(13);
		
		public int type;
		
		private Case(int type) {
			this.type = type;
		}
		
		public Direction move(Direction d) {
		    System.err.println("Move : " + d + " " + type);
			switch (type) {
				case 0  : return null;
				case 1  : return Direction.BOTTOM;
				case 2  : return d;
				case 3  : return d;
				case 4  : return d == Direction.BOTTOM ? Direction.LEFT : d == Direction.RIGHT ? null : Direction.BOTTOM;
				case 5  : return d == Direction.RIGHT ? Direction.BOTTOM : d != Direction.BOTTOM ? null : Direction.RIGHT;
				case 6  : return d == Direction.BOTTOM ? null : d;
				case 7  : return d == Direction.LEFT ? Direction.BOTTOM : d;
				case 8  : return Direction.BOTTOM;
				case 9  : return Direction.BOTTOM;
				case 10 : return d == Direction.RIGHT ? null : Direction.LEFT;
				case 11 : return d == Direction.LEFT ? null : Direction.RIGHT;
				case 12 : return Direction.BOTTOM;
				case 13 : return Direction.BOTTOM;
				default : throw new IllegalArgumentException("This type doesn't exist");
			}
		}
	}
	
    public static void main(String args[]) {
        @SuppressWarnings("resource")
		Scanner  in = new Scanner(System.in);
        int      w  = in.nextInt();
        int      h  = in.nextInt();
        //E si not used in this problem
        Case[][] map = new Case[h][w];
        in.nextLine();
        
        for (int i=0 ; i<h ; i++) {
        	String[] split = in.nextLine().split(" ");
        	for (int j=0 ; j<w ; j++) 
        		map[i][j] = Case.values()[Integer.parseInt(split[j])];
        }
        in.nextLine();
        
        Direction d = null;
        while (true) {
            int x      = in.nextInt();
            int y      = in.nextInt();
            String pos = in.nextLine().trim();
            d          = d == null ? pos.equals("TOP") ? Direction.BOTTOM : pos.equals("LEFT") ? 
            					Direction.RIGHT : Direction.LEFT : d;
            d          = map[y][x].move(d);
            Point p    = d.move(new Point(x,y));
            System.out.println(p.x + " " + p.y);
        }
    }
}