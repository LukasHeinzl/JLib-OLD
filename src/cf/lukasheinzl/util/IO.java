package cf.lukasheinzl.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * This class contains a few IO utility methods.
 * 
 * @author Lukas Heinzl
 *
 */
public class IO{

	// no one should instantiate this class
	private IO(){

	}

	/**
	 * This method is used to compress multiple files into a single ZIP archive.
	 * 
	 * @param zip
	 *            The path to where to store the archive
	 * @param root
	 *            The path to the root of all files used (or null if the input files are not in one common
	 *            sub-directory)
	 * @param content
	 *            The paths to the files to store in the archive
	 * @throws IOException
	 *             If the files were not found or the archive could not be created or written
	 * @see #writeZip(Path, String[], byte[][])
	 */
	public static void writeZip(Path zip, Path root, Path... content) throws IOException{
		String[] names = new String[content.length];
		byte[][] data = new byte[content.length][];

		for(int i = 0; i < content.length; i++){
			if(root == null){
				names[i] = content[i].toString();
			} else{
				names[i] = root.relativize(content[i]).toString();
			}

			data[i] = Files.readAllBytes(content[i]);
		}

		writeZip(zip, names, data);
	}

	/**
	 * This method is used to store raw byte-data into a single ZIP archive.
	 * 
	 * @param zip
	 *            The path to where to store the archive
	 * @param names
	 *            The names of the individual data-packets (used for creating a file structure in the archive)
	 * @param content
	 *            The data-packets to write
	 * @throws IOException
	 *             If the archive could not be created or written
	 */
	public static void writeZip(Path zip, String[] names, byte[][] content) throws IOException{
		try(ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip.toFile()))){
			int min = names.length < content.length ? names.length : content.length;

			for(int i = 0; i < min; i++){
				zos.putNextEntry(new ZipEntry(names[i]));
				zos.write(content[i]);
				zos.closeEntry();
			}
		}
	}

	/**
	 * This method is used to pack multiple files into a single JAR file.
	 * 
	 * @param jar
	 *            The path to where to store the JAR file
	 * @param mainClass
	 *            The full name (package + class name) of the main class
	 * @param src
	 *            The path to the root folder where all packages start from
	 * @param content
	 *            The paths to the files to store in the JAR file
	 * @throws IOException
	 *             If the files were not found or the JAR file could not be created or written
	 */
	public static void writeJar(Path jar, String mainClass, Path src, Path... content) throws IOException{
		String[] names = new String[content.length];
		byte[][] data = new byte[content.length][];

		for(int i = 0; i < content.length; i++){
			if(src == null){
				names[i] = content[i].toString();
			} else{
				names[i] = src.relativize(content[i]).toString();
			}

			data[i] = Files.readAllBytes(content[i]);
		}

		writeJar(jar, mainClass, names, data);
	}

	/**
	 * This method is used to store raw byte-data into a single JAR file.
	 * 
	 * @param jar
	 *            The path to where to store the JAR file
	 * @param mainClass
	 *            The full name (package + class name) of the main class
	 * @param names
	 *            The names of the individual data-packets (used for creating a file structure in the JAR file)
	 * @param content
	 *            The data-packets to write
	 * @throws IOException
	 *             If the JAR file could not be created or written
	 */
	public static void writeJar(Path jar, String mainClass, String[] names, byte[][] content) throws IOException{
		Manifest m = new Manifest();
		m.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
		m.getMainAttributes().put(Attributes.Name.MAIN_CLASS, mainClass);

		try(JarOutputStream jos = new JarOutputStream(new FileOutputStream(jar.toFile()), m)){
			int min = names.length < content.length ? names.length : content.length;

			for(int i = 0; i < min; i++){
				jos.putNextEntry(new JarEntry(names[i]));
				jos.write(content[i]);
				jos.closeEntry();
			}
		}
	}

	/**
	 * This method can be used to search for files.
	 * 
	 * @param root
	 *            The root directory to start searching
	 * @param name
	 *            The name of the file to find
	 * @return The File or null if it could not be found
	 */
	public static File findFile(File root, String name){
		if(!root.isDirectory()){
			return null;
		}

		File[] children = root.listFiles();
		if(children == null || children.length == 0){
			return null;
		}

		File found = null;

		for(File f: children){
			if(f.getName().equals(name)){
				return f;
			}

			if(f.isDirectory()){
				found = findFile(f, name);
				if(found != null){
					return found;
				}
			}
		}

		return null;
	}

	/**
	 * This method can be used to copy files and directories recursively.
	 * 
	 * @param src
	 *            The source file / directory
	 * @param dest
	 *            The destination file / directory
	 * @throws IOException
	 *             If the files could not be written
	 */
	public static void copyFiles(File src, File dest) throws IOException{
		if(src.isFile()){
			Files.copy(src.toPath(), dest.toPath());
		} else if(src.isDirectory()){
			dest.mkdir();

			for(File f: src.listFiles()){
				copyFiles(f, new File(dest, f.getName()));
			}
		}
	}

	/**
	 * This method can be used to list all files recursively from the given path.
	 * 
	 * @param p
	 *            The path to start from
	 * @return A list of path containing all paths to the files found
	 */
	public static List<Path> listFiles(Path p){
		List<Path> paths = new ArrayList<>();

		if(p.toFile().isFile()){
			paths.add(p);
		} else if(p.toFile().isDirectory()){
			for(File f: p.toFile().listFiles()){
				paths.addAll(listFiles(f.toPath()));
			}
		}

		return paths;
	}

	/**
	 * This method can be used to delete files and directories recursively.
	 * 
	 * @param f
	 *            The file to start the deletion from
	 */
	public static void deleteFiles(File f){
		if(f.isDirectory()){
			for(File ff: f.listFiles()){
				deleteFiles(ff);
			}
		}

		f.delete();
	}

}
