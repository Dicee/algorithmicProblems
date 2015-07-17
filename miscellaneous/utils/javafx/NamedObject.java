package miscellaneous.utils.javafx;
import javafx.beans.property.StringProperty;

public class NamedObject<T> {
	public T						bean;
	private final StringProperty	name;
	
	public NamedObject(StringProperty name, T bean) {
		this.bean = bean;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name.getValue();
	}
	
	public StringProperty nameProperty() {
		return name;
	}
}