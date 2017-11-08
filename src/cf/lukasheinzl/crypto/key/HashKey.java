package cf.lukasheinzl.crypto.key;

import cf.lukasheinzl.crypto.CryptoException;
import cf.lukasheinzl.crypto.Hasher;

/**
 * Implementation of the {@link cf.lukasheinzl.crypto.key.Key Key} interface for using hashed keys
 * 
 * @author Lukas Heinzl
 *
 */
public class HashKey implements Key{

	private final Key		key;
	private final String	hashFunc;

	/**
	 * @param key
	 *            The key that should be hashed
	 * @param hashFunc
	 *            The name of the hash function to use
	 */
	public HashKey(Key key, String hashFunc){
		this.key = key;
		this.hashFunc = hashFunc;
	}

	/**
	 * Returns the key-object corresponding to this hashed-key
	 * 
	 * @return The key
	 */
	public Key getKey(){
		return key;
	}

	/**
	 * Returns the hash function to be used
	 * 
	 * @return The name of the has function
	 */
	public String getHashFunc(){
		return hashFunc;
	}

	@Override
	public byte[] getBytes(){
		try{
			return Hasher.hash(key.getBytes(), hashFunc);
		} catch(CryptoException e){
			throw new RuntimeException(e);
		}
	}

}
