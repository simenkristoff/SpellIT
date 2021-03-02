package spellit.core.exceptions;

public class InvalidWordException extends TurnException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidWordException(String word) {
		super(String.format("Ordet '%s' finnes ikke i ordboken som brukes", word));
	}

}
