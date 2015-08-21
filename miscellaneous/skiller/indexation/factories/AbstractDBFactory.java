package miscellaneous.skiller.indexation.factories;

import static miscellaneous.utils.exceptions.ExceptionUtils.uncheckedFunction;
import miscellaneous.skiller.indexation.model.DBManager;

public abstract class AbstractDBFactory<K,T> implements DBFactory<K,T> {
	@Override
	public final T fromDB(K key) {
		String query = selectByKeyQuery(key);
		return DBManager.getSingleResult(query,uncheckedFunction(this::fromResultSet));
	}
	
	protected abstract String selectByKeyQuery(K key);
}
