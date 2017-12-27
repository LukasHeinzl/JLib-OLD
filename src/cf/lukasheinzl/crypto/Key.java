package cf.lukasheinzl.crypto;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

/**
 * This is Key-class used in the {@link cf.lukasheinzl.crypto} package
 * 
 * @author Lukas Heinzl
 *
 */
public class Key implements Serializable{

	private static final long	serialVersionUID	= 3589694371087874818L;
	private String				algorithm;
	private byte[]				bytes;

	/**
	 * Constructs a new Key from the given bytes.
	 * 
	 * @param algorithm
	 *            The algorithm used to generate the bytes
	 * 
	 * @param bytes
	 *            The byte[] corresponding to this key
	 */
	public Key(String algorithm, byte... bytes){
		this.algorithm = algorithm;
		this.bytes = bytes;
	}

	/**
	 * Returns the algorithm that was used to generate this key.
	 * 
	 * @return The algorithm that was used to generate this key
	 */
	public String getAlgorithm(){
		return algorithm;
	}

	/**
	 * Returns the bytes corresponding to this key
	 * 
	 * @return The bytes corresponding to this key
	 */
	public byte[] getBytes(){
		return bytes;
	}

	/**
	 * Returns the size of this key in bits.
	 * 
	 * @return The size of this key in bits
	 */
	public int getKeySize(){
		return bytes.length * 8;
	}

	/**
	 * Used to generate a new random key with the given algorithm and key-size.
	 * 
	 * @param algorithm
	 *            The algorithm to use
	 * @param keysize
	 *            The size of the key
	 * @return The new Key
	 * @throws CryptoException
	 *             If the given algorithm could not be found, the key size is invalid or the generation failed.
	 */
	public static Key generateKey(String algorithm, int keysize) throws CryptoException{
		try{
			KeyGenerator kg = KeyGenerator.getInstance(algorithm);
			kg.init(keysize);
			return new Key(algorithm, kg.generateKey().getEncoded());
		} catch(NoSuchAlgorithmException e){
			throw new CryptoException("Could not generate key: " + e.getMessage(), e);
		}
	}

}
