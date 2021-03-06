package spellit.core.models;

import java.io.Serializable;

/**
 * The Class Letter. Represents a letter with a character and points.
 */
public class Letter implements Serializable {

  private static final long serialVersionUID = 1L;

  public char character;
  public int points;

  /**
   * Instantiates a new letter.
   */
  public Letter() {
    super();
  }

  /**
   * Instantiates a new letter.
   *
   * @param character the character
   * @param points the points
   */
  public Letter(char character, int points) {
    this.character = character;
    this.points = points;
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "Letter [character=" + character + ", points=" + points + "]";
  }
}
