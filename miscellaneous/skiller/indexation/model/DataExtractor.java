package miscellaneous.skiller.indexation.model;

import static com.dici.exceptions.ExceptionUtils.uncheckedBiFunction;

import java.sql.ResultSet;
import java.util.function.BiFunction;

public enum DataExtractor {
	LONG  (uncheckedBiFunction((rs,name) -> rs.getLong(name)), Long.class),
	STRING(uncheckedBiFunction((rs,name) -> rs.getString(name)), String.class);
	
	private final BiFunction<ResultSet, String, ?>	extractor;
	public final Class<?>							clazz;

	private DataExtractor(BiFunction<ResultSet,String,?> extractor, Class<?> clazz) {
		this.extractor = extractor;
		this.clazz     = clazz;
	}
	
	public Object extract(ResultSet rs, String columnName) { return extractor.apply(rs,columnName); }
}
