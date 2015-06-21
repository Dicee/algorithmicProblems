package miscellaneous.skiller.indexation.model;

public class Stat<T> {
	public final T result;
	public final String description;

	public Stat(T result, String description) {
		this.result = result;
		this.description = description;
	}
}
