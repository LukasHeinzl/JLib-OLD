package cf.lukasheinzl.syntax.lang;

/**
 * This interface is the base for all language specific formatters
 * @author Lukas Heinzl
 *
 */
public interface LanguageFormatter{

	String formatFile(String content);

	/**
	 * This method is used to construct language specific formatters.
	 * 
	 * @param lang
	 *            The language name (eg. java, c)
	 * @return A LanguageFormatter instance corresponding to the input language or null if no formatter exists
	 */
	public static LanguageFormatter getFormatter(String lang){
		if(lang.equalsIgnoreCase("java")){
			return new JavaFormatter();
		} else if(lang.equalsIgnoreCase("c")){
			return new CFormatter();
		}

		return null;
	}

}
