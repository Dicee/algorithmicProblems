package miscellaneous.utils.javafx.actions;

public abstract class NonCancelableAction extends AbstractAction {	
	public static final NonCancelableAction nonCancelableAction(Runnable doAction) {
		return new NonCancelableAction() { @Override public void doAction() { doAction.run(); } };
	}
	
	@Override
	public final void updateState(StateObserver observer) { observer.handleIrreversibleStateChange(); }	
}
