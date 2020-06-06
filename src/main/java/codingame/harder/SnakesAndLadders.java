package codingame.harder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class SnakesAndLadders {
	
	private static class Node implements Comparable<Node> {
		public int dist;
		public Node parent;
		public int indexInMap;
		
		public Node() {
			this.dist       = 0;
			this.parent     = null;
			this.indexInMap = startIndex;
		}
		
		public Node(Node parent, int indexInMap) {
			this.dist       = !map[parent.indexInMap].equals("R") ? parent.dist + 1 : parent.dist;
			this.parent     = parent;
			this.indexInMap = indexInMap;
		}
		
		public int getPathLength() {
			int result = 0;			
			Node node  = this;
			while (node.parent != null) {
				node = node.parent;		
				result++;
			}
			return result;
		}
		
		public List<Node> getSuccessors() {
			List<Node> successors = new ArrayList<>();
			
			if (map[indexInMap].equals("E"))
				return successors;
			
			if (!Arrays.asList("R","S").contains(map[indexInMap])) { 
				int newIndex = Integer.parseInt(map[indexInMap]) + indexInMap;
				if (!outOfBounds(newIndex))
					successors.add(new Node(this,newIndex));
				return successors;
			}
			
			for (int dice=1 ; dice<=6 ; dice++) {
				int newIndex = dice + indexInMap;
				if (!outOfBounds(newIndex))
					successors.add(new Node(this,newIndex));
			}
			return successors;	
		}

		@Override
		public int compareTo(Node other) {
			return Integer.compare(dist,other.dist);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + indexInMap;
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
			if (indexInMap != other.indexInMap)
				return false;			
			return true;
		}
		
		@Override
		public String toString() {
		    return map[indexInMap];
		}
	}
	
	public static boolean outOfBounds(int index) {
		return index < 0 || index >= nSquares;
	}
	
	public static int getPathLength() {		
		Set<Node>   explored = new HashSet<>();
		Queue<Node> seen     = new PriorityQueue<>(1000);
		Node        node     = new Node();
		explored.add(node);
		seen.add(node);
		
		while (node != null && !map[node.indexInMap].equals("E")) {
			for (Node successor : node.getSuccessors())
				if (!explored.contains(successor))
					seen.add(successor);
			explored.add(node = seen.poll());
		}
		return node == null ? -1 : node.getPathLength();
	}

	private static String[]	map;
	private static int startIndex = 0, endIndex = 0, nSquares;

	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		nSquares   = in.nextInt();
		map        = new String[nSquares];	
		
		in.nextLine();
		for (int i=0 ; i<nSquares ; i++) {
			String s   = in.nextLine();
			startIndex = s.equals("S") ? i : startIndex;
			endIndex   = s.equals("E") ? i : endIndex;
			map[i]     = s;
		}
		in.close();
		int output = getPathLength();
		System.out.println(output == -1 ? "impossible" : output);
	}
}