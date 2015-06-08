package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javafx.beans.property.StringProperty;
import javafx.beans.property.StringPropertyBase;

public class ObservableProperties extends Properties {
	private final Map<String,StringProperty> properties = new HashMap<>();
		
	public StringProperty getObservableProperty(String key) {
		if (!containsKey(key))
			throw new IllegalArgumentException("No such property : " + key);
		return properties.get(key);
	}
	
	@Override
	public Object setProperty(String key, String value) {
		Object result = super.setProperty(key,value);
		updateProperty(key,value);
		return result;
	}
	
	@Override
	public void load(Reader reader) throws IOException {
		super.load(reader);
		refresh();
	}
	
	@Override
	public void load(InputStream fis) throws IOException {
		super.load(fis);
		refresh();
	}
	
	@Override
	public void loadFromXML(InputStream fis) throws IOException {
		super.loadFromXML(fis);
		refresh();
	}
	
	private void refresh() {
		for (Map.Entry<Object,Object> entry : entrySet()) {
			String key   = entry.getKey().toString();
			String value = entry.getValue().toString();
			updateProperty(key,value);
		}
	}
	
	private void updateProperty(String key, String value) {
		if (!properties.containsKey(key))
			properties.put(key,new ObservableProperty(this,key,value));
		else 
			properties.get(key).setValue(value);
	}
	
	private class ObservableProperty extends StringPropertyBase {
		private final ObservableProperties	bean;
		private final String				name;
		
		public ObservableProperty(ObservableProperties bean, String name, String value) {
			this.bean = bean;
			this.name = name;
			setValue(value);
		}
		
		@Override
		public Object getBean() {
			return bean;
		}

		@Override
		public String getName() {
			return name;
		}			
	}
}