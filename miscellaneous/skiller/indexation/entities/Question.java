package miscellaneous.skiller.indexation.entities;

public class Question extends AbstractTextualContent {
	private final long		id;
	private final String	author;
	
	public Question(long id, String text, String author) { 
		super(text);
		this.id     = id;
		this.author = author;
	}

	public long   getId    () { return id    ; }
	public String getAuthor() { return author; }

	@Override
	public String toString() {
		return "Question [id=" + id + ", author=" + author + ", text=" + text() + "]";
	}
}
