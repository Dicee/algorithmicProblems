package  miscellaneous.utils.javafx.components;

import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class HLabelledNode<T extends Node> extends HBox {
	public final T		node;
	public final Label	label;

	private HLabelledNode(int hgap, T node) {
		super(hgap);
		this.node  = node;
		this.label = new Label();
		getChildren().addAll(label,node);
	}
	
	public HLabelledNode(int hgap, T node, StringProperty textProperty, Font font) {
		this(hgap,node);
		this.label.textProperty().bind(textProperty);
		this.label.setFont(font);
		
	}

	public HLabelledNode(int hgap, T node, StringProperty textProperty) {
		this(hgap,node);
		this.label.textProperty().bind(textProperty);
	}

	public HLabelledNode(int hgap, T node, String text, Font font) {
		this(hgap,node);
		this.label.setText(text);
        this.label.setFont(font);
	}
}
