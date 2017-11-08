package cf.lukasheinzl.syntax.lang;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import cf.lukasheinzl.lexing.Token;
import cf.lukasheinzl.lexing.TokenData;
import cf.lukasheinzl.lexing.TokenType;
import cf.lukasheinzl.syntax.FileParser;
import cf.lukasheinzl.syntax.Format;
import cf.lukasheinzl.syntax.Formatter;
import cf.lukasheinzl.syntax.WordFormatter;

/**
 * This class can be used to turn Java files into formatted HTML files. This uses the default Eclipse color-scheme.
 * 
 * @author Lukas Heinzl
 *
 */
public class JavaFormatter implements LanguageFormatter{

	// package private constructor
	JavaFormatter(){

	}

	/**
	 * This enum handles all Java related TokenTypes
	 * 
	 * @author Lukas Heinzl
	 *
	 */
	enum JavaTokenType implements TokenType{

		KEYWORD, STRING, CHAR, LINE_CMT, BLOCK_CMT, DOC_CMT, ESCAPE_CHAR, CMT_END;

	}

	// this contains all java keywords and the reserved words null, true and false
	private static final List<String>		KEYWORDS	= new ArrayList<>();

	// this contains all java related TokenData
	private static final List<TokenData>	DATA		= new ArrayList<>();

	// this contains all java related Formatters
	private static Formatter[]				FMTS		= null;

	// this contains the CSS formatting for java files
	private static String					STYLES		= null;

	/**
	 * This method is used to fill the {@link #KEYWORDS KEYWORDS} List.
	 */
	private static void setupKeywords(){
		KEYWORDS.add("abstract");
		KEYWORDS.add("continue");
		KEYWORDS.add("for");
		KEYWORDS.add("new");
		KEYWORDS.add("switch");
		KEYWORDS.add("assert");
		KEYWORDS.add("default");
		KEYWORDS.add("goto");
		KEYWORDS.add("package");
		KEYWORDS.add("synchronized");
		KEYWORDS.add("boolean");
		KEYWORDS.add("double");
		KEYWORDS.add("do");
		KEYWORDS.add("if");
		KEYWORDS.add("private");
		KEYWORDS.add("this");
		KEYWORDS.add("break");
		KEYWORDS.add("implements");
		KEYWORDS.add("protected");
		KEYWORDS.add("throws");
		KEYWORDS.add("throw");
		KEYWORDS.add("byte");
		KEYWORDS.add("else");
		KEYWORDS.add("import");
		KEYWORDS.add("public");
		KEYWORDS.add("case");
		KEYWORDS.add("enum");
		KEYWORDS.add("instanceof");
		KEYWORDS.add("return");
		KEYWORDS.add("transient");
		KEYWORDS.add("catch");
		KEYWORDS.add("extends");
		KEYWORDS.add("interface");
		KEYWORDS.add("int");
		KEYWORDS.add("short");
		KEYWORDS.add("try");
		KEYWORDS.add("char");
		KEYWORDS.add("finally");
		KEYWORDS.add("final");
		KEYWORDS.add("static");
		KEYWORDS.add("void");
		KEYWORDS.add("class");
		KEYWORDS.add("long");
		KEYWORDS.add("strictfp");
		KEYWORDS.add("volatile");
		KEYWORDS.add("const");
		KEYWORDS.add("float");
		KEYWORDS.add("native");
		KEYWORDS.add("super");
		KEYWORDS.add("while");
		KEYWORDS.add("false");
		KEYWORDS.add("true");
		KEYWORDS.add("null");
	}

	/**
	 * This method is used to fill the {@link #DATA DATA} List.
	 */
	private static void setupData(){
		// keywords
		for(String kw: KEYWORDS){
			DATA.add(new TokenData(Pattern.compile("^(" + kw + "[ \\(\\[\\)\\{,\\.])"), JavaTokenType.KEYWORD));
		}

		// escape chars
		for(String ec: new String[]{ "\\\\t", "\\\\b", "\\\\n", "\\\\r", "\\\\f", "\\\\'", "\\\\\"", "\\\\\\\\"}){
			DATA.add(new TokenData(Pattern.compile("^(" + ec + ")"), JavaTokenType.ESCAPE_CHAR));
		}

		// string and char
		DATA.add(new TokenData(Pattern.compile("^(\")"), JavaTokenType.STRING));
		DATA.add(new TokenData(Pattern.compile("^(')"), JavaTokenType.CHAR));

		// line / block / doc // end comment
		DATA.add(new TokenData(Pattern.compile("^(//)"), JavaTokenType.LINE_CMT));
		DATA.add(new TokenData(Pattern.compile("^(/\\*\\*)"), JavaTokenType.DOC_CMT));
		DATA.add(new TokenData(Pattern.compile("^(/\\*)"), JavaTokenType.BLOCK_CMT));
		DATA.add(new TokenData(Pattern.compile("^(\\*/)"), JavaTokenType.CMT_END));

		// any other character and space / tab / new line
		DATA.add(TokenData.getNewLineData());
		DATA.add(TokenData.getTabData());
		DATA.add(TokenData.getCarriageReturnData());
		DATA.add(TokenData.getSpaceData());
		DATA.add(TokenData.getAnyData());
	}

