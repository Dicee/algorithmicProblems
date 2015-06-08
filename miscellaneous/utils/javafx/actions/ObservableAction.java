package miscellaneous.utils.javafx.actions;

interface ObservableAction extends Action {
	default void updateState(StateObserver observer) { };
}
