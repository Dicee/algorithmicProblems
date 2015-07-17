package miscellaneous.utils.javafx;

import static javafx.application.Application.STYLESHEET_CASPIAN;
import static javafx.application.Application.STYLESHEET_MODENA;
import static javafx.application.Application.setUserAgentStylesheet;
import impl.org.controlsfx.i18n.Localization;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.property.Property;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import miscellaneous.utils.javafx.components.CodeEditor;

public class Settings {
	public static final Properties				properties		= new Properties();
	public static final ObservableProperties	strings			= new ObservableProperties();
	public static final Map<String, MenuItem>	preferences		= new HashMap<>();

	public static final String					PREF_SKIN		= "defaultStyle";
	public static final String					PREF_LANGUAGE	= "defaultLanguage";
	public static final String					PREF_THEME		= "defaultTheme";

	public static void init() {
		// load preferences and program constants
		loadProperties();
		// load localised texts and descriptions
		loadLocalizedTexts(properties.getProperty(properties.getProperty("defaultLanguage")));
	}
	
	public static void loadTemplatesText(File dir) {
		Properties p = new Properties();
		try {
			File f = new File(String.format("%s/lang/strings_%s.properties",dir.getPath(),
				properties.getProperty("defaultLanguage")));
			p.load(new FileReader(f));
			
			String name = String.format("%s.%sTemplate.",dir.getParentFile().getName(),dir.getName());
			strings.putAll(p,s -> name + s,Function.identity());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void loadProperties() {
		try (InputStreamReader isr = new InputStreamReader(
				Settings.class.getResourceAsStream("/properties/config.properties"))) {
			properties.load(isr);
			isr.close();
		} catch (IOException e) {
			System.out.println("Error while retrieving the application properties");
			e.printStackTrace();
		}
	}
    
    private static void loadLocalizedTexts(String lang) {
        try (InputStreamReader isr = new InputStreamReader(Settings.class.getResourceAsStream(lang))) {
			strings.load(isr);
			isr.close();
		} catch (IOException e) {
			System.out.println("Error while retrieving the application texts and descriptions");
			e.printStackTrace();
		}
	}
    
    public static void bindProperty(Property<String> toBind, String property) {
    	toBind.bind(strings.getObservableProperty(property));
    }
    
    public static Menu getChooseStyleMenu(final ImageView checkedIcon) {
		Menu chooseStyle       = new Menu();		
		final MenuItem caspian = new MenuItem("Caspian");
        final MenuItem modena  = new MenuItem("Modena");
		MenuItem selectedMenu;
		switch (properties.getProperty(PREF_SKIN)) {
			case "CASPIAN" : selectedMenu = caspian; break;
			default        : selectedMenu = modena;
		}
		
		selectedMenu.setGraphic(checkedIcon);
		preferences.put(PREF_SKIN,selectedMenu);
		setUserAgentStylesheet(properties.getProperty("defaultStyle"));
		
		chooseStyle.textProperty().bind(strings.getObservableProperty("skin"));
		
		caspian.setOnAction(ev -> {			
			setUserAgentStylesheet(STYLESHEET_CASPIAN);
			changePreference(caspian,PREF_SKIN,STYLESHEET_CASPIAN,checkedIcon);
		});
		modena.setOnAction(ev -> {
			setUserAgentStylesheet(STYLESHEET_MODENA);
			changePreference(modena,PREF_SKIN,STYLESHEET_MODENA,checkedIcon);
		});       
        chooseStyle.getItems().addAll(caspian,modena);
		return chooseStyle;
	}
    
    public static Menu getChooseLanguageMenu(final ImageView checkedIcon) {
		Menu chooseLanguage    = new Menu();
		final MenuItem french  = new MenuItem();
		final MenuItem english = new MenuItem();	
		MenuItem selectedMenu;
		
		chooseLanguage.textProperty().bind(strings.getObservableProperty("lang"));
		french        .textProperty().bind(strings.getObservableProperty("lang-fr"));
		english       .textProperty().bind(strings.getObservableProperty("lang-en"));
		
		switch (properties.getProperty(PREF_LANGUAGE)) {
			case "FR" : selectedMenu = french ; break;
			default   :	selectedMenu = english; break;
		}
		
		selectedMenu.setGraphic(checkedIcon);
		chooseLanguage.getItems().addAll(french,english);
		preferences.put(PREF_LANGUAGE,selectedMenu);
		
		french .setOnAction(languageChoiceAction(french,"FR","fr","FR",checkedIcon));   
		english.setOnAction(languageChoiceAction(english,"EN","en","UK",checkedIcon));
		return chooseLanguage;
	}
    
    public static Menu getChooseThemeMenu(final ImageView checkedIcon, Consumer<String> onChange) {
		Menu chooseTheme    = new Menu();
		chooseTheme.textProperty().bind(strings.getObservableProperty("theme"));
		
		MenuItem selectedMenu = null;
		for (String themeName : CodeEditor.THEMES) {
			MenuItem themeMenu  = new MenuItem(themeName);
			themeMenu.setOnAction(ev -> {
				changePreference(themeMenu,PREF_THEME,themeName,checkedIcon);
				onChange.accept(themeName);
			});   
			
			if (properties.getProperty(PREF_THEME).equals(themeName))
				selectedMenu = themeMenu;
			chooseTheme.getItems().add(themeMenu);
		}

		selectedMenu.setGraphic(checkedIcon);
		preferences.put(PREF_THEME,selectedMenu);
		return chooseTheme;
	}
	
    private static void changePreference(MenuItem clicked, String prefName, String value, Node node) {
		MenuItem checked = preferences.get(prefName);
		if (checked != clicked) {
			checked.setGraphic(null);
			clicked.setGraphic(node);
			preferences.put(prefName,clicked);
			updateProperty(prefName,value);
		}
	}	
    
	private static EventHandler<ActionEvent> languageChoiceAction(MenuItem menu, String propertyName, String lang, String country,
			ImageView checkedIcon) {
		return (ActionEvent ev) -> {
			if (menu != preferences.get(PREF_LANGUAGE)) {
				loadLocalizedTexts(properties.getProperty(propertyName));
				Localization.setLocale(new Locale(lang,country));
				changePreference(menu,PREF_LANGUAGE,propertyName,checkedIcon);
			}
		};
	}
	
	private static void updateProperty(String name, String value) {
		if (!properties.containsKey(name)) 
			throw new IllegalArgumentException(String.format("Property %s does not exist !",name));
		
		properties.put(name,value);
		
		Pattern p = Pattern.compile("\\s*([a-zA-Z0-9]*)\\s*=\\s*(.*)\\s*");
		try {
			Path           path = Paths.get(Settings.class.getResource("/properties/config.properties").toURI());
			Path           temp = Files.createTempFile("config",".properties");
			Files.copy(path,temp,StandardCopyOption.REPLACE_EXISTING);
			
			BufferedReader br   = Files.newBufferedReader(temp);
			BufferedWriter bw   = Files.newBufferedWriter(path);

			for (String line = br.readLine() ; line != null ; line = br.readLine()) {
				Matcher m = p.matcher(line);
				bw.write(m.matches() && m.group(1).equals(name) ? String.format("%s=%s\n",name,value) : line + "\n");
			}
			
			bw.close();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}