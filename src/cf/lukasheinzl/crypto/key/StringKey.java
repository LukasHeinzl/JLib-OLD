package cf.lukasheinzl.crypto.key;

/**
 * Implementation of the {@link cf.lukasheinzl.crypto.key.Key Key} interface for using strings
 * 
 * @author Lukas Heinzl
 *
 */
public final class StringKey implements Key{

	private final String key;

	/**
	 * @param key
	 *            The string corresponding to this key
	 */
	public StringKey(String key){
		this.key = key;
	}

	/**
	 * Returns the string corresponding to this key
	 * 
	 * @return The string
	 */
	public String getKey(){
		return key;
	}

	@Override
	public byte[] getBytes(){
		return key.getBytes();
	}

}
