package cf.lukasheinzl.util;

/**
 * Used as a Wrapper for all Objects. This enables call-by-reference.
 * 
 * @author Lukas Heinzl
 *
 * @param <T>
 *            The type of object to wrap.
 */
public class Pointer<T>{

	private T o;

	/**
	 * 
	 * @param o
	 *            The object to wrap
	 */
	public Pointer(T o){
		this.o = o;
	}

	/**
	 * 
	 * @return The wrapped object
	 */
	public T get(){
		return o;
	}

	/**
	 * 
	 * @param o
	 *            The object to wrap
	 */
	public void set(T o){
		this.o = o;
	}

	@Override
	public String toString(){
		return o.getClass().getName() + "* -> " + o.toString();
	}

}
