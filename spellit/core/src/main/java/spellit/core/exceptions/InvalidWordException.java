package spellit.core.exceptions;

/**
 * The Class InvalidWordException.
 */
public class InvalidWordException extends TurnException {

  private static final long serialVersionUID = 1L;

  /**
   * Instantiates a new invalid word exception.
   *
   * @param word the invalid word
   */
  public InvalidWordException(String word) {
    super(String.format("Ordet '%s' finnes ikke i ordboken som brukes", word));
  }

}
