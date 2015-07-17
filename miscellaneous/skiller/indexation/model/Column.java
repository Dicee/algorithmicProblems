package miscellaneous.skiller.indexation.model;

import static miscellaneous.utils.check.Check.areEqual;
import static miscellaneous.utils.check.Check.notNull;

import java.sql.ResultSet;

public class Column<T> {
	public final String			name;
	public final DataExtractor	extractor;
	private final Class<T>		clazz;

	public Column(String name, DataExtractor extractor, Class<T> clazz) {
		this.name      = notNull(name);
		this.extractor = notNull(extractor);
		this.clazz     = notNull(clazz);
		areEqual(clazz,extractor.clazz);
	}
	
	public T getValue(ResultSet rs) { return clazz.cast(extractor.extract(rs,name)); }
}
