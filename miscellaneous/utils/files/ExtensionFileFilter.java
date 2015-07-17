package miscellaneous.utils.files;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class ExtensionFileFilter extends FileFilter {
	private String	description;
	private String	regex;
	
	public static enum Locale { 
		FR("Fichiers"),
		EN("Files");

		private final String file;
		private Locale(String file) { this.file = file; }
	}

	public ExtensionFileFilter(Locale localization, String... extensions) {
		StringBuilder sbDes = new StringBuilder();
		StringBuilder sbReg = new StringBuilder();
		sbDes.append(localization.file).append(" ");
		
		int i = 0;
		for (String s : extensions) {
			sbDes.append(i == 0 ? "*." + s : ", *." + s);
			sbReg.append(i == 0 ? ".*\\.(" + s : i == extensions.length - 1 ? "|" + s + ")" : "|" + s);
			i++;
		}
		
		this.description = sbDes.toString();
		this.regex       = sbReg.toString();
	}
	
	public boolean accept(File f) {		
		return f.isDirectory() || f.getName().matches(regex);
	}
	
	public String getDescription() {	
		return description;
	}
}
