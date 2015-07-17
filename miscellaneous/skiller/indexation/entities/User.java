package miscellaneous.skiller.indexation.entities;

public class User extends AbstractIdentifiable<String> {
	private final long longId;

	public User(String name, long id) {
		super(name);
		this.longId = id;
	}

	public long getLongId() { return longId; }
}
