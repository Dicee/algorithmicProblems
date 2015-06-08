package miscellaneous.utils.javafx.components;

import static miscellaneous.utils.javafx.Settings.strings;
import javafx.geometry.Insets;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.util.Callback;
import miscellaneous.utils.javafx.NamedObject;

import org.controlsfx.dialog.Dialogs;

public class ListExplorer<T> extends HBox {
	private final ListView<NamedObject<T>>	listView;
	private final TextArea					test;
	
	public ListExplorer() {
		super(2);
		test = new TextArea("salut");
		listView = new ListView<>();
		
		getChildren().addAll(listView,test);
		
		test.setMaxWidth(Double.MAX_VALUE);
		listView.setMinWidth(100);
		
		listView.setCellFactory(new Callback<ListView<NamedObject<T>>, ListCell<NamedObject<T>>>() {
			public ListCell<NamedObject<T>> call(ListView<NamedObject<T>> param) {
				final ListCell<NamedObject<T>> cell = new ListCell<NamedObject<T>>() {
					@Override
					public void updateItem(NamedObject<T> item, boolean empty) {
						super.updateItem(item,empty);
						if (!empty && item != null) 
                           textProperty().bind(item.nameProperty());
                        else {
                            textProperty().unbind();
                            setText("");
                        }
                            
					}
				};
				return cell;
			}
		});
		HBox.setMargin(test,new Insets(0,1,0,0));
		HBox.setMargin(listView,new Insets(1,0,1,1));
		HBox.setHgrow(test,Priority.ALWAYS);
	}
	
	public ListView<NamedObject<T>> getListView() {
		return listView;
	}
    
    public void removeSelectedItem() {
        NamedObject<T> item = listView.getSelectionModel().getSelectedItem();
        if (item != null) 
            listView.getItems().remove(item);
        else 
            Dialogs.create().owner(this).
				title(strings.getProperty("error")).
				masthead(strings.getProperty("anErrorOccurredMessage")).
				message(strings.getProperty("noSelectedItemMessage")).
				showError();
    }
    
    public int getSelectedIndex() {
        return listView.getSelectionModel().getSelectedIndex();
    }
    
    public T getSelectedItem() {
        return listView.getSelectionModel().getSelectedItem().bean;
    }
}
