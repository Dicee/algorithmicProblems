package miscellaneous.utils.javafx.components;

import static miscellaneous.utils.javafx.DialogsFactory.showError;
import static miscellaneous.utils.javafx.DialogsFactory.showPreFormattedError;
import static miscellaneous.utils.javafx.Settings.strings;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import miscellaneous.utils.javafx.Constraint;

public class DoubleConstraintField extends ConstraintForm {
	public static final double	ERROR_RETURN	= Double.POSITIVE_INFINITY;
	private final TextField		field;

	@SuppressWarnings("unchecked")
	public DoubleConstraintField(StringProperty name) {
		this(10,name,new Constraint[0]);
	}
	
	@SafeVarargs
	public DoubleConstraintField(Constraint<Double>... constraints) {
		this(5,constraints);
	}
	
	@SafeVarargs
	public DoubleConstraintField(int hgap, StringProperty name, Constraint<Double>... constraints) {
		this(hgap,constraints);
		Label nameLabel = new Label();
		nameLabel.textProperty().bind(name);
		nameLabel.setFont(GraphicFactory.subtitlesFont);
		getChildren().add(0,nameLabel);
	}
	
	@SafeVarargs
	private DoubleConstraintField(int hgap, Constraint<Double>... constraints) {
		super(hgap,constraints);
		this.field = new TextField();
		this.field.setPrefColumnCount(5);
		getChildren().add(field);
	}

	public void setValue(double d) {
        constraints.stream().forEach(predicate -> {
			if (!predicate.test(d))
				throw new ConstraintsException(predicate.errorMessage());
		});
        field.setText(d + "");
    }
    
	public double getValue() {
		try {
			double d = Double.parseDouble(field.getText());
			constraints.stream().forEach(predicate -> {
				if (!predicate.test(d))
					throw new ConstraintsException(predicate.errorMessage());
			});
			return d;
		} catch (NumberFormatException nfe) {
			field.requestFocus();
			showPreFormattedError(this,"error","anErrorOccurredMessage","numberFormatException");
			return ERROR_RETURN;
		} catch (ConstraintsException ce) {
			field.requestFocus();
			showError(this,strings.getProperty("error"),strings.getProperty("anErrorOccurredMessage"),ce.getMessage());
			return ERROR_RETURN;
		}
	}
	
	public void setOnAction(EventHandler<ActionEvent> handler) {
		field.setOnAction(handler);
	}
	
	public void bindOnActionProperty(ObservableValue< ? extends EventHandler<ActionEvent>> ov) {
		field.onActionProperty().bind(ov);
	}
}
