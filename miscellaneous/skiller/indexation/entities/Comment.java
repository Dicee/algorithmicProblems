package miscellaneous.skiller.indexation.entities;

public class Comment extends AbstractTextualContent {
	private final long		questionId;
	private final String	author;
	
	public Comment(long questionId, String text, String author) { this(-1,questionId,text,author); }

	public Comment(long id, long questionId, String text, String author) { 
		super(id,text);
		this.questionId = questionId;
		this.author     = author;
	}

	public long   getQuestionId() { return questionId; }
	public String getAuthor    () { return author    ; }

	@Override
	public String toString() {
		return "Comment [id=" + id() + ", questionId=" + questionId + ", author=" + author + ", text=" + text() + "]";
	}
}