	/**
	 * This method is used to fill the {@link #FMTS FMTS} Array.
	 */
	private static void setupFormatters(){
		List<Formatter> fmts = new ArrayList<>();

		Formatter str = new Formatter(new Format("<span class='str'>", new Token("\"", JavaTokenType.STRING), true),
				new Format("</span>", new Token("\"", JavaTokenType.STRING), false));
		Formatter chr = new Formatter(new Format("<span class='str'>", new Token("'", JavaTokenType.CHAR), true),
				new Format("</span>", new Token("'", JavaTokenType.CHAR), false));
		Formatter cmt = new Formatter(new Format("<span class='cmt'>", new Token("//", JavaTokenType.LINE_CMT), true),
				new Format("</span>", new Token("\n", JavaTokenType.NEW_LINE), true));
		Formatter doc = new Formatter(new Format("<span class='doc'>", new Token("/**", JavaTokenType.DOC_CMT), true),
				new Format("</span>", new Token("*/", JavaTokenType.CMT_END), false));
		Formatter bmt = new Formatter(new Format("<span class='cmt'>", new Token("/*", JavaTokenType.BLOCK_CMT), true),
				new Format("</span>", new Token("*/", JavaTokenType.CMT_END), false));

		str.setLocks(chr, cmt, bmt, doc);
		chr.setLocks(str, cmt, bmt, doc);
		cmt.setLocks(str, chr, bmt, doc);
		bmt.setLocks(str, chr, cmt, doc);
		doc.setLocks(str, chr, cmt, bmt);

		for(String kw: KEYWORDS){
			Formatter f = new WordFormatter(new Format("<span class='kwd'>", new Token(kw, JavaTokenType.KEYWORD), true),
					new Format("</span>", new Token(kw, JavaTokenType.KEYWORD), false));
			f.setLocks(str, chr, cmt, bmt, doc);
			fmts.add(f);
		}

		fmts.add(str);
		fmts.add(chr);
		fmts.add(cmt);
		fmts.add(bmt);
		fmts.add(doc);

		FMTS = fmts.toArray(new Formatter[fmts.size()]);
	}

	/**
	 * This method is used to fill the {@link #STYLES STYLES} String.
	 */
	private static void setupStyles(){
		StringBuilder sb = new StringBuilder();
		sb.append("\n\t\t\t* {");
		sb.append("\n\t\t\t\tfont-family: Consolas,monaco,monospace;");
		sb.append("\n\t\t\t}");
		sb.append("\n\t\t\t");
		sb.append("\n\t\t\t.str {");
		sb.append("\n\t\t\t\tcolor: #2A00FF;");
		sb.append("\n\t\t\t}");
		sb.append("\n\t\t\t");
		sb.append("\n\t\t\t.str * {");
		sb.append("\n\t\t\t\tcolor: #2A00FF !important;");
		sb.append("\n\t\t\t\tfont-weight: normal !important;");
		sb.append("\n\t\t\t}");
		sb.append("\n\t\t\t");
		sb.append("\n\t\t\t.kwd {");
		sb.append("\n\t\t\t\tcolor: #7B0052;");
		sb.append("\n\t\t\t\tfont-weight: bold;");
		sb.append("\n\t\t\t}");
		sb.append("\n\t\t\t");
		sb.append("\n\t\t\t.cmt {");
		sb.append("\n\t\t\t\tcolor: #3F7F5F;");
		sb.append("\n\t\t\t}");
		sb.append("\n\t\t\t");
		sb.append("\n\t\t\t.doc {");
		sb.append("\n\t\t\t\tcolor: #3F5FBF;");
		sb.append("\n\t\t\t}");
		sb.append("\n\t\t\t");
		STYLES = sb.toString();
	}

	/**
	 * This method can be used to format Java files.
	 * 
	 * @param content
	 *            The content of the file to format
	 * @return The formated HTML file (only contains the body part of the HTML file)
	 */
	public String formatFile(String content){
		if(KEYWORDS.isEmpty()){
			setupKeywords();
		}

		if(DATA.isEmpty()){
			setupData();
		}

		if(FMTS == null){
			setupFormatters();
		}

		if(STYLES == null){
			setupStyles();
		}

		return FileParser.doFile(content, FMTS, DATA, STYLES);
	}

}
