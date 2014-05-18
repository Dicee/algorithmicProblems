package codingame.harder;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

class KirkLabyrinth {
	public static enum Case {
		NOT_SCANNED ('?'),
		WALL        ('#'),
		EMPTY       ('.'),
		START       ('T'),
		COMMAND_ROOM('C');
		
		public char symbol;
		
		private Case(char c) {
			symbol = c;
		}
		
		public static Case getCase(char ch) {
			for (Case c : values())
				if (c.symbol == ch)
					return c;
			return null;
		}
	}
	
	public enum Direction {
		DOWN ( 1, 0),
		RIGHT( 0, 1),
		UP   (-1, 0),
		LEFT ( 0,-1);

		public int x, y;

		private Direction(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		private Point move(Point p) {
			return new Point(p.x + x,p.y + y);
		}
		
		public Point tryMove(Point p) {
			return isLegal(p) ? move(p) : null;
		}
		
		private boolean isLegal(Point p) {
			Point q = move(p);
			return q.x < lines && q.y < cols && q.x >= 0 && q.y >= 0;
		}
		
		public Direction opposed() {
		    switch (this) {
		        case UP   : return DOWN;
		        case DOWN : return UP;
		        case LEFT : return RIGHT;
		        default   : return LEFT;
		    }
		}
	}
	
	public static class Node {
		public final Direction	previous;
		public final Node       parent;
		public final int		dist;
		public final Point		pos;
		
		public Node(Point pos, Direction previous, Node parent) {
			this.pos      = pos;
			this.dist     = parent == null ? 0 : parent.dist + 1;
			this.parent   = parent;
			this.previous = previous;
		}
		
		public Node(Point pos) {
			this(pos,null,null);
		}
		
		public List<Direction> getPath() {
			List<Direction> result = new ArrayList<>();
			Node            node   = this;
			while (node.parent != null) {
				result.add(node.previous);
				node = node.parent;			
			}
			Collections.reverse(result);
			return result;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
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
			Node other = (Node) obj;
			if (pos == null) {
				if (other.pos != null)
					return false;
			} else if (!pos.equals(other.pos))
				return false;
			return true;
		}
	}
	
	private static class NodeComparator implements Comparator<Node> {
		private Point end;
		
		public NodeComparator(Point end) {
			this.end = end;
		}
		
		@Override
		public int compare(Node n1, Node n2) {
			return Double.compare(n1.dist + distance(n1.pos,end),
								  n2.dist + distance(n2.pos,end));
		}
	}
	
	public static double distance(Point p, Point q) {
		return Math.abs(p.x - q.x) + Math.abs(p.y - q.y);
	}	

	public static List<Direction> getPath(Point start, Point end) {		
		Set<Node>   explored = new HashSet<>();
		Queue<Node> seen     = new PriorityQueue<>(1000,new NodeComparator(end));
		Node        node     = new Node(start);
		explored.add(node);
		seen.add(node);
		
		while (node != null && !node.pos.equals(end)) {
			for (Direction d : Direction.values()) {
				Point q       = d.tryMove(node.pos);
				Node  newNode = new Node(q,d,node);
				if (q != null) {
					boolean legal = cases[q.x][q.y] != Case.NOT_SCANNED &&  
							cases[q.x][q.y] != Case.WALL;
					if (legal && explored.add(newNode))
						seen.add(newNode);
				}
			}
			node = seen.poll();
		}
		return node == null ? null : node.getPath();
	}
	
	public static Direction reachCommandRoom(Point pKirk) {
		Direction output = null;
		if (commandRoom != null) {
		    List<Direction> pathBack = new LinkedList<>(getPath(commandRoom,entry));
		    //We are still not enable to return to the entry before the alarm rings so
		    //we need to explore further and not to reach the command room
		    if (pathBack.size() > alarm) 
		        return null;
		        
    		//If we have already seen the command room but not computed a short path to reach it
    		if (computedPath.isEmpty()) {
    			List<Direction> tryPath = getPath(pKirk,commandRoom);
    			//It is possible that getPath fails because we might have not scanned enough of the map to find a path
    			computedPath = tryPath == null ? computedPath : new LinkedList<>(tryPath);
    		}
    		output = computedPath.isEmpty() ? null : computedPath.poll();
    	} 
		return output;
	}

	//If either we haven't seen the command room or we couldn't find a path to reach it
	public static Direction explore(Point pKirk, Set<Point> explored) {
		Direction output = null;
		for (Direction d : Direction.values()) {
		    Point q = d.tryMove(pKirk);
	    	if (q != null && cases[q.x][q.y] != Case.WALL && !explored.contains(q)) {
	    		boolean ignore = q.equals(commandRoom);
	    		if (ignore) {
	    			List<Direction> pathBack = new LinkedList<>(getPath(commandRoom,entry));
	    			ignore                   = pathBack.size() > alarm;
	    		}
	    		if (!ignore) {
	    			output    = d;
	    			break;
	    		}
	    	} 
    	}
		return output;
	}
	
	public static int						lines, cols;
	public static Point						commandRoom;
	public static Case[][]					cases;
	private static LinkedList<Direction>	computedPath;
	private static int	alarm;
	private static Point	entry = null;
	
    public static void main(String args[]) {
        @SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
        lines      = in.nextInt();
        cols       = in.nextInt();
        alarm      = in.nextInt(); 
        
        cases               = new Case[lines][cols];
        Set<Point> explored = new HashSet<>((int) (lines*cols/0.6));
        boolean getBack     = false;
        
        LinkedList<Direction> pastPath = new LinkedList<>();
        computedPath                   = new LinkedList<>();
        
        while (true) {
            int xKirk   = in.nextInt();
            int yKirk   = in.nextInt();
            Point pKirk = new Point(xKirk,yKirk);
            entry       = entry == null ? new Point(pKirk) : entry;
            
            explored.add(pKirk);
            
            in.nextLine();
            
            for (int i=0 ; i<lines ; i++) {
            	String line = in.nextLine();
            	int l = line.length();
            	for (int j=0 ; j<l ; j++) {
            		cases[i][j] = Case.getCase(line.charAt(j));
            		if (cases[i][j] == Case.COMMAND_ROOM) 
            			commandRoom = new Point(i,j);
            	}
            }
            
            Direction output  = null;
            getBack           = getBack || pKirk.equals(commandRoom);
            
            //If we still haven't reached the command room
            if (!getBack) {
            	output = reachCommandRoom(pKirk);
            	output = output == null ? explore(pKirk,explored) : output;
            } else {
            	//If we have reached the command room but still not computed the path back to the entry
            	if (computedPath.isEmpty()) 
            		computedPath = new LinkedList<>(getPath(pKirk,entry));
            	output = computedPath.poll();
            }
            
            if (output != null) 
                pastPath.offerFirst(output);
            else 
                output = pastPath.pollFirst().opposed();
            System.out.println(output);
        }
    }
}