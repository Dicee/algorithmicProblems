package miscellaneous.utils.collection.toolbox;

import static miscellaneous.utils.exceptions.ExceptionUtils.uncheckedConsumer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javafx.util.Pair;

public final class DiffReport<T> {
	private final List<Diff<T>>		diffs		= new ArrayList<>();
	private final Map<String, Long>	eventsCount	= new HashMap<>();
	private int						diffCount, missingCount, unexpectedCount, totalCount;

	public void reportDifference(T actual, T expected) {
		reportNewRecord();
		diffs.add(Diff.diff(actual,expected));
		diffCount++;
	}

	public void reportMissingElement(T missing) {
		reportNewRecord();
		diffs.add(Diff.missing(missing));
		missingCount++;                                                    
	}                                                                      
                                                                           
	public void reportUnexpectedElement(T unexpected) {                    
		reportNewRecord();                                                 
		diffs.add(Diff.unexpected(unexpected));                            
		unexpectedCount++;                                                 
	}                                                                      
	                                                                       
	public void reportNewRecord()               { totalCount++; }                        
	public void reportEvent(String eventName)   { eventsCount.put(eventName, eventsCount.getOrDefault(eventName, 0L) + 1); }
	
	
	public Pair<File, File> makeDiffFiles() throws IOException {
		return makeDiffFiles(Object::toString);
	}
	
	public Pair<File, File> makeDiffFiles(Function<T, String> toString) throws IOException {
		File actual   = File.createTempFile("diff-actual"  , null);
		File expected = File.createTempFile("diff-expected", null);
		
		try (BufferedWriter actualWriter   = Files.newBufferedWriter(actual  .toPath()) ; 
			 BufferedWriter expectedWriter = Files.newBufferedWriter(expected.toPath())) {
			diffs.stream()
				 .map(Diff::showDiff).map(pair -> new Pair<>(pair.getKey().map(toString), pair.getValue().map(toString)))
				 .forEach(uncheckedConsumer(pair -> {
					 	if (pair.getKey  ().isPresent()) actualWriter  .write(pair.getKey  () + "\n");	
					 	if (pair.getValue().isPresent()) expectedWriter.write(pair.getValue() + "\n");	
				 }));
		}
		return new Pair<>(actual, expected);
	}
	
	public List<Diff<T>> getDiffs  ()           { return Collections.unmodifiableList(diffs); }
	public int  getDiffCount       ()           { return diffCount                          ; }                                                                       
	public int  getMissingCount    ()           { return missingCount                       ; }                                                                   
	public int  getUnexpectedCount ()           { return unexpectedCount                    ; }                                                                
	public int  getTotalCount      ()           { return totalCount                         ; }
	public int  getTotalEventsCount()           { return eventsCount.size()                 ; }
	public long getEventCount      (String key) { return eventsCount.get(key)               ; }

	@Override
	public String toString() {
		return "DiffReport [diffs=" + diffs + ", eventsCount=" + eventsCount + ", diffCount=" + diffCount + ", missingCount="
				+ missingCount + ", unexpectedCount=" + unexpectedCount + ", totalCount=" + totalCount + "]";
	}  
}