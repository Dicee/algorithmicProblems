package miscellaneous.skiller.indexation.entities;

public class Question extends AbstractTextualContent {
	private final String	author;
	
	public Question(long id, String text, String author) { 
		super(id,text);
		this.author = author;
	}

	public String getAuthor() { return author; }

	@Override
	public String toString() {
		return "Question [id=" + id() + ", author=" + author + ", text=" + text() + "]";
	}
}
