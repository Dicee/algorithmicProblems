package miscellaneous.utils.files;

import java.io.File;

public final class FileUtils {
	public static String getResourceAbsolutePath(String localPath, Class<?> clazz) {
		return clazz.getResource(localPath).getPath().substring(1);
	}
	
	/**
	 * Transforms a potentially system-dependent path into its canonical representation
	 * @param path potentially system-dependent path
	 * @return canonical path corresponding to $code{path}
	 */
	public static String toCanonicalPath(String path) { return path.replace(File.separatorChar,'/'); }
}
