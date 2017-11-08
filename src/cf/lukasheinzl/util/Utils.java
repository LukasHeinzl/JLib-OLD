package cf.lukasheinzl.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.util.function.Supplier;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * This class contains a few utility methods.
 * 
 * @author Lukas Heinzl
 *
 */
public class Utils{

	// no one should instantiate this class
	private Utils(){

	}

	/**
	 * This method is used as a changing replaceAll for strings.
	 * 
	 * @param s
	 *            The string to modify
	 * @param what
	 *            What to replace
	 * @param f
	 *            The supplier that supplies the replacement
	 * @return The modified string
	 */
	public static String replaceForEach(String s, String what, Supplier<String> f){
		StringBuilder sb = new StringBuilder();
		String[] parts = s.split(what);

		for(int i = 0; i < parts.length; i++){
			sb.append(parts[i]);

			if(i < parts.length - 1){
				sb.append(f.get());
			}
		}

		return sb.toString();
	}

	/**
	 * This method writes the file with the given content and tries to compile it.
	 * 
	 * @param java
	 *            The java code to compile
	 * @param f
	 *            The file to use for compilation (must be named after the public class from the string)
	 * @return True if the compilation was successful
	 * @throws IOException
	 *             If the temporary file could not be created or written
	 * @throws NullPointerException
	 *             If no java compiler was found
	 * @see #compile(File)
	 */
	public static boolean compile(String java, File f) throws IOException{
		Files.write(f.toPath(), java.getBytes());

		return compile(f);
	}

	/**
	 * This method is used to compile a java file.
	 * 
	 * @param f
	 *            The file to compile
	 * @return True if the compilation was successful
	 * @throws NullPointerException
	 *             If no java compiler was found
	 */
	public static boolean compile(File f){
		JavaCompiler javac = Utils.getCompiler();

		if(javac == null){
			throw new NullPointerException("No compiler available!");
		}

		return 0 == javac.run(null, null, null, f.getPath());
	}

	/**
	 * This method is used to load classes at runtime.
	 * 
	 * @param root
	 *            The root folder in which the class if in
	 * @param className
	 *            The full class name including the package name
	 * @return An instance of the newly loaded class
	 * @throws MalformedURLException
	 *             If the root file cannot be turned into an URL
	 * @throws ClassNotFoundException
	 *             If the given class could not be found
	 * @throws InstantiationException
	 *             If the class could not be instantiated
	 * @throws IllegalAccessException
	 *             If the classes constructor could not be accessed
	 */
	public static Object loadClass(File root, String className)
			throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		URLClassLoader cl = URLClassLoader.newInstance(new URL[]{ root.toURI().toURL()});
		Class<?> cls = Class.forName(className, true, cl);
		return cls.newInstance();
	}

	/**
	 * This method tries to find a Java compiler.
	 * 
	 * @return An instance of {@link javax.tools.JavaCompiler JavaCompiler} or null if no compiler could be found
	 */
	public static JavaCompiler getCompiler(){
		// standard way
		JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
		if(javac == null){
			try{
				// try loading the class manually
				Class<?> javacClass = Class.forName("com.sun.tools.javac.api.JavacTool");
				Method create = javacClass.getMethod("create");
				javac = (JavaCompiler) create.invoke(null);
			} catch(Exception e){
				try{
					// try finding the tools.jar and load the class from there
					javac = (JavaCompiler) Utils.loadClass(IO.findFile(new File(System.getProperty("java.home")).getParentFile(), "tools.jar"),
							"com.sun.tools.javac.api.JavacTool");
				} catch(Exception e1){

				}
			}
		}

		return javac;
	}

	/**
	 * This method can be used to get the contents of an file by its URL location.
	 * 
	 * @param url
	 *            The URL to locate the file
	 * @return A string containing the contents of the file
	 * @throws IOException
	 *             If the file could not be found or read from
	 */
	public static String getFromURL(String url) throws IOException{
		try(BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()))){
			StringBuilder sb = new StringBuilder();
			String line = null;

			while((line = br.readLine()) != null){
				sb.append(line);
				sb.append('\n');
			}

			return sb.toString();
		}
	}

}
