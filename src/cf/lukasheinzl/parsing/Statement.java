package cf.lukasheinzl.parsing;

import java.util.ArrayList;
import java.util.List;

import cf.lukasheinzl.lexing.Token;

/**
 * This class represents a list of tokens that are logically grouped together.
 * 
 * @author Lukas Heinzl
 *
 */
public class Statement{

	private List<Token> tokens;

	/**
	 * Constructs a new Statement with the given tokens.
	 * 
	 * @param tokens
	 *            The tokens this statement consists of
	 */
	public Statement(List<Token> tokens){
		this.tokens = tokens;
	}

	/**
	 * Returns the tokens this statement consists of
	 * 
	 * @return The tokens this statement consists of
	 */
	public List<Token> getTokens(){
		return tokens;
	}

	/**
	 * This method takes a list of tokens, groups them and returns a list of statements.
	 * 
	 * @param tokens
	 *            The tokens to group
	 * @param delimiters
	 *            Where to split the tokens
	 * @return A list of statements
	 */
	public static List<Statement> toStatements(List<Token> tokens, List<Token> delimiters){
		List<Statement> stmts = new ArrayList<>();
		List<Token> tmp = new ArrayList<>();

		for(Token tok: tokens){
			tmp.add(tok);

			if(delimiters.contains(tok)){
				stmts.add(new Statement(tmp));
				tmp = new ArrayList<>();
			}
		}

		return stmts;
	}

}
