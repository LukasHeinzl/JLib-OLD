package cf.lukasheinzl.parsing;

/**
 * This interface is the base of all parsers.
 * 
 * @author Lukas Heinzl
 *
 */
public interface Parser{

	/**
	 * This method checks if the given statement can be parsed by this parser.
	 * 
	 * @param s
	 *            The statement to check
	 * @return True if the statement can be parsed
	 */
	boolean canParse(Statement s);

	/**
	 * This method turns the given statement into an abstract syntax tree.
	 * 
	 * @param s
	 *            The statement to parse
	 * @return The AST corresponding to the given statement
	 */
	AstNode parse(Statement s);

}
