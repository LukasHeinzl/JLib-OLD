package cf.lukasheinzl.util;

/**
 * A ReflectionException is thrown when any other {@link java.lang.reflect} related exception is thrown in any class in
 * the {@link cf.lukasheinzl.util.Reflection} class
 * 
 * @author Lukas Heinzl
 *
 */
public class ReflectionException extends Exception{

	private static final long serialVersionUID = -4555719873447565390L;

	/**
	 * Constructs a ReflectionException with no detail message. A detail message is a String that describes this
	 * particular exception.
	 */
	public ReflectionException(){
		super();
	}

	/**
	 * Constructs a ReflectionException with the specified detail message. A detail message is a String that describes
	 * this particular exception.
	 *
	 * @param message
	 *            the detail message.
	 */
	public ReflectionException(String message){
		super(message);
	}

	/**
	 * Creates a {@code ReflectionException} with the specified detail message and cause.
	 *
	 * @param message
	 *            the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A {@code null}
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public ReflectionException(String message, Throwable cause){
		super(message, cause);
	}

	/**
	 * Creates a {@code ReflectionException} with the specified cause and a detail message of
	 * {@code (cause==null ? null : cause.toString())} (which typically contains the class and detail message of
	 * {@code cause}).
	 *
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A {@code null}
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public ReflectionException(Throwable cause){
		super(cause);
	}

}
