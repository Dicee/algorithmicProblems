package miscellaneous.utils.javafx.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class IconSelectionBox extends VBox {
	public IconSelectionBox(IconSelectionView... views) {
		this();
		addSelectionViews(views);
	}
	
	public IconSelectionBox() {
        super();      
        setPadding(new Insets(5));
        setSpacing(10);
    }
    
	public void addSelectionViews(IconSelectionView... views) { for (IconSelectionView view : views) addSelectionView(view); }
	
    public void addSelectionView(IconSelectionView view) { 
    	Label label = new Label();
    	label.textProperty().bind(view.nameProperty());
    	getChildren().addAll(label,view);
    }
}