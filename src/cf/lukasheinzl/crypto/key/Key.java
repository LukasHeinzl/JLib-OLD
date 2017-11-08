package cf.lukasheinzl.crypto.key;

/**
 * This is the base-interface for all Keys used in the {@link cf.lukasheinzl.crypto} package
 * 
 * @author Lukas Heinzl
 *
 */
public interface Key{

	/**
	 * Returns the bytes of the key implementation
	 * 
	 * @return The bytes corresponding to this key
	 */
	byte[] getBytes();

}
