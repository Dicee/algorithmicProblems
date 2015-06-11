package miscellaneous.utils.files;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import miscellaneous.utils.system.SystemProperties;


public final class FileUtils {
	private FileUtils() { }
	
	public static String getResourceAbsolutePath(String localPath, Class<?> clazz) {
		String path = clazz.getResource(localPath).getPath();
		return SystemProperties.isWindows() ? path.substring(1) : path;
	}
	
	/**
	 * Transforms a potentially system-dependent path into its canonical representation
	 * @param path potentially system-dependent path
	 * @return canonical path corresponding to $code{path}
	 */
	public static String toCanonicalPath(String path) { return path.replace(File.separatorChar,'/'); }
	
	public static boolean hasExtension(String path, List<String> extensions) {
		return hasExtension(new File(path),extensions);
	}
	
	public static boolean hasExtension(File file, List<String> extensions) {
		return extensions.stream().filter(file.getName()::endsWith).findAny().isPresent();
	}
	
	public static File toExtension(String path, String extension) {
		return toExtension(new File(path),extension);
	}
	
	public static File toExtension(File file, String extension) {
		if (hasExtension(file,Arrays.asList(extension))) return file;
		
		if (!extension.startsWith(".")) extension = "." + extension;
		
		String path = file.getAbsolutePath();
		int lastDot = path.lastIndexOf('.');
		return new File((lastDot < 0 ? path : path.substring(0,lastDot)) + extension);
	}
}

