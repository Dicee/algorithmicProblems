package miscellaneous.playingWithDot;

import static java.util.stream.Collectors.toSet;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import miscellaneous.utils.collection.richIterator.RichIterators;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Graph {
	private static final Log		LOG				= LogFactory.getLog(Graph.class);
	private static final Pattern	EDGE_PATTERN	= Pattern.compile("(\\S+)\\s*->\\s*(\\S+)");
	private static final int		DEFAULT_LEVEL	= 2;
	
	public static Graph fromFile(String path) { return fromFile(new File(path)); }
	public static Graph fromFile(File f) { 
		Graph g = new Graph();
		RichIterators.fromLines(f).foreach(line -> {
			Matcher m = EDGE_PATTERN.matcher(line);
			if (m.matches()) g.edges.add(new Edge(m.group(1),m.group(2)));
//			else 			 LOG.warn("Ignored the following line (unrecognized format) : " + line);
		});
		return g;
	}
	
	private final Set<Edge> edges = new HashSet<>();
	
	private Graph() { }
	
	public Graph subgraphRelatedToSelection(Predicate<Edge> selector) {
		Set<Edge> border = edges.stream().filter(selector).collect(Collectors.toSet());
		Graph     graph  = new Graph();
//		System.out.println("border " + border.size() + "\n" + StringUtils.join("\n",border));
		createAncestorsGraph  (graph,border);
		createDescendantsGraph(graph,border);
		return graph;
	}
	
	private void createAncestorsGraph(Graph subgraph, Set<Edge> border) {
		if (border.isEmpty()) return;
		subgraph.edges.addAll(border);
    	createAncestorsGraph(subgraph,ancestors(subgraph.edges,border));
    }
    
    private void createDescendantsGraph(Graph subgraph, Set<Edge> border) {
    	if (border.isEmpty()) return;
        subgraph.edges.addAll(border);
        createAncestorsGraph  (subgraph,withSameOutput(subgraph.edges,border));
        createDescendantsGraph(subgraph,successors    (subgraph.edges,border));
    }

    private Set<Edge> ancestors(Set<Edge> explored, Set<Edge> border) {
    	return findAll(explored, border,(edge,borderEdge) -> edge.outputName.equals(borderEdge.inputName));
    }
    
    private Set<Edge> successors(Set<Edge> explored, Set<Edge> border) {
    	return findAll(explored,border,(edge,borderEdge) -> edge.inputName.equals(borderEdge.outputName));
    }
    
    private Set<Edge> withSameOutput(Set<Edge> explored, Set<Edge> border) {
    	return findAll(explored,border,(edge,borderEdge) -> edge.outputName.equals(borderEdge.outputName));
    }
    
    private Set<Edge> findAll(Set<Edge> explored, Set<Edge> border, BiFunction<Edge,Edge,Boolean> predicate) {
        return border.stream()
                     .flatMap(edge -> edges.stream().filter(e -> predicate.apply(e,edge)))
                     .filter(edge -> !explored.contains(edge))
                     .collect(toSet());
    }
	
	public Set<Edge> getEdges() { return Collections.unmodifiableSet(edges); }

	public String toDot(int level) { return join("digraph {","}",level); }
	
	public String toClusterDot(int level) { 
		
		return join("digraph {","}",level); 
	}
	
	@Override
	public String toString() { return join("Graph [","]",DEFAULT_LEVEL); }
	private String join(String before, String after, int level) { 
		return 
			before + 
			RichIterators.fromCollection(edges).map(edge -> edge.toDot(level)).distinct().mkString("\n\t","\n\t","\n") + 
			after; 
	}
}
