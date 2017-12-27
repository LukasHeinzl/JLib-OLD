package cf.lukasheinzl.crypto;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This class can be used to send encrypted data.
 * 
 * @author Lukas Heinzl
 *
 */
public class SecureOutputStream extends OutputStream{

	private final OutputStream	os;
	private final Key			key;

	/**
	 * Constructs a new secure output stream with the given key.
	 * 
	 * @param os
	 *            The output stream to write to
	 * @param key
	 *            The key used for encryption
	 */
	public SecureOutputStream(OutputStream os, Key key){
		this.os = os;
		this.key = key;
	}

	@Override
	public void write(int b) throws IOException{
		try{
			os.write(Crypter.encrypt(new byte[]{ (byte) b}, key.getAlgorithm(), key));
		} catch(CryptoException e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public void write(byte[] b) throws IOException{
		write(b, 0, b.length);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException{
		byte[] bb;

		try{
			bb = Crypter.encrypt(b, key.getAlgorithm(), key);
		} catch(CryptoException e){
			throw new RuntimeException(e);
		}

		os.write(bb, off, len);
	}

}
