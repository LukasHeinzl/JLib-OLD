package cf.lukasheinzl.lexing;

/**
 * This class represents a string token. It consists of the string itself and the corresponding token type.
 * 
 * @author Lukas Heinzl
 *
 */
public class Token{

	private String		value;
	private TokenType	type;

	/**
	 * Constructs a new Token with the given value and type.
	 * 
	 * @param value
	 *            The string value of this token
	 * @param type
	 *            The type of this token
	 */
	public Token(String value, TokenType type){
		this.value = value;
		this.type = type;
	}

	/**
	 * Returns the string value of this token
	 * 
	 * @return The string value
	 */
	public String getValue(){
		return value;
	}

	/**
	 * Returns the type of this token
	 * 
	 * @return The type
	 */
	public TokenType getType(){
		return type;
	}

	@Override
	public String toString(){
		return "{[" + type + "] " + value + "}";
	}

	@Override
	public boolean equals(Object other){
		if(getClass() != other.getClass()){
			return false;
		}

		Token t = (Token) other;

		return type == t.type && value.trim().equals(t.value.trim());
	}

	@Override
	public int hashCode(){
		return type.hashCode() * 2 + value.hashCode() * 5;
	}

}
