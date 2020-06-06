package miscellaneous.skiller.indexation.entities;

import com.dici.collection.ArrayUtils;

public interface TextualContent {
	public static enum SourceType { 
		QUESTION(Question.class),
		COMMENT (Comment .class);
		
		private final Class<? extends TextualContent> clazz;

		private SourceType(Class<? extends TextualContent> clazz) {
			this.clazz = clazz;
		}
		
		public static SourceType forName (String name   ) { return ArrayUtils.findAnyRequired(values(),x -> x.name().equals(name)); }
		public static SourceType forClass(Class<?> clazz) { return ArrayUtils.findAnyRequired(values(),x -> x.clazz == clazz)     ; }
	}
	
	public String text();
}
