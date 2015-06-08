package  miscellaneous.utils.javafx.components;

import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

public class LabelledSlider extends HBox {
	private final Label		label;
	private final Slider	slider;
	
	public LabelledSlider(int hgap, double min, double max, double value, String format) {
		this("",hgap,min,max,value,format);
	}
	
	public LabelledSlider(String name, int hgap, double min, double max, double value, String format) {
		super(hgap);
		this.slider = new Slider(min,max,value);
		this.label  = new Label();
		this.label.setText(name);
		
		final Label valueLabel = new Label(String.format(format,value));
		slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldValue,
					Number newValue) -> {
				valueLabel.setText(String.format(format,slider.getValue()));
			});
		getChildren().addAll(label,slider,valueLabel);
	}
	
	public StringProperty textProperty() {
		return label.textProperty();
	}
	
	public void setDisableSlider(boolean b) {
		slider.setDisable(b);
	}
	
	public void setMajorTickUnit(double d) {
		slider.setMajorTickUnit(d);
	}
	
	public void setMinorTickCount(int n) {
		slider.setMinorTickCount(n);
	}
	
	public void setMin(double d) {
		slider.setMin(d);
	}
	
	public void setMax(double d) {
		slider.setMax(d);
	}
	
	public void setValue(double d) {
		slider.setValue(d);
	}
	
	public void setShowTickLabels(boolean b) {
		slider.setShowTickLabels(b);
	}
	
	public void setShowTickMarks(boolean b) {
		slider.setShowTickMarks(b);
	}
	
	public double getValue() {
		return slider.getValue();
	}
	
	public Label getLabel() {
		return label;
	}
}
