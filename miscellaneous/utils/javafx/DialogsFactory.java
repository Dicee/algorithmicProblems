package miscellaneous.utils.javafx;

import static miscellaneous.utils.javafx.Settings.strings;
import javafx.scene.Node;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;

public class DialogsFactory {
	public static final Action showPreFormattedError(Node owner, String titlePropertyName, String mastheadPropertyName, String msgPropertyName) {
		return Dialogs.create()
				.owner(owner)
				.title(strings.getProperty(titlePropertyName))
				.masthead(strings.getProperty(mastheadPropertyName))
				.message(strings.getProperty(msgPropertyName))
				.showError();
	}
	
	public static final Action showError(Node owner, String title, String masthead, String msg) {
		return Dialogs.create()
				.owner(owner)
				.title(title)
				.masthead(masthead)
				.message(msg)
				.showError();
	}
}
