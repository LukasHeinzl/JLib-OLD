package cf.lukasheinzl.lexing;

/**
 * This is the interface for all TokenTypes. It contains only one type: EMPTY. All other required types have to be
 * supplied from the user. These types should be enums implementing this interface.
 * 
 * @author Lukas Heinzl
 *
 */
public interface TokenType{

	/**
	 * Represents an empty string. This should only be used when the string to lex is empty / the lexer is finished.
	 */
	public TokenType	EMPTY			= new TokenType(){
											@Override
											public String toString(){
												return "EMPTY";
											}
										};;

	/**
	 * Represents any character sequence that does not have a specific type.
	 */
	public TokenType	ANY				= new TokenType(){
											@Override
											public String toString(){
												return "ANY";
											}
										};

	/**
	 * Represents an space character.
	 */
	public TokenType	SPACE			= new TokenType(){
											@Override
											public String toString(){
												return "SPACE";
											}
										};

	/**
	 * Represents an \r character.
	 */
	public TokenType	CARRIAGE_RETURN	= new TokenType(){
											@Override
											public String toString(){
												return "CARRIAGE_RETURN";
											}
										};

	/**
	 * Represents an \n character.
	 */
	public TokenType	NEW_LINE		= new TokenType(){
											@Override
											public String toString(){
												return "NEW_LINE";
											}
										};

	/**
	 * Represents an \t character.
	 */
	public TokenType	TAB				= new TokenType(){
											@Override
											public String toString(){
												return "TAB";
											}
										};

}
