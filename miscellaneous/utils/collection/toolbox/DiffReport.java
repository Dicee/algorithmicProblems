package miscellaneous.utils.collection.toolbox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DiffReport<T> {
	private final List<Diff<T>>		diffs		= new ArrayList<>();
	private final Map<String, Long>	eventsCount	= new HashMap<>();
	private int						diffCount, missingCount, unexpectedCount, totalCount;

	public void reportDifference(T actual, T expected) {
		reportNewRecord();
		diffs.add(new NotEqualDiff<>(actual,expected));
		diffCount++;
	}

	public void reportMissingElement(T missing) {
		reportNewRecord();
		diffs.add(new MissingElementDiff<>(missing));
		missingCount++;                                                    
	}                                                                      
                                                                           
	public void reportUnexpectedElement(T unexpected) {                    
		reportNewRecord();                                                 
		diffs.add(new UnexpectedElementDiff<T>(unexpected));                            
		unexpectedCount++;                                                 
	}                                                                      
	                                                                       
	public void reportNewRecord()               { totalCount++; }                        
	public void reportEvent(String eventName)   { eventsCount.put(eventName, eventsCount.getOrDefault(eventName, 0L) + 1); }
	
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