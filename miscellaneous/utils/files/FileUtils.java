package miscellaneous.utils.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import miscellaneous.utils.system.SystemProperties;

public final class FileUtils {
	private FileUtils() { }
	
	public static String getResourceAbsolutePath(String localPath, Class<?> clazz) {
		String path = clazz.getResource(localPath).getPath();
		return SystemProperties.isWindows() ? path.substring(1) : path;
	}
	
	@Deprecated
	public static Path getPathRelativeToClass(Class<?> clazz, String path) {
		return Paths.get(currentDirectory()).resolve(Paths.get(getPathToPackage(clazz) + path));
	}

	public static String getPathToPackage(Class<?> clazz) {
		String name = clazz.getCanonicalName();
		return name.substring(0,name.lastIndexOf('.') + 1).replace('.','/');
	}
	
	public static String currentDirectory() {
		String path = new File(".").getAbsolutePath();
		return path.substring(0,path.length() - 1);
	}
	
	public static String canonicalCurrentDirectory() { return toCanonicalPath(currentDirectory()); }
	
	public static void ensureExists(String dirPath) {
		File dir = new File(dirPath);
		if(!dir.exists()) dir.mkdirs();
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
	
	public static String readAllFile(String path) throws IOException {
		StringBuilder sb    = new StringBuilder();
		File          input = new File(path);
    	for (String line : Files.readAllLines(Paths.get(input.toURI()))) sb.append(line);
    	String lines = sb.toString();
		return lines;
    }
}
