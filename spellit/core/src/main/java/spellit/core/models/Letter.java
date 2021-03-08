package spellit.core.models;

import java.io.Serializable;

/**
 * The Class Letter. Represents a letter with a character and points.
 */
public class Letter implements Serializable {

  private static final long serialVersionUID = 1L;

  private char character;
  private int points;

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
   * Gets the character.
   *
   * @return the character
   */
  public char getCharacter() {
    return character;
  }

  /**
   * Sets the character.
   *
   * @param character the new character
   */
  public void setCharacter(char character) {
    this.character = character;
  }

  /**
   * Gets the points.
   *
   * @return the points
   */
  public int getPoints() {
    return points;
  }

  /**
   * Sets the points.
   *
   * @param points the new points
   */
  public void setPoints(int points) {
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
