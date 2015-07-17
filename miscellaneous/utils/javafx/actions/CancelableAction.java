package miscellaneous.utils.javafx.actions;

public abstract class CancelableAction extends AbstractAction {	
	public static final CancelableAction saveAction(Runnable updateSate, Runnable cancel) {
		return new CancelableAction() {
			@Override
			protected void doAction() { updateSate.run(); }
			
			@Override
			public void cancel() { cancel.run(); }
		};
	}
	
	@Override
	public final void updateState(StateObserver observer) { observer.handleReversibleStateChange(this); }	
	public abstract void cancel();
}
