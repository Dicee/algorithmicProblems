package miscellaneous.utils.javafx.components;

import static miscellaneous.utils.javafx.Settings.PREF_THEME;
import static miscellaneous.utils.javafx.Settings.properties;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import scala.io.Codec;
import scala.io.Source;

/**
 * A syntax highlighting code editor for JavaFX created by wrapping a CodeMirror
 * code editor in a WebView.
 *
 * See http://codemirror.net for more information on using the codemirror
 * editor.
 */
public class CodeEditor extends BorderPane {
	private static final Map<String, String>	MIMES;
	public  static final List<String>			THEMES;
	
	static {
		List<String> res = new ArrayList<>();
		try {
			res = Arrays.asList(new File(CodeEditor.class.getResource("codemirror-4.8/theme").toURI()).listFiles()).stream()
				.map(f -> f.getName().substring(0,f.getName().lastIndexOf(".")))
				.collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		THEMES = new ArrayList<>(res);
	}
	
	/** a webview used to encapsulate the CodeMirror JavaScript. */
	public final WebView	webview	= new WebView();
	private String			mime	= "text/x-java";

	/**
	 * a snapshot of the code to be edited kept for easy initialization and
	 * reversion of editable code.
	 */
	private String editingCode;

	/**
	 * a template for editing code - this can be changed to any template derived
	 * from the supported modes at http://codemirror.net to allow syntax
	 * highlighted editing of a wide variety of languages.
	 */
	private static final String	editingTemplate	= Source.fromURL(CodeEditor.class.getResource("codemirror-4.8/editor.html"),
			Codec.UTF8()).mkString();
	
	public void refresh() {
		webview.getEngine().loadContent(applyEditingTemplate());
	}
	
	/**
	 * applies the editing template to the editing code to create the
	 * html+javascript source for a code editor.
	 */
	private String applyEditingTemplate() {
		return editingTemplate
			.replace("${code}",editingCode)
			.replace("${theme}",properties.getProperty(PREF_THEME))
			.replace("${mime}",mime)
			.replace("${language}",MIMES.get(mime));
	}
	
	public void setLanguage(String mime) {
		this.mime = mime;
		getCodeAndSnapshot();
		refresh();
	}
	
	/**
	 * sets the current code in the editor and creates an editing snapshot of
	 * the code which can be reverted to.
	 */
	public void setCode(String newCode) {
		this.editingCode = newCode;
		refresh();
	}

	/**
	 * returns the current code in the editor and updates an editing snapshot of
	 * the code which can be reverted to.
	 */
	public String getCodeAndSnapshot() {
		this.editingCode = (String) webview.getEngine().executeScript("editor.getValue();");
		return editingCode;
	}

	/** revert edits of the code to the last edit snapshot taken. */
	public void revertEdits() {
		setCode(editingCode);
	}

	/**
	 * Create a new code editor.
	 * @param editingCode the initial code to be edited in the code editor.
	 */
	public CodeEditor(String editingCode) {
		this.editingCode = editingCode;
		webview.getEngine().loadContent(applyEditingTemplate());
		setCenter(webview);
	}
	
	static {
		MIMES = new HashMap<>();
		MIMES.put("text/x-java"    ,"clike/clike.js"          );
		MIMES.put("text/x-c++src"  ,"clike/clike.js"          );
		MIMES.put("text/x-csrc"    ,"clike/clike.js"          );
		MIMES.put("text/x-scala"   ,"clike/clike.js"          );
		MIMES.put("text/x-stex"    ,"stex/stex.js"            );
		MIMES.put("text/javascript","javascript/javascript.js");
		MIMES.put("text/x-python"  ,"python/python.js"        );
	}
}