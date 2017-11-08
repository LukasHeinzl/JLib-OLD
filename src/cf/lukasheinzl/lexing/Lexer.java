package cf.lukasheinzl.lexing;

import java.util.List;
import java.util.regex.Matcher;

/**
 * This class is used to turn a given input string into a list of tokens.
 * 
 * @author Lukas Heinzl
 *
 */
public class Lexer{

	private List<TokenData>	data;
	private String			str;
	private boolean			trim;

	/**
	 * Constructs a new Lexer used to split the input string into tokens.
	 * 
	 * @param str
	 *            The input string to lex
	 * @param data
	 *            The {@link java.util.List List&lt;TokenData&gt;} containing the 'rules' on how to lex this string
	 * @param trim
	 *            If the string should be trimmed at every {@link #nextToken()} call
	 */
	public Lexer(String str, List<TokenData> data, boolean trim){
		this.str = str;
		this.data = data;
		this.trim = trim;
	}

	/**
	 * Returns the next token of the input string. The returned token is of type
	 * {@link cf.lukasheinzl.lexing.TokenType#EMPTY EMPTY} if there is nothing left to lex
	 * 
	 * @return The next token of the input string
	 */
	public Token nextToken(){
		if(trim){
			str = str.trim();
		}

		if(str.isEmpty()){
			return new Token("", TokenType.EMPTY);
		}

		for(TokenData d: data){
			Matcher m = d.getPattern().matcher(str);

			if(m.find()){
				String tok = m.group();

				if(trim){
					tok = tok.trim();
				}

				str = m.replaceFirst("");

				return new Token(tok, d.getType());
			}
		}

		throw new IllegalStateException("Could not parse: " + str);
	}

	/**
	 * Returns true if the next call to {@link #nextToken()} does not return a token of type
	 * {@link cf.lukasheinzl.lexing.TokenType#EMPTY EMPTY}.
	 * 
	 * @return If there are still tokens
	 */
	public boolean hasNextToken(){
		return !str.isEmpty();
	}

}
