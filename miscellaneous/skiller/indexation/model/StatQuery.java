package miscellaneous.skiller.indexation.model;

public class StatQuery<T> {
	public final String					name;
	private final String				query;
	private final DescriptionFormatter	descriptionFormatter;

	StatQuery(String name, String query, DescriptionFormatter descriptionFormatter, Column<?>... columns) {
		this.name                 = name;
		this.query                = query;
		this.descriptionFormatter = descriptionFormatter;
	}

	public Stat<T> getStat(Object... params) {
		return null;
	}
}
