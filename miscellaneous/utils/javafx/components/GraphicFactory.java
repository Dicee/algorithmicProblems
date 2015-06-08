package miscellaneous.utils.javafx.components;

import static miscellaneous.utils.javafx.Settings.strings;

import java.util.function.Consumer;

import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import miscellaneous.utils.javafx.NamedObject;

public abstract class GraphicFactory<T> {
	private static final double		PREFERRED_WIDTH		= 500;
	private static final double		PREFERRED_HEIGHT	= 300;
	public static final Font		subtitlesFont		= Font.font(null,FontWeight.BOLD,13);

	private static final String		backgroundStyle		= "linear-gradient(#f9f9f9,#e9e9e9)";
	protected Stage					primaryStage;
	protected final AnchorPane		root;
	protected final Button			create;
	
	public Consumer<NamedObject<T>> consumer;

	public GraphicFactory(StringProperty titleProperty, StringProperty actionProperty) {
		this(titleProperty,actionProperty,null,PREFERRED_WIDTH,PREFERRED_HEIGHT);
	}
	
	public GraphicFactory(StringProperty titleProperty, StringProperty actionProperty, 
			Consumer<NamedObject<T>> consumer) {
		this(titleProperty,actionProperty,consumer,PREFERRED_WIDTH,PREFERRED_HEIGHT);
	}
	
	public GraphicFactory(StringProperty titleProperty, StringProperty actionProperty, 
			Consumer<NamedObject<T>> consumer, double width, double height) {
		this.consumer = consumer;
		primaryStage  = new Stage(StageStyle.DECORATED);
		Button close  = new Button();
		create        = new Button();
		close .textProperty().bind(strings.getObservableProperty("closeAction"));
		create.textProperty().bind(actionProperty);
		
		HBox footer = new HBox(12,create,close);
		root        = new AnchorPane(footer);
		root.setBackground(new Background(new BackgroundFill(Paint.valueOf(backgroundStyle),
				new CornerRadii(0,false),Insets.EMPTY)));
		
		AnchorPane.setBottomAnchor(footer,15d);
		AnchorPane.setRightAnchor(footer,25d);
		
		//Event handling
		create.setOnAction(ev -> { 
			NamedObject<T> element = create();
			if (element != null) {
				this.consumer.accept(element); 
				hide(); 
			}
		});
		close .setOnAction(ev -> hide());
		
		javafx.scene.Scene scene = new Scene(root,width,height,Color.WHITESMOKE);
        primaryStage.titleProperty().bind(titleProperty);
        primaryStage.setScene(scene);
		primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(ev -> hide());
	}
	
	public void setConsumer(Consumer<NamedObject<T>> consumer) {
		if (consumer == null)
			throw new NullPointerException();
		this.consumer = consumer;
	}
	
	public final void show() {
		primaryStage.show();
	}
	
	public abstract void show(T item);
	
	public final void hide() {
		primaryStage.hide();
        consumer.accept(null);
	}
	
	public StringProperty textProperty() {
		return create.textProperty();
	}
	
	protected abstract NamedObject<T> create();
}
