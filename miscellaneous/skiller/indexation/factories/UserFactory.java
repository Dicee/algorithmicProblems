package miscellaneous.skiller.indexation.factories;

import static miscellaneous.skiller.indexation.model.DBManager.prefixTablesByDatabaseName;
import static miscellaneous.skiller.indexation.model.Table.USERS;

import java.sql.ResultSet;
import java.sql.SQLException;

import miscellaneous.skiller.indexation.entities.User;
import miscellaneous.skiller.indexation.model.DBManager;
import com.dici.collection.ArrayUtils;

public class UserFactory extends AbstractDBFactory<String,User> {
	@Override
	public void persist(User user) { DBManager.persist(USERS,ArrayUtils.of("login","id"),ArrayUtils.of(user.id(),user.getLongId())); }

	@Override
	public User fromResultSet(ResultSet rs) throws SQLException { return new User(rs.getString("login"),rs.getLong("id")); }

	@Override
	protected String selectByKeyQuery(String key) { return prefixTablesByDatabaseName("select * from %s." + USERS.getName() +  " where login=" + key);	}
}
