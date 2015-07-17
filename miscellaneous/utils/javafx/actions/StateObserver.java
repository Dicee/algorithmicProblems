package miscellaneous.utils.javafx.actions;

interface StateObserver {
	public void handleStateSaved();
	public void handleReversibleStateChange(CancelableAction action);
	public void handleIrreversibleStateChange();
}