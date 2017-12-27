package cf.lukasheinzl.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Used to modify/get values of fields and methods using Reflection. This is a little wrapper over Java's Reflection API.
 * 
 * @author Lukas Heinzl
 *
 */
public class Reflection{

	// no one should instantiate this class
	private Reflection(){

	}

	/**
	 * Used to modify the value of a field using Reflection.
	 * 
	 * @param where
	 *            The class where the field is located in
	 * @param what
	 *            The name of the field
	 * @param on
	 *            The object for an instance field or null for class fields
	 * @param newValue
	 *            The value to be set
	 * @throws ReflectionException
	 *             If the field is not found or could not be accessed
	 * @see java.lang.NoSuchFieldException
	 * @see java.lang.IllegalAccessException
	 */
	public static void mutate(Class<?> where, String what, Object on, Object newValue) throws ReflectionException{
		try{
			Field f = where.getDeclaredField(what);

			Field modF = Field.class.getDeclaredField("modifiers");
			modF.setAccessible(true);
			modF.setInt(f, f.getModifiers() & ~Modifier.FINAL);

			f.setAccessible(true);
			f.set(on, newValue);
		} catch(NoSuchFieldException | IllegalAccessException e){
			throw new ReflectionException("Could not mutate: " + e.getMessage(), e);
		}
	}

	/**
	 * Used to get the value of a field using Reflection.
	 * 
	 * @param where
	 *            The class the field is located in
	 * @param what
	 *            The name of the field
	 * @param on
	 *            The object for an instance field or null for class fields
	 * @return The value of the given field
	 * @throws ReflectionException
	 *             If the field is not found or could not be accessed
	 * @see java.lang.NoSuchFieldException
	 * @see java.lang.IllegalAccessException
	 */
	public static Object getValue(Class<?> where, String what, Object on) throws ReflectionException{
		try{
			Field f = where.getDeclaredField(what);

			Field modF = Field.class.getDeclaredField("modifiers");
			modF.setAccessible(true);
			modF.setInt(f, f.getModifiers() & ~Modifier.FINAL);

			f.setAccessible(true);
			return f.get(on);
		} catch(NoSuchFieldException | IllegalArgumentException | IllegalAccessException e){
			throw new ReflectionException("Could not get value: " + e.getMessage(), e);
		}
	}

	/**
	 * Used to get the method of a class using Reflection.
	 * 
	 * @param where
	 *            The class the method is located in
	 * @param what
	 *            The name of the method
	 * @param types
	 *            An array of Class&lt;?&gt; containing the types of the methods parameters
	 * @return The method
	 * @throws ReflectionException
	 *             If the method could not be found or accessed
	 * @see java.lang.NoSuchMethodException
	 * @see java.lang.SecurityException
	 */
	public static Method getMethod(Class<?> where, String what, Class<?>... types) throws ReflectionException{
		try{
			Method m = where.getMethod(what, types);
			m.setAccessible(true);
			return m;
		} catch(NoSuchMethodException | SecurityException e){
			throw new ReflectionException("Could not get method: " + e.getMessage(), e);
		}
	}

}
