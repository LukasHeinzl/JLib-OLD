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
 * This class can be used to turn C files into formatted HTML files. This uses the default Eclipse color-scheme.
 * 
 * @author Lukas Heinzl
 *
 */
public class CFormatter implements LanguageFormatter{

	// package private constructor
	CFormatter(){

	}

	/**
	 * This enum handles all C related TokenTypes
	 * 
	 * @author Lukas Heinzl
	 *
	 */
	enum CTokenType implements TokenType{

		KEYWORD, STRING, CHAR, LINE_CMT, BLOCK_CMT, ESCAPE_CHAR, CMT_END, PREPRO;

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
		KEYWORDS.add("auto");
		KEYWORDS.add("break");
		KEYWORDS.add("case");
		KEYWORDS.add("char");
		KEYWORDS.add("const");
		KEYWORDS.add("continue");
		KEYWORDS.add("double");
		KEYWORDS.add("do");
		KEYWORDS.add("else");
		KEYWORDS.add("enum");
		KEYWORDS.add("extern");
		KEYWORDS.add("float");
		KEYWORDS.add("for");
		KEYWORDS.add("goto");
		KEYWORDS.add("if");
		KEYWORDS.add("int");
		KEYWORDS.add("long");
		KEYWORDS.add("register");
		KEYWORDS.add("return");
		KEYWORDS.add("short");
		KEYWORDS.add("signed");
		KEYWORDS.add("sizeof");
		KEYWORDS.add("static");
		KEYWORDS.add("strcut");
		KEYWORDS.add("switch");
		KEYWORDS.add("typedef");
		KEYWORDS.add("union");
		KEYWORDS.add("unsigned");
		KEYWORDS.add("void");
		KEYWORDS.add("volatile");
		KEYWORDS.add("while");
	}

	/**
	 * This method is used to fill the {@link #DATA DATA} List.
	 */
	private static void setupData(){
		// keywords
		for(String kw: KEYWORDS){
			DATA.add(new TokenData(Pattern.compile("^(" + kw + "[ \\(\\[\\)\\{,\\.\\*])"), CTokenType.KEYWORD));
		}

		// escape chars
		for(String ec: new String[]{ "\\\\a", "\\\\b", "\\\\f", "\\\\n", "\\\\r", "\\\\v", "\\\\\\\\", "\\\\'", "\\\\\"", "\\\\\\?"}){
			DATA.add(new TokenData(Pattern.compile("^(" + ec + ")"), CTokenType.ESCAPE_CHAR));
		}

		// string and char
		DATA.add(new TokenData(Pattern.compile("^(\")"), CTokenType.STRING));
		DATA.add(new TokenData(Pattern.compile("^(')"), CTokenType.CHAR));

		// line / block // end comment
		DATA.add(new TokenData(Pattern.compile("^(//)"), CTokenType.LINE_CMT));
		DATA.add(new TokenData(Pattern.compile("^(/\\*)"), CTokenType.BLOCK_CMT));
		DATA.add(new TokenData(Pattern.compile("^(\\*/)"), CTokenType.CMT_END));

		// preprocessor
		DATA.add(new TokenData(Pattern.compile("^(#)"), CTokenType.PREPRO));

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

		Formatter str = new Formatter(new Format("<span class='str'>", new Token("\"", CTokenType.STRING), true),
				new Format("</span>", new Token("\"", CTokenType.STRING), false));
		Formatter chr = new Formatter(new Format("<span class='str'>", new Token("'", CTokenType.CHAR), true),
				new Format("</span>", new Token("'", CTokenType.CHAR), false));
		Formatter cmt = new Formatter(new Format("<span class='cmt'>", new Token("//", CTokenType.LINE_CMT), true),
				new Format("</span>", new Token("\n", CTokenType.NEW_LINE), true));
		Formatter bmt = new Formatter(new Format("<span class='cmt'>", new Token("/*", CTokenType.BLOCK_CMT), true),
				new Format("</span>", new Token("*/", CTokenType.CMT_END), false));
		Formatter pp = new Formatter(new Format("<span class='prepro'>", new Token("#", CTokenType.PREPRO), true),
				new Format("</span>", new Token(" ", TokenType.SPACE), false));

		str.setLocks(chr, cmt, bmt);
		chr.setLocks(str, cmt, bmt);
		cmt.setLocks(str, chr, bmt);
		bmt.setLocks(str, chr, cmt);
		pp.setLocks(str, chr, cmt, bmt);

		for(String kw: KEYWORDS){
			Formatter f = new WordFormatter(new Format("<span class='kwd'>", new Token(kw, CTokenType.KEYWORD), true),
					new Format("</span>", new Token(kw, CTokenType.KEYWORD), false));
			f.setLocks(str, chr, cmt, bmt);
			fmts.add(f);
		}

		fmts.add(pp);
		fmts.add(str);
		fmts.add(chr);
		fmts.add(cmt);
		fmts.add(bmt);

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
		sb.append("\n\t\t\t.prepro {");
		sb.append("\n\t\t\t\tcolor: #999999;");
		sb.append("\n\t\t\t}");
		sb.append("\n\t\t\t");
		STYLES = sb.toString();
	}

	/**
	 * This method can be used to format C files.
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
