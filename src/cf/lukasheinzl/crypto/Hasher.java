package cf.lukasheinzl.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Used to hash byte[]
 * 
 * @author Lukas Heinzl
 *
 */
public class Hasher{

	// no one should instantiate this class
	private Hasher(){
	}

	/**
	 * Used to hash a byte[] with a given encryption algorithm
	 * 
	 * @param what
	 *            The byte[] to hash
	 * @param how
	 *            The name of the encryption algorithm to use
	 * @return The byte[] containing the hashed data
	 * @throws CryptoException
	 *             If the encryption algorithm could not be found
	 * @see java.security.NoSuchAlgorithmException
	 */
	public static byte[] hash(byte[] what, String how) throws CryptoException{
		try{
			MessageDigest md = MessageDigest.getInstance(how);
			return md.digest(what);
		} catch(NoSuchAlgorithmException e){
			throw new CryptoException("Could not hash: " + e.getMessage(), e);
		}
	}

}
