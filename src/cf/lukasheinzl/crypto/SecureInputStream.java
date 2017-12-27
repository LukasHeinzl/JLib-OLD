package cf.lukasheinzl.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * This class can be used to receive encrypted data.
 * 
 * @author Lukas Heinzl
 *
 */
public class SecureInputStream extends InputStream{

	private final InputStream	is;
	private final Key			key;

	/**
	 * Constructs a new secure input stream with the given key.
	 * 
	 * @param is
	 *            The input stream to read from
	 * @param key
	 *            The key used for encryption
	 */
	public SecureInputStream(InputStream is, Key key){
		this.is = is;
		this.key = key;
	}

	@Override
	public int read() throws IOException{
		int i = is.read();

		if(i == -1){
			return -1;
		}

		try{
			return Crypter.decrypt(new byte[]{ (byte) i}, key.getAlgorithm(), key)[0];
		} catch(CryptoException e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public int read(byte[] b) throws IOException{
		return read(b, 0, b.length);
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException{
		int i = is.read(b, off, len);

		if(i == -1){
			return -1;
		}

		byte[] bb = Arrays.copyOf(b, i);
		byte[] dec;
		try{
			dec = Crypter.decrypt(bb, key.getAlgorithm(), key);
		} catch(CryptoException e){
			throw new RuntimeException(e);
		}

		System.arraycopy(bb, 0, b, 0, dec.length);
		return dec.length;
	}

}
