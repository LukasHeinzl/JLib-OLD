package cf.lukasheinzl.crypto.key;

/**
 * Implementation of the {@link cf.lukasheinzl.crypto.key.Key Key} interface for using bytes
 * 
 * @author Lukas Heinzl
 *
 */
public final class ByteKey implements Key{

	private final byte key;

	/**
	 * @param key
	 *            The byte corresponding to this key
	 */
	public ByteKey(byte key){
		this.key = key;
	}

	/**
	 * Returns the byte corresponding to this key
	 * 
	 * @return The byte
	 */
	public byte getKey(){
		return key;
	}

	@Override
	public byte[] getBytes(){
		return new byte[]{ key};
	}

}
