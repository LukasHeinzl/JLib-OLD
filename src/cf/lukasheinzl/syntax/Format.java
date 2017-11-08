package cf.lukasheinzl.syntax;

import java.util.regex.Pattern;

import cf.lukasheinzl.lexing.Token;

/**
 * This class represents a Format.
 * 
 * @author Lukas Heinzl
 *
 */
public class Format{

	private String	fmt;
	private Token	t;
	private boolean	before;

	/**
	 * Constructs a new format that can be used by a {@link cf.lukasheinzl.syntax.Formatter Formatter}.
	 * 
	 * @param fmt
	 *            The format to insert (typically &lt;span style='...'&gt;)
	 * @param t
	 *            The token that matches this format
	 * @param before
	 *            If the format should be inserted before or after the token
	 */
	public Format(String fmt, Token t, boolean before){
		this.fmt = fmt;
		this.t = t;
		this.before = before;
	}

	/**
	 * Returns a string containing the given format if the input matches this format's token.
	 * 
	 * @param input
	 *            The input token to apply the format to
	 * @return The formatted string
	 */
	public String apply(Token input){
		if(before){
			return input.getValue().replaceFirst(Pattern.quote(t.getValue()), fmt + t.getValue());
		}

		return input.getValue().replace(t.getValue(), t.getValue() + fmt);
	}

	/**
	 * Tests if this format can be applied to the given token
	 * 
	 * @param input
	 *            The token to test
	 * @return If the format can be applied
	 */
	public boolean canApply(Token input){
		return t.getType() == input.getType() && input.getValue().contains(t.getValue());
	}

	/**
	 * Returns the format string
	 * 
	 * @return The format string
	 */
	public String getFmt(){
		return fmt;
	}

	/**
	 * Returns if the format should be inserted before or after the token
	 * 
	 * @return If the format should be inserted before or after the token
	 */
	public boolean isBefore(){
		return before;
	}

}
