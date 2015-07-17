package miscellaneous.utils.javafx;

import java.util.function.Predicate;
import javafx.beans.property.StringProperty;

public abstract class Constraint<T> implements Predicate<T> {
	private final StringProperty errorMessage;
	
	public Constraint(StringProperty errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public StringProperty errorMessageProperty() {
		return errorMessage;
	}
	
	public String errorMessage() {
		return errorMessage.getValue();
	}
}
