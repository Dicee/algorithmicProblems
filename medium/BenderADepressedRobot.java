package codingame.medium;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class BenderADepressedRobot {
	
	public enum Direction {
		SOUTH( 1, 0),
		EAST ( 0, 1),
		NORTH(-1, 0),
		WEST ( 0,-1);
		
		public int x, y;
		
		private Direction (int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	public enum Case {
		BEER        ("B"),
		UNBROKABLE  ("#"),
		BROKABLE    ("X"),
		START       ("@"),
		SUICIDE     ("$"),
		TELEPORT    ("T"),
		INVERSOR    ("I"),
		CHANGE_NORTH("N"),
		CHANGE_SOUTH("S"),
		CHANGE_EAST ("E"),
		CHANGE_WEST ("W"),
		BLANK       (" ");
		
		private String	symbol;

		private Case(String symbol) {
			this.symbol = symbol;
		}
		
		public static Case getCase(String symbol) {
			for (Case c : values())
				if (c.symbol.equals(symbol))
					return c;
			return null;
		}
	}
	
	private static class State {
		
		private Case[][] map;
		private Direction dir;
		private Point pos;
		private boolean	inversed;
		private boolean	enraged;
		
		public State(Case[][] map, Direction dir, Point pos, boolean inversed, boolean enraged) {
			this.map      = map;
			this.dir      = dir;
			this.pos      = pos;
			this.inversed = inversed;
			this.enraged  = enraged;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((dir == null) ? 0 : dir.hashCode());
			result = prime * result + (enraged ? 1231 : 1237);
			result = prime * result + (inversed ? 1231 : 1237);
			result = prime * result + Arrays.hashCode(map);
			result = prime * result + ((pos == null) ? 0 : pos.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			State other = (State) obj;
			if (dir != other.dir)
				return false;
			if (enraged != other.enraged)
				return false;
			if (inversed != other.inversed)
				return false;
			if (!Arrays.deepEquals(map, other.map))
				return false;
			if (pos == null) {
				if (other.pos != null)
					return false;
			} else if (!pos.equals(other.pos))
				return false;
			return true;
		}
	}
		
	public static Direction changeDirection(Point current, Direction d) {
		Case newCase = map[current.x + d.x][current.y + d.y];
		if (enraged && newCase == Case.BROKABLE) {
			map[current.x + d.x][current.y + d.y] = Case.BLANK;
			return d;
		}
		int begin = !inversed ? 0 : 3;
		int end   = 3 - begin;
		int it    = begin < end ? 1 : -1;
		int i     = begin;
		
		Direction[] dirs = orderedDirs;		
		while (i != end + it) {
			newCase = map[current.x + dirs[i].x][current.y + dirs[i].y];
			if (enraged && newCase == Case.BROKABLE) {
				map[current.x + dirs[i].x][current.y + dirs[i].y] = Case.BLANK;
				return dirs[i];
			} else if (newCase != Case.UNBROKABLE && newCase != Case.BROKABLE) 
				return dirs[i];
			i += it;
		}
		return null;	
	}

	public static Case[][]		map;
	public static boolean		enraged		= false;
	public static boolean		inversed	= false;
	public static Direction[]	orderedDirs	= Direction.values();

	public static void main(String[] args) {
		Scanner in    = new Scanner(System.in);
		int L         = in.nextInt();
		int C         = in.nextInt();
		
		Point current = null;
		map           = new Case[L][C];
		Point[] telep = new Point[2];
		int k         = 0;
		
		in.nextLine();
		for (int i=0 ; i<L ; i++) {
			int j=0;
			String[] line = in.nextLine().split("");
			for (String symbol : line) {
			    if (!symbol.isEmpty()) {
				    map[i][j] = Case.getCase(symbol);
				    if (map[i][j] == Case.START)
				    	current    = new Point(i,j);
				    else if (map[i][j] == Case.TELEPORT)
					    telep[k++] = new Point(i,j);
				    j++;
			    }
			}
		}	
		in.close();
			
		HashSet<State> states = new HashSet<>();
		List<Direction> moves = new ArrayList<>();
		Direction dir         = Direction.SOUTH;
		states.add(new State(map.clone(),dir,current,inversed,enraged));
				
		while (map[current.x][current.y] != Case.SUICIDE) {
			int x = current.x, y = current.y;
			switch (map[x][y]) {
				case CHANGE_NORTH :
					dir = Direction.NORTH;
					break;
				case CHANGE_SOUTH :
					dir = Direction.SOUTH;
					break;
				case CHANGE_EAST :
					dir = Direction.EAST;
					break;
				case CHANGE_WEST :
					dir = Direction.WEST;
					break;
				case BEER :
					enraged = !enraged;
					break;
				case INVERSOR :
					inversed = !inversed;
					break;
				case TELEPORT :
					Point p = new Point(x,y);
					current = telep[0].equals(p) ? telep[1] : telep[0];
				default :
					break;
			}
			
			Case keepDir = map[current.x + dir.x][current.y + dir.y];
			if (keepDir == Case.BROKABLE || keepDir == Case.UNBROKABLE) {
			    dir = changeDirection(current,dir);
			}
			current = new Point(current.x + dir.x,current.y + dir.y);
			moves.add(dir);
			
			if (!states.add(new State(map.clone(),dir,current,inversed,enraged))) {
			    moves.clear();
			    System.out.println("LOOP");
			    break;
			}
		}
		for (Direction direction : moves) 
			System.out.println(direction);
	}
}
