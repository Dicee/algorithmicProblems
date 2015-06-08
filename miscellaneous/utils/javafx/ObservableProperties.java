package miscellaneous.utils.javafx;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

import javafx.beans.property.StringProperty;
import javafx.beans.property.StringPropertyBase;

public class ObservableProperties extends Properties {
	private static final long	serialVersionUID	= 1L;
	
	private final Map<String,StringProperty> properties = new HashMap<>();
		
	public StringProperty getObservableProperty(String key) {
		if (!containsKey(key))
			throw new IllegalArgumentException("No such property : " + key);
		return properties.get(key);
	}
	
	@Override 
	public void putAll(Map<? extends Object,? extends Object> that) {
		putAll(that,Function.identity(),Function.identity());
	}
	
	public void putAll(Map<? extends Object,? extends Object> that, Function<String,String> mapKeys, Function<String,String> mapValues) {
		for (Map.Entry<? extends Object,? extends Object> entry : that.entrySet()) {
			if (!(entry.getKey() instanceof String) || !(entry.getValue() instanceof String))
				throw new IllegalArgumentException("Parameter 'that' must be a Map<String,String>");
			setProperty(mapKeys.apply((String) entry.getKey()),mapValues.apply((String) entry.getValue()));
		}
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
		
		@Override
		public String toString() {
			return bean.getProperty(name);
		}
	}
}