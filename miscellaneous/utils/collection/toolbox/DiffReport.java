package miscellaneous.utils.collection.toolbox;

import static miscellaneous.utils.exceptions.ExceptionUtils.uncheckedConsumer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import javafx.util.Pair;
import miscellaneous.utils.strings.StringUtils;

public final class DiffReport<T> {
	private final List<Diff<T>>		diffs		= new ArrayList<>();
	private final Map<String, Long>	eventsCount	= new HashMap<>();
	private int						diffCount, missingCount, unexpectedCount, totalCount;

	public Diff<T> reportDifference(T actual, T expected) {
		reportNewRecord();
		Diff<T> diff = new NotEqualDiff<>(actual,expected);
		diffs.add(diff);
		diffCount++;
		return diff;
	}

	public Diff<T> reportMissingElement(T missing) {
		reportNewRecord();
		Diff<T> diff = new MissingElementDiff<>(missing);
		diffs.add(diff);
		missingCount++;
		return diff;
	}

	public Diff<T> reportUnexpectedElement(T unexpected) {
		reportNewRecord();
		Diff<T> diff = new UnexpectedElementDiff<>(unexpected);
		diffs.add(diff);
		unexpectedCount++;
		return diff;
	}
	
	public String getDiffString() {
	     return getDiffString(Object::toString);
	 }

	public String getDiffString(Function<T, String> toString) {
		StringBuilder sb = new StringBuilder();
		diffs.stream().map(Diff::showDiff).map(pair -> new Pair<>(pair.getKey().map(toString),pair.getValue().map(toString)))
				.forEach(uncheckedConsumer(pair -> {
					Optional<String> actual = pair.getKey(), expected = pair.getValue();
					// at least one of the optionals is non empty
					if (actual.isPresent() && expected.isPresent()) {
						appendLinesWithPrefix(sb,"found: ",actual.get());
						appendLinesWithPrefix(sb,"expected: ",expected.get());
					} else if (actual.isPresent()) {
						appendLinesWithPrefix(sb,"+",actual.get());
					} else {
						appendLinesWithPrefix(sb,"-",expected.get());
					}
				}));
		return sb.toString();
	}

	private static void appendLinesWithPrefix(StringBuilder sb, String prefix, String text) {
		for (String line : text.split("\n")) {
			sb.append(prefix).append(line).append("\n");
		}
	}
	 
	public  void reportEqual()                  { reportNewRecord(); }
	private void reportNewRecord()              { totalCount++; }                        
	public  void reportEvent(String eventName)  { eventsCount.put(eventName, eventsCount.getOrDefault(eventName, 0L) + 1); }
	
	public List<Diff<T>> getDiffs  ()           { return Collections.unmodifiableList(diffs); }
	public int  getDiffCount       ()           { return diffCount                          ; }                                                                       
	public int  getMissingCount    ()           { return missingCount                       ; }                                                                   
	public int  getUnexpectedCount ()           { return unexpectedCount                    ; }                                                                
	public int  getTotalCount      ()           { return totalCount                         ; }
	public int  getTotalEventsCount()           { return eventsCount.size()                 ; }
	public long getEventCount      (String key) { return eventsCount.get(key)               ; }

	@Override
	public String toString() {
	    return "DiffReport [" +
	            "\n\tdiffs=" + (diffs.isEmpty() ? "[]," : "\n\t\t" + StringUtils.join("\n\t\t", diffs)) +
	            "\n\teventsCount=" + eventsCount + "," +
	            "\n\tdiffCount=" + diffCount + "," +
	            "\n\tmissingCount=" + missingCount + "," +
	            "\n\tunexpectedCount=" + unexpectedCount + "," +
	            "\n\ttotalCount=" + totalCount +
	            "\n]";
	}  
}