package cf.lukasheinzl.crypto.key;

import java.math.BigInteger;

/**
 * Implementation of the {@link cf.lukasheinzl.crypto.key.Key Key} interface for using longs
 * 
 * @author Lukas Heinzl
 *
 */
public final class LongKey implements Key{

	private final long key;

	/**
	 * @param key
	 *            The long corresponding to this key
	 */
	public LongKey(long key){
		this.key = key;
	}

	/**
	 * Returns the long corresponding to this key
	 * 
	 * @return The long
	 */
	public long getKey(){
		return key;
	}

	@Override
	public byte[] getBytes(){
		return BigInteger.valueOf(key).toByteArray();
	}

}
