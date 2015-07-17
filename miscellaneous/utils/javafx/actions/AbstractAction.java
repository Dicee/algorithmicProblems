package miscellaneous.utils.javafx.actions;

abstract class AbstractAction implements ObservableAction {
	@Override
	public final void perform(StateObserver observer) {
		doAction();
		updateState(observer);
	}
	
	protected abstract void doAction();
	
	public Action before(Action after) {
		return observer -> {
			perform(observer);
			after.perform(observer);
		};
	}
}