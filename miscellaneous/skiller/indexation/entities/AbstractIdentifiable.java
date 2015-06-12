package miscellaneous.skiller.indexation.entities;

public class AbstractIdentifiable<K> implements Identifiable<K> {
	private final K id;

	public AbstractIdentifiable(K id) { this.id = id; }
	
	@Override public final K id() { return id  ; }
}
