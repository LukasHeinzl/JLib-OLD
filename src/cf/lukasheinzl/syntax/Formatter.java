package cf.lukasheinzl.syntax;

import cf.lukasheinzl.lexing.Token;

/**
 * This class is used to applied formats to tokens.
 * 
 * @author Lukas Heinzl
 *
 */
public class Formatter{

	protected Format	startFmt;
	protected Format	endFmt;
	private Formatter[]	locks;
	private Formatter[]	needs;
	private boolean		active;

	/**
	 * Constructs a new formatter with the given start and end formats.
	 * 
	 * @param startFmt
	 *            The format to apply at the start of this formated region (example: /*)
	 * @param endFmt
	 *            The format to apply at the end of this formated region (example: *&#47;)
	 */
	public Formatter(Format startFmt, Format endFmt){
		this.startFmt = startFmt;
		this.endFmt = endFmt;
		this.active = false;
	}

	/**
	 * Processes the input token and applies the correct format, if any, to it
	 * 
	 * @param input
	 *            The token to apply the format to
	 * @return The formatted string or null if not applicable
	 */
	public String process(Token input){
		if(!active && startFmt.canApply(input)){
			active = true;
			return startFmt.apply(input);
		}

		if(active && endFmt.canApply(input)){
			active = false;
			return endFmt.apply(input);
		}

		return null;
	}

	/**
	 * Checks if this formatter can be used or not
	 * 
	 * @return False if no locking formatters are active and all needed formatters are active
	 */
	public boolean isLocked(){
		if(locks == null || locks.length == 0){
			return false;
		}

		for(Formatter f: locks){
			if(f.isActive()){
				return true;
			}
		}

		if(needs != null && needs.length > 0){
			for(Formatter f: needs){
				if(!f.isActive()){
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Returns if this formatter is currently active
	 * 
	 * @return If this formatter is currently active
	 */
	public boolean isActive(){
		return active;
	}

	/**
	 * Sets the locking formatters
	 * 
	 * @param locks
	 *            The locking formatters
	 */
	public void setLocks(Formatter... locks){
		this.locks = locks;
	}

	/**
	 * Sets the needed formatters
	 * 
	 * @param needs
	 *            The needed formatters
	 */
	public void setNeeds(Formatter... needs){
		this.needs = needs;
	}

}
