package cf.lukasheinzl.crypto;

/**
 * A CryptoException is thrown when any other {@link javax.crypto} related exception is thrown in any class in the
 * {@link cf.lukasheinzl.crypto} package
 * 
 * @author Lukas Heinzl
 *
 */
public class CryptoException extends Exception{

	private static final long serialVersionUID = 3909236831843073680L;

	/**
	 * Constructs a CryptoException with no detail message. A detail message is a String that describes this particular
	 * exception.
	 */
	public CryptoException(){
		super();
	}

	/**
	 * Constructs a CryptoException with the specified detail message. A detail message is a String that describes this
	 * particular exception.
	 *
	 * @param message
	 *            the detail message.
	 */
	public CryptoException(String message){
		super(message);
	}

	/**
	 * Creates a {@code CryptoException} with the specified detail message and cause.
	 *
	 * @param message
	 *            the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A {@code null}
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public CryptoException(String message, Throwable cause){
		super(message, cause);
	}

	/**
	 * Creates a {@code CryptoException} with the specified cause and a detail message of
	 * {@code (cause==null ? null : cause.toString())} (which typically contains the class and detail message of
	 * {@code cause}).
	 *
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link #getCause()} method). (A {@code null}
	 *            value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public CryptoException(Throwable cause){
		super(cause);
	}

}
