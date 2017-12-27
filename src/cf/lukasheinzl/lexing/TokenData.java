package cf.lukasheinzl.lexing;

import java.util.regex.Pattern;

/**
 * This class represents a token template. It consists of a regular expression matching the token-value and the
 * corresponding token type.
 * 
 * @author Lukas Heinzl
 *
 */
public class TokenData{

	private Pattern		pattern;
	private TokenType	type;

	/**
	 * Constructs a new TokenData object with the given value and type. It isused to tell a lexer how to split its input
	 * string.
	 * 
	 * @param pattern
	 *            The regular expression matching the wanted token-value
	 * @param type
	 *            The type of this token template
	 */
	public TokenData(Pattern pattern, TokenType type){
		this.pattern = pattern;
		this.type = type;
	}

	/**
	 * Returns the regular expression matching the wanted token-value
	 * 
	 * @return The regular expression
	 */
	public Pattern getPattern(){
		return pattern;
	}

	/**
	 * Returns the type of this token template
	 * 
	 * @return The type
	 */
	public TokenType getType(){
		return type;
	}

	/**
	 * Returns a TokenData object that matches the space character and is of type
	 * {@link cf.lukasheinzl.lexing.TokenType#SPACE SPACE}.
	 * 
	 * @return The TokenData object
	 */
	public static TokenData getSpaceData(){
		return new TokenData(Pattern.compile("^( )"), TokenType.SPACE);
	}

	/**
	 * Returns a TokenData object that matches any character and is of type {@link cf.lukasheinzl.lexing.TokenType#ANY
	 * ANY}.
	 * 
	 * @return The TokenData object
	 */
	public static TokenData getAnyData(){
		return new TokenData(Pattern.compile("^(.)"), TokenType.ANY);
	}

	/**
	 * Returns a TokenData object that matches the \r character and is of type
	 * {@link cf.lukasheinzl.lexing.TokenType#CARRIAGE_RETURN CARRIAGE_RETURN}.
	 * 
	 * @return The TokenData object
	 */
	public static TokenData getCarriageReturnData(){
		return new TokenData(Pattern.compile("^(\r)"), TokenType.CARRIAGE_RETURN);
	}

	/**
	 * Returns a TokenData object that matches the \n character and is of type
	 * {@link cf.lukasheinzl.lexing.TokenType#NEW_LINE NEW_LINE}.
	 * 
	 * @return The TokenData object
	 */
	public static TokenData getNewLineData(){
		return new TokenData(Pattern.compile("^(\n)"), TokenType.NEW_LINE);
	}

	/**
	 * Returns a TokenData object that matches the \t character and is of type
	 * {@link cf.lukasheinzl.lexing.TokenType#TAB TAB}.
	 * 
	 * @return The TokenData object
	 */
	public static TokenData getTabData(){
		return new TokenData(Pattern.compile("^(\t)"), TokenType.TAB);
	}

}
