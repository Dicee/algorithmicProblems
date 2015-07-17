package miscellaneous.utils.javafx.actions;

import java.util.Deque;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class ActionManagerImpl implements StateObserver, ActionManager {
	private Deque<CancelableAction>	previous	= new LinkedList<>();
	private Deque<CancelableAction>	next		= new LinkedList<>();

	private BooleanProperty			hasPrevious	= new SimpleBooleanProperty(false);
	private BooleanProperty			hasNext		= new SimpleBooleanProperty(false);
	private BooleanProperty			isSaved  	= new SimpleBooleanProperty(true);
	
	@Override
	public void perform(Action action) {
		action.perform(this);
		clearNext();
	}
	
	@Override
	public void undo() {
		if (!previous.isEmpty()) { 
			pushNext(popPrevious());
			next.peek().cancel();
		} else 
			throw new NoSuchElementException("No action to undo");
	}
	
	@Override
	public void redo() {
		if (!next.isEmpty()) popNext().perform(this);
		else                 throw new NoSuchElementException("No action to redo");
	}
	
	@Override
	public void reset() {
		clearNext();
		clearPrev();
	}

	@Override
	public void handleStateSaved() { isSaved.set(true); System.out.println("handleSt"); }
	
	@Override
	public void handleReversibleStateChange(CancelableAction action) { 
		pushPrevious(action);
		isSaved.set(false);
	} 

	 @Override
	public void handleIrreversibleStateChange() { 
		clearPrev();
		isSaved.set(false);
	 } 
	
	public BooleanProperty hasPreviousProperty() { return hasPrevious; }
	public BooleanProperty hasNextProperty    () { return hasNext    ; }
	public BooleanProperty isSavedProperty    () { return isSaved    ; }
	
	private CancelableAction popPrevious() { return pop(previous,hasPrevious); }
	private CancelableAction popNext    () { return pop(next    ,hasNext    ); }

	private void pushPrevious(CancelableAction action) { push(action,previous,hasPrevious); }
	private void pushNext    (CancelableAction action) { push(action,next    ,hasNext    ); }
	
	private CancelableAction pop(Deque<CancelableAction> deque, BooleanProperty property) {
		CancelableAction res = deque.pop();
		if (deque.isEmpty()) property.set(false);
		return res;
	}
	
	private void push(CancelableAction action, Deque<CancelableAction> deque, BooleanProperty property) {
		deque.push(action);
		property.set(true);
	}
	
	private void clearNext() {
		next.clear();
		hasNext.set(false);
	}
	
	private void clearPrev() {
		previous.clear();
		hasPrevious.set(false);
	}
}