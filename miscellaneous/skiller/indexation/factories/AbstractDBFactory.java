package miscellaneous.skiller.indexation.factories;

import static miscellaneous.utils.exceptions.IgnoreCheckedExceptions.ignoreCheckedExceptionsFunction;
import miscellaneous.skiller.indexation.model.DBManager;

public abstract class AbstractDBFactory<K,T> implements DBFactory<K,T> {
	@Override
	public final T fromDB(K key) {
		String query = selectByKeyQuery(key);
		return DBManager.getSingleResult(query,ignoreCheckedExceptionsFunction(this::fromResultSet));
	}
	
	protected abstract String selectByKeyQuery(K key);
}
