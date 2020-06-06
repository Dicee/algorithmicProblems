package miscellaneous.skiller.indexation.factories;

import static miscellaneous.skiller.indexation.model.DBManager.prefixTablesByDatabaseName;
import static miscellaneous.skiller.indexation.model.Table.COMMENTS;

import java.sql.ResultSet;
import java.sql.SQLException;

import miscellaneous.skiller.indexation.entities.Comment;
import miscellaneous.skiller.indexation.model.DBManager;
import com.dici.collection.ArrayUtils;

public class CommentFactory extends AbstractDBFactory<Long,Comment> {
	@Override
	public void persist(Comment comment) {
		comment.setId(DBManager.newLongId(COMMENTS));
		DBManager.persist(COMMENTS,ArrayUtils.of("id","question_id","author"),ArrayUtils.of(comment.id(),comment.getQuestionId(),comment.getAuthor()));
	}

	@Override
	public Comment fromResultSet(ResultSet rs) throws SQLException {
		Long   id         = rs.getLong("id");
		String author     = rs.getString("author");
		Long   questionId = rs.getLong("question_id");
		return new Comment(id,questionId,"",author);
	}

	@Override
	protected String selectByKeyQuery(Long key) { return prefixTablesByDatabaseName("select * from %s." + COMMENTS.getName() + " where id=" + key); }
}
