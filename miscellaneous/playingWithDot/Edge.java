package miscellaneous.playingWithDot;

import static miscellaneous.utils.check.Check.notNull;

import java.io.File;
import java.util.List;
import java.util.Objects;

import miscellaneous.utils.collection.richIterator.RichIterator;
import miscellaneous.utils.files.FileUtils;

public class Edge {
	private static String unpartitionedPath(String path) {
		if (path.charAt(path.length() - 1) == '/') path = path.substring(0,path.length() - 1);
		int index = path.lastIndexOf('/');
		if (isPartitionNumber(path.substring(index + 1))) path = path.substring(0,index);
		return path;
	}
	
	private static boolean isPartitionNumber(String s) {
		return s.length() == 3 && s.chars().allMatch(Character::isDigit) && Integer.parseInt(s) < 128;
	}
	
	public final String inputName;
	public final String outputName;
	
	public Edge(String inputName, String outputName) {
		this.inputName  = unpartitionedPath(notNull(inputName));
		this.outputName = unpartitionedPath(notNull(outputName));
	}
	
	public String toDot(int level) { return format(selectLevelInPath(inputName,level),selectLevelInPath(outputName,level)); }
	
	@Override
	public String toString() { return format(inputName,outputName); }
	
	private String format(String left, String right) { return String.format("\"%s\" -> \"%s\"",left,right); }

	public String getInputRoot() { return selectLevelInPath(inputName,0); }
	
	private String selectLevelInPath(String path, int level) {
		List<String> levels = RichIterator
				.iterate(new File(path),f -> f.getParentFile())
				.takeUntil(f -> f.getParentFile() == null)
				.map(f -> FileUtils.toCanonicalPath(f.toString()))
				.toList();
		return levels.get(Math.max(0,levels.size() - 1 - level));
	}
	
	@Override
	public int hashCode() { return Objects.hash(inputName,outputName); }
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Edge) {
			Edge that = (Edge) o;
			return Objects.equals(inputName,that.inputName) && Objects.equals(outputName,that.outputName);
		} else 
			return false;
	}
}
