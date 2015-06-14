package miscellaneous.skiller.indexation.factories;

import static miscellaneous.skiller.indexation.model.DBManager.prefixTablesByDatabaseName;
import static miscellaneous.skiller.indexation.model.Table.WORDS;

import java.sql.ResultSet;
import java.sql.SQLException;

import miscellaneous.skiller.indexation.entities.TextualContent.SourceType;
import miscellaneous.skiller.indexation.entities.WordCount;
import miscellaneous.skiller.indexation.model.DBManager;
import miscellaneous.utils.collection.ArrayUtils;

public final class WordCountFactory extends AbstractDBFactory<Long,WordCount> {
	@Override
	public void persist(WordCount wordCount) {
		DBManager.persist(
				WORDS,
				ArrayUtils.of("type","source_id","word","count"),
				ArrayUtils.of(wordCount.getSourceType().name().toLowerCase(),wordCount.getSourceId(),wordCount.getWord(),wordCount.getCount()));
	}

	@Override
	public WordCount fromResultSet(ResultSet rs) throws SQLException {
		Long       id         = rs.getLong("id");
		Long       sourceId   = rs.getLong("source_id"); 
		SourceType sourceType = SourceType.forName(rs.getString("type"));
		String     word       = rs.getString("word");
		Integer    count      = rs.getInt("count");
		return new WordCount(id,sourceId,sourceType,word,count);
	}

	@Override
	protected String selectByKeyQuery(Long key) { return prefixTablesByDatabaseName("select * from %s." + WORDS.getName() +  " where id=" + key); }
}
