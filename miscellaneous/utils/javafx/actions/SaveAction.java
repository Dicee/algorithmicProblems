package miscellaneous.utils.javafx.actions;

public abstract class SaveAction extends AbstractAction {
	public static final SaveAction saveAction(Runnable save) {
		return new SaveAction() { @Override public void save() { save.run(); } };
	}
	
	@Override
	public final void doAction() { save(); }
	
	@Override
	public final void updateState(StateObserver observer) { observer.handleStateSaved(); }
	public abstract void save();
}