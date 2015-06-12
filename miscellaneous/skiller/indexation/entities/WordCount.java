package miscellaneous.skiller.indexation.entities;

import miscellaneous.skiller.indexation.entities.TextualContent.SourceType;

public class WordCount extends AbstractIdentifiable<Long> {
	private final long			sourceId;
	private final SourceType	sourceType;
	private final String		word;
	private final int			count;
	
	public WordCount(long sourceId, SourceType sourceType, String word, int count) { 
		this(-1,sourceId,sourceType,word,count);
	}
	
	public WordCount(long id, long sourceId, SourceType sourceType, String word, int count) { 
		super(id);
		this.sourceId   = sourceId;
		this.sourceType = sourceType;
		this.word       = word;
		this.count      = count;
	}

	public long       getSourceId  () { return sourceId  ; }
	public SourceType getSourceType() { return sourceType; }
	public String     getWord      () { return word      ; }
	public int        getCount     () { return count     ; }

	@Override
	public String toString() {
		return "WordCount [sourceId=" + sourceId + ", sourceType=" + sourceType + ", word=" + word + ", count=" + count + ", id=" + id()
				+ "]";
	}
	
	
}
