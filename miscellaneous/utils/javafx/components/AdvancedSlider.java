package miscellaneous.utils.javafx.components;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

public class AdvancedSlider extends HBox {
	private final Slider	slider;
	private final Button	prev, next;
	private double			scale	= 1;
	
	public AdvancedSlider(double min, double max, double value) {
		super(10);
		this.slider = new Slider(min,max,value);
		this.prev   = new Button("<");
		this.next   = new Button(">");
		
		Label label = new Label(String.format("%.2f",value));
		slider.valueProperty().addListener((ObservableValue<? extends Number> ov, Number oldValue,
				Number newValue) -> {
			label.setText(String.format("%.2f",slider.getValue()));
		});
		slider.setMajorTickUnit((slider.getMax() - slider.getMin())/10);
		slider.setMinorTickCount(20);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		
		prev.setOnAction(ev -> {
			scale /= 10;
			scale(0.1d);
			prev.setDisable(scale == 0.01d);
			next.setDisable(false);
		});
		next.setOnAction(ev -> {
			scale *= 10;
			scale(10d);
			prev.setDisable(false);
			next.setDisable(scale == 100d);
		});
		
		getChildren().addAll(slider,prev,next,label);
	}	
	
	private void scale(double scale) {
		double value = slider.getValue();
		slider.setMin(scale*slider.getMin());
		slider.setMax(scale*slider.getMax());
		slider.setValue(scale*value);
		slider.setMajorTickUnit(scale*slider.getMajorTickUnit());
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
}
