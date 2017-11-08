package cf.lukasheinzl.crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import cf.lukasheinzl.crypto.key.Key;

/**
 * Used to encrypt/decrypt byte[] using {@link cf.lukasheinzl.crypto.key.Key Keys}
 * 
 * @author Lukas Heinzl
 *
 */
public class Crypter{

	// no one should instantiate this class
	private Crypter(){
	}

	/**
	 * Used to encrypt a byte[] with a given encryption algorithm and key
	 * 
	 * @param what
	 *            The byte[] to encrypt
	 * @param how
	 *            The name of the encryption algorithm to use
	 * @param key
	 *            The {@link cf.lukasheinzl.crypto.key.Key Key} to use for the encryption
	 * @return The byte[] containing the encrypted data
	 * @throws CryptoException
	 *             If the encryption algorithm could not be found or the encryption failed
	 * @see java.security.NoSuchAlgorithmException
	 * @see java.security.InvalidKeyException
	 */
	public static byte[] encrypt(byte[] what, String how, Key key) throws CryptoException{
		try{
			SecretKeySpec sks = new SecretKeySpec(key.getBytes(), how);

			Cipher c = Cipher.getInstance(how);
			c.init(Cipher.ENCRYPT_MODE, sks);

			return c.doFinal(what);
		} catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
			throw new CryptoException("Could not encrypt: " + e.getMessage(), e);
		}
	}

	/**
	 * Used to decrypt a byte[] with a given decryption algorithm and key
	 * 
	 * @param what
	 *            The byte[] to decrypt
	 * @param how
	 *            The name of the encryption algorithm to use
	 * @param key
	 *            The {@link cf.lukasheinzl.crypto.key.Key Key} to use for the encryption
	 * @return The byte[] containing the decrypted data
	 * @throws CryptoException
	 *             If the decryption algorithm could not be found or the decryption failed
	 * @see java.security.NoSuchAlgorithmException
	 * @see java.security.InvalidKeyException
	 */
	public static byte[] decrypt(byte[] what, String how, Key key) throws CryptoException{
		try{
			SecretKeySpec sks = new SecretKeySpec(key.getBytes(), how);

			Cipher c = Cipher.getInstance(how);
			c.init(Cipher.DECRYPT_MODE, sks);

			return c.doFinal(what);
		} catch(NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e){
			throw new CryptoException("Could not encrypt: " + e.getMessage(), e);
		}
	}

	/**
	 * Returns the list of available cipher and hashing algorithms.
	 * 
	 * @return The list of available cipher and hashing algorithms
	 */
	public static List<String> getAvailableAlgorithms(){
		List<String> algos = new ArrayList<>();

		for(Provider p: Security.getProviders()){
			for(Service s: p.getServices()){
				algos.add(s.getAlgorithm());
			}
		}

		return algos;
	}

}
