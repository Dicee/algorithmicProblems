package miscellaneous.playingWithDot;

import java.util.HashMap;
import java.util.Map;

import miscellaneous.utils.check.Check;

public class EdgeLayout {
	private final Map<String,Edge> clusterToEdges = new HashMap<>();
	private final int level;
	
	public EdgeLayout(int level) { this.level = Check.isPositive(level); }
	
	public void addEdges(Edge... edges) { for (Edge edge : edges) add(edge); } 
	public void add(Edge e) { clusterToEdges.put(e.getInputRoot(), e); }
	
	public String toDot() {
		StringBuilder sb = new StringBuilder();
		return sb.toString();
	}
}
