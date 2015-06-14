package miscellaneous.utils.files;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public final class FileUtils {
	private FileUtils() { }
	
	public static String getResourceAbsolutePath(String localPath, Class<?> clazz) {
		String path = clazz.getResource(localPath).getPath();
		return SystemProperties.isWindows() ? path.substring(1) : path;
	}
	
	public static Path getPathRelativeToClass(Class<?> clazz, String path) {
		String name = clazz.getCanonicalName();
		return Paths.get(currentDirectory()).resolve(Paths.get(name.substring(0,name.lastIndexOf('.') + 1).replace('.','/') + path));
	}
	
	public static String currentDirectory() {
		String path = new File(".").getAbsolutePath();
		return path.substring(0,path.length() - 1);
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
