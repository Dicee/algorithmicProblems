package miscellaneous.game2048;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

	private static final String	fireLeft	= "var ev = document.createEvent('KeyboardEvent');\n"
	+ "ev.initKeyboardEvent('keydown', true, true, null, false, false, false, false, KeyboardEvent.DOM_VK_DOWN, KeyboardEvent.DOM_VK_DOWN);\n"
	+ "document.dispatchEvent(ev);";

	@Override
	public void start(Stage primaryStage) throws Exception {
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();


		WebView webview = new WebView();
		webview.getEngine().load("http://2048game.com/");

		TextField commands = new TextField();
		Button left = new Button("Left");
		left.setOnAction(ev ->  {
			commands.setText(fireLeft);
			commands.getOnAction().handle(null);
		});
		HBox buttons = new HBox(5,left,commands);

		TextArea console = new TextArea();
		console.setEditable(false);

		commands.setOnAction(e -> {
			console.appendText(">> " + commands.getText() + "\n");
			console.appendText(webview.getEngine().executeScript(commands.getText()) + "\n");
			commands.clear();
		});

		VBox bottom = new VBox(5,buttons,console);

		VBox root = new VBox(5,webview,bottom);

		Scene scene = new Scene(root,0,0);
		primaryStage.setScene(scene);
		primaryStage.setTitle("2048");

		primaryStage.setX     (screenBounds.getMinX  ());
		primaryStage.setY     (screenBounds.getMinY  ());
		primaryStage.setWidth (screenBounds.getWidth ());
		primaryStage.setHeight(screenBounds.getHeight());
		primaryStage.show();
	}

	private void fireLeft(WebView webview) {
		webview.getEngine().executeScript(
				  fireLeft);
		// initKeyboardEvent
	}

	public static void main(String[] args) {
		launch(args);
	}
}
