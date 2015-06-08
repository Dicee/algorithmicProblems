package miscellaneous.utils.javafx.components;

import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ContextToolBar extends ToolBar {
	
	private Label title;
	
	public ContextToolBar(StringProperty titleProperty, double prefWidth, Node... items) {
		super(items);
		setPrefWidth(prefWidth);
		
		title = new Label();
		title.setFont(Font.font("",FontWeight.BOLD,15));
		title.setAlignment(Pos.CENTER);
		title.textProperty().bind(titleProperty);
		getItems().add(0,title);		
		
//		widthProperty().addListener(obs -> resizeItems());		
	}
	
//	private final void resizeItems() {
//		double maxWidth  = getPrefWidth();
//		Button[] buttons = new Button[getItems().size() - 1];
//		int i            = 0;
//		for (Node item : getItems()) 
//			if (item instanceof Button) {
//				Button b     = (Button) item;
//				maxWidth     = Math.max(maxWidth,b.prefWidth(b.getHeight()));	
//				buttons[i++] = b;
//			}
//        maxWidth = Math.max(maxWidth,title.prefWidth(title.getHeight()));
//		for (Button b : buttons) 
//			b.setPrefWidth(maxWidth);
//		title.setMaxWidth(maxWidth);
//	}
	
	public ContextToolBar(StringProperty titleProperty, double prefWidth) {
		this(titleProperty,prefWidth,new Node[] {});		
	}
}