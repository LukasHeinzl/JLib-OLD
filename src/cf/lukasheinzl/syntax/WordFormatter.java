package cf.lukasheinzl.syntax;

import cf.lukasheinzl.lexing.Token;

/**
 * This class is a subclass of {@link cf.lukasheinzl.syntax.Formatter Formatter} and is used for formats that only apply
 * to a single token.
 * 
 * @author Lukas Heinzl
 *
 */
public class WordFormatter extends Formatter{

	/**
	 * Constructs a WordFormatter with the given formats
	 * 
	 * @param startFmt
	 *            The format to insert before a given token
	 * @param endFmt
	 *            The format to insert after a given token
	 */
	public WordFormatter(Format startFmt, Format endFmt){
		super(startFmt, endFmt);
	}

	@Override
	public String process(Token input){
		if(startFmt.canApply(input)){
			return startFmt.apply(new Token(endFmt.apply(input), null));
		}

		return null;
	}

}
