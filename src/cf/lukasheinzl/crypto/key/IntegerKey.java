package cf.lukasheinzl.crypto.key;

import java.math.BigInteger;

/**
 * Implementation of the {@link cf.lukasheinzl.crypto.key.Key Key} interface for using integers
 * 
 * @author Lukas Heinzl
 *
 */
public final class IntegerKey implements Key{

	private final int key;

	/**
	 * @param key
	 *            The integer corresponding to this key
	 */
	public IntegerKey(int key){
		this.key = key;
	}

	/**
	 * Returns the integer corresponding to this key
	 * 
	 * @return The integer
	 */
	public int getKey(){
		return key;
	}

	@Override
	public byte[] getBytes(){
		return BigInteger.valueOf(key).toByteArray();
	}

}
