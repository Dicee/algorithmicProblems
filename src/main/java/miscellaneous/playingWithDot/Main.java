package miscellaneous.playingWithDot;

import java.io.File;
import java.io.IOException;

import com.dici.collection.richIterator.RichIterators;
import com.dici.files.FileUtils;

public class Main {
	private static final String	RESOURCES_PATH	= "src/" + FileUtils.getPathToPackage(Main.class) + "resources/";
	private static final String	GRAPH_DOT		= RESOURCES_PATH + "graph.dot";
	private static final String	SUBGRAPH_DOT	= RESOURCES_PATH + "subgraph.dot";
	private static final File	GRAPH_SVG		= new File(RESOURCES_PATH + "graph.svg");
	private static final File	SUBGRAPH_SVG	= new File(RESOURCES_PATH + "subgraph.svg");

	public static void main(String[] args) throws IOException {
		Graph graph = Graph.fromFile(FileUtils.getResourceAbsolutePath("resources/edges.txt",Main.class));
//		System.out.println(graph);
		Graph subgraph = graph.subgraphRelatedToSelection(edge -> edge.inputName.contains("AdvertiserEvents/retail/unsorted"));
//		System.out.println(subgraph);
		
		RichIterators.of(graph.toDot(1)).writeToFile(new File(GRAPH_DOT));
		RichIterators.of(subgraph.toDot(1)).writeToFile(new File(SUBGRAPH_DOT));
		
		new ProcessBuilder("dot","-Tsvg",GRAPH_DOT,"-o",GRAPH_SVG.getAbsolutePath()).start();
		new ProcessBuilder("dot","-Tsvg",SUBGRAPH_DOT,"-o",SUBGRAPH_SVG.getAbsolutePath()).start();
	}
}
