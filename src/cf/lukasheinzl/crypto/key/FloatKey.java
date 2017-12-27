package cf.lukasheinzl.crypto.key;

import java.math.BigInteger;

/**
 * Implementation of the {@link cf.lukasheinzl.crypto.key.Key Key} interface for using floats
 * 
 * @author Lukas Heinzl
 *
 */
public final class FloatKey implements Key{

	private final float key;

	/**
	 * @param key
	 *            The float corresponding to this key
	 */
	public FloatKey(float key){
		this.key = key;
	}

	/**
	 * Returns the float corresponding to this key
	 * 
	 * @return The float
	 */
	public float getKey(){
		return key;
	}

	@Override
	public byte[] getBytes(){
		return BigInteger.valueOf(Float.floatToIntBits(key)).toByteArray();
	}

}
