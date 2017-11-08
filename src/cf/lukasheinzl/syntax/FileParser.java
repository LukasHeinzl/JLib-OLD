package cf.lukasheinzl.syntax;

import java.util.List;

import cf.lukasheinzl.lexing.Lexer;
import cf.lukasheinzl.lexing.Token;
import cf.lukasheinzl.lexing.TokenData;
import cf.lukasheinzl.lexing.TokenType;

/**
 * This class is used to turn any file into an formatted HTML-file.
 * 
 * @author Lukas Heinzl
 *
 */
public class FileParser{

	// no one should instantiate this class
	private FileParser(){

	}

	/**
	 * This method is used to format files.
	 * 
	 * @param content
	 *            The file content as a string
	 * @param fmts
	 *            The {@link cf.lukasheinzl.syntax.Formatter Formatters} to use for formatting
	 * @param data
	 *            The {@link cf.lukasheinzl.lexing.TokenData TokenData} to use for lexing
	 * @param styles
	 *            A string containing valid CSS
	 * @return A string containing valid HTML and the formatted file content (only contains the body part of the HTML
	 *         file)
	 */
	public static String doFile(String content, Formatter[] fmts, List<TokenData> data, String styles){
		int lineMax = content.length() - content.replace("\n", "").length();
		int lineFormat = (lineMax + "").length();

		content = content.replace("&", "&amp;");
		content = content.replace("<", "&lt;");
		content = content.replace(">", "&gt;");
		content = content.replace("    ", "\t");

		int lineNum = 2;
		String out = null;

		StringBuilder sb = new StringBuilder(
				"<div><span class='newline'>" + String.format("%" + lineFormat + "d", 1).replace(" ", "&nbsp;") + " </span>");

		Lexer t = new Lexer(content, data, false);

		while(t.hasNextToken()){
			out = null;
			Token tok = t.nextToken();

			for(Formatter f: fmts){
				if(f.isLocked()){
					continue;
				}

				out = f.process(tok);
				if(out != null){
					break;
				}
			}

			if(out != null){
				sb.append(out);
			}

			if(tok.getType() == TokenType.SPACE){
				sb.append(" ");
			} else if(tok.getType() == TokenType.NEW_LINE){
				sb.append("<br><span class='newline'>" + String.format("%" + lineFormat + "d", lineNum++).replace(" ", "&nbsp;") + " </span>");
			} else if(out == null){
				sb.append(tok.getValue());
			}
		}

		return finishFile(sb.toString().replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;"), styles);
	}

	/**
	 * This method is used for creating the file HTML file
	 * 
	 * @param content
	 *            The content that should be placed in &lt;body&gt;
	 * @param styles
	 *            The CSS styles that should be placed in &lt;style&gt;
	 * @return The finished HTML file (only contains the body part of the HTML file)
	 */
	private static String finishFile(String content, String styles){
		StringBuilder sb = new StringBuilder("<style>\n");
		sb.append(styles);
		sb.append(".newline {\n");
		sb.append("color: #AAAAAA;\n");
		sb.append("}\n");
		sb.append("</style>\n");
		sb.append(content);
		sb.append("\n</div>\n");

		return sb.toString();
	}

}
