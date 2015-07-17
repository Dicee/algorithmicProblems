package miscellaneous.utils.javafx.actions;

import javafx.beans.property.BooleanProperty;

public interface ActionManager {
	public void perform(Action action);
	public void undo();
	public void redo();
	public void reset();
	
	public BooleanProperty hasPreviousProperty();
	public BooleanProperty hasNextProperty();
	public BooleanProperty isSavedProperty();
}