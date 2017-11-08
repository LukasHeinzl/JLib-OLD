package cf.lukasheinzl.crypto.key;

import java.math.BigInteger;

/**
 * Implementation of the {@link cf.lukasheinzl.crypto.key.Key Key} interface for using shorts
 * 
 * @author Lukas Heinzl
 *
 */
public final class ShortKey implements Key{

	private final short key;

	/**
	 * @param key
	 *            The short corresponding to this key
	 */
	public ShortKey(short key){
		this.key = key;
	}

	/**
	 * Returns the short corresponding to this key
	 * 
	 * @return The short
	 */
	public short getKey(){
		return key;
	}

	@Override
	public byte[] getBytes(){
		return BigInteger.valueOf(key).toByteArray();
	}

}
