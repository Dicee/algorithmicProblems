package miscellaneous.skiller.indexation.factories;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBFactory<K,T> {
	public T fromDB(K key);
	public void persist(T t);
	public T fromResultSet(ResultSet rs) throws SQLException;
}
