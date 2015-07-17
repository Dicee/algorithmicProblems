package miscellaneous.skiller.indexation.entities;

public abstract class AbstractTextualContent extends AbstractIdentifiable<Long> implements TextualContent {
	private final String	text;
	
	public AbstractTextualContent(String text) { this(-1L,text); }
	
	public AbstractTextualContent(Long id, String text) { 
		super(id);
		this.text = text; 
	}

	@Override 
	public final String text() { return text; }
}
