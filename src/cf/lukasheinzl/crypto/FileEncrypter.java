package cf.lukasheinzl.crypto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import cf.lukasheinzl.crypto.key.Key;

/**
 * Used to encrypt/decrypt Files using {@link cf.lukasheinzl.crypto.key.Key Keys}
 * 
 * @author Lukas Heinzl
 *
 */
public class FileEncrypter{

	// no one should instantiate this class
	private FileEncrypter(){
	}

	/**
	 * Used to encrypt a File with a given encryption algorithm and key
	 * 
	 * @param file
	 *            The path to the file to encrypt
	 * @param algorithm
	 *            The name of the encryption algorithm to use
	 * @param key
	 *            The {@link cf.lukasheinzl.crypto.key.Key Key} to use for the encryption
	 * @param write
	 *            If this boolean is set to true the encrypted bytes will be written to the file
	 * @return A byte[] containing the encrypted data
	 * @throws CryptoException
	 *             If the encryption algorithm could not be found or the encryption failed
	 * @throws IOException
	 *             If the file could not be found or read / written
	 * @see java.security.NoSuchAlgorithmException
	 * @see java.security.InvalidKeyException
	 */
	public static byte[] encryptFile(String file, String algorithm, Key key, boolean write) throws CryptoException, IOException{
		byte[] data = Files.readAllBytes(Paths.get(file));
		byte[] enc = Crypter.encrypt(data, algorithm, key);

		if(write){
			Files.write(Paths.get(file), enc);
		}

		return enc;
	}

	/**
	 * Used to decrypt a File with a given decryption algorithm and key
	 * 
	 * @param file
	 *            The path to the file to decrypt
	 * @param algorithm
	 *            The name of the decryption algorithm to use
	 * @param key
	 *            The {@link cf.lukasheinzl.crypto.key.Key Key} to use for the decryption
	 * @param write
	 *            If this boolean is set to true the decrypted bytes will be written to the file
	 * @return A byte[] containing the decrypted data
	 * @throws CryptoException
	 *             If the decryption algorithm could not be found or the decryption failed
	 * @throws IOException
	 *             If the file could not be found or read / written
	 * @see java.security.NoSuchAlgorithmException
	 * @see java.security.InvalidKeyException
	 */
	public static byte[] decryptFile(String file, String algorithm, Key key, boolean write) throws CryptoException, IOException{
		byte[] data = Files.readAllBytes(Paths.get(file));
		byte[] dec = Crypter.decrypt(data, algorithm, key);

		if(write){
			Files.write(Paths.get(file), dec);
		}

		return dec;
	}

}
