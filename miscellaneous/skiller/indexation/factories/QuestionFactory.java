package miscellaneous.skiller.indexation.factories;

import static miscellaneous.skiller.indexation.model.DBManager.prefixTablesByDatabaseName;

import java.sql.ResultSet;
import java.sql.SQLException;

import miscellaneous.skiller.indexation.entities.Question;
import miscellaneous.skiller.indexation.model.DBManager;

public class QuestionFactory extends AbstractDBFactory<Long,Question> {
	@Override
	public void persist(Question question) {
		DBManager.persist("questions",question.id(),question.getAuthor());
	}

	@Override
	public Question fromResultSet(ResultSet rs) throws SQLException { 
		Long   id     = rs.getLong("id");
		String author = rs.getString("author");
		return new Question(id,"",author);
	}

	@Override
	protected String selectByKeyQuery(Long key) { return prefixTablesByDatabaseName("select * from %s.questions where id=" + key); }
}

