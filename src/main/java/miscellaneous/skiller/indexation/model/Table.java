package miscellaneous.skiller.indexation.model;

public enum Table {
	USERS("users"),
	QUESTIONS("questions"),
	COMMENTS("comments"),
	WORDS("words");
	
	private final String name;
	private Table(String name) { this.name = name; }
	public String getName()    { return name; }
}
