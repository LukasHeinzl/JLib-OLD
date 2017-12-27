package cf.lukasheinzl.crypto;

import java.security.KeyPairGenerator;

/**
 * This class represents a public-private-key-pair.
 * 
 * @author Lukas Heinzl
 *
 */
public class KeyPair{

	private final String	algorithm;
	private final int		keysize;
	private Key				privateKey;
	private Key				publicKey;

	/**
	 * Constructs a key-pair with the given algorithm and key size.
	 * 
	 * @param algorithm
	 *            The algorithm to use (eg. RSA)
	 * @param keysize
	 *            The size of the keys (eg. 512 bit)
	 */
	public KeyPair(String algorithm, int keysize){
		this.algorithm = algorithm;
		this.keysize = keysize;
	}

	/**
	 * Creates a public-private-key-pair.
	 * 
	 * @throws CryptoException
	 *             If the given algorithm could not be found, the key size is invalid or the generation failed.
	 */
	public void init() throws CryptoException{
		try{
			KeyPairGenerator kg = KeyPairGenerator.getInstance(algorithm);
			kg.initialize(keysize);

			java.security.KeyPair kp = kg.genKeyPair();

			privateKey = new Key(algorithm, kp.getPrivate().getEncoded());
			publicKey = new Key(algorithm, kp.getPublic().getEncoded());
		} catch(Exception e){
			throw new CryptoException("Could not generate key-pair: " + e.getMessage(), e);
		}
	}

	/**
	 * Returns the private-key of this key-pair.
	 * 
	 * @return The private-key of this key-pair
	 */
	public Key getPrivateKey(){
		return privateKey;
	}

	/**
	 * Returns the public-key of this key-pair.
	 * 
	 * @return The public-key of this key-pair
	 */
	public Key getPublicKey(){
		return publicKey;
	}

}
