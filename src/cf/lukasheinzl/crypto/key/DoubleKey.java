package cf.lukasheinzl.crypto.key;

import java.math.BigInteger;

/**
 * Implementation of the {@link cf.lukasheinzl.crypto.key.Key Key} interface for using doubles
 * 
 * @author Lukas Heinzl
 *
 */
public final class DoubleKey implements Key{

	private final double key;

	/**
	 * @param key
	 *            The double corresponding to this key
	 */
	public DoubleKey(double key){
		this.key = key;
	}

	/**
	 * Returns the double corresponding to this key
	 * 
	 * @return The double
	 */
	public double getKey(){
		return key;
	}

	@Override
	public byte[] getBytes(){
		return BigInteger.valueOf(Double.doubleToLongBits(key)).toByteArray();
	}

}
