package spellit.core.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The Class Player.
 */
@JsonIgnoreProperties(value = { "playing" })
public class Player {

  private final IntegerProperty pointsProperty = new SimpleIntegerProperty();
  private final StringProperty scoreProperty = new SimpleStringProperty();
  private final StringProperty nameProperty = new SimpleStringProperty();
  private Game game;
  private boolean playing = false;
  private ArrayList<Letter> letters;

  /**
   * Instantiates a new player.
   *
   * @param game the game instance
   * @param name the name of the player
   */
  public Player(Game game, String name) {
    this.game = game;
    this.nameProperty.set(name);
    this.pointsProperty.set(0);
    this.letters = new ArrayList<Letter>();
    scoreProperty.bind(Bindings.createStringBinding(() -> {
      return String.format("%s: %d", nameProperty.get(), pointsProperty.get());
    }, nameProperty, pointsProperty));
  }

  /**
   * Instantiates a new player.
   *
   * @param name the name of the player
   * @param letters the player's current letters
   */
  @JsonCreator
  public Player(@JsonProperty("name") String name, @JsonProperty("letters") List<Letter> letters) {
    this.nameProperty.set(name);
    this.pointsProperty.set(0);
    this.letters = new ArrayList<Letter>(letters);
  }

  /**
   * Points property.
   *
   * @return the integer property
   */
  public final IntegerProperty pointsProperty() {
    return this.pointsProperty;
  }

  /**
   * Score property.
   *
   * @return the string property
   */
  public final StringProperty scoreProperty() {
    return this.scoreProperty;
  }

  /**
   * Name property.
   *
   * @return the string property
   */
  public final StringProperty nameProperty() {
    return this.nameProperty;
  }

  /**
   * Gets the player's letters.
   *
   * @return the letters
   */
  public ArrayList<Letter> getLetters() {
    return this.letters;
  }

  /**
   * Sets the player's letters.
   *
   * @param letters the new letters
   */
  public void setLetters(ArrayList<Letter> letters) {
    this.letters = letters;
    this.game.letterCollection.removeLetters(letters);
  }

  /**
   * Draw initial letters from the games letter collection.
   */
  public void drawInitialLetters() {
    this.letters.addAll(game.letterCollection.drawInitialLetters());
  }

  /**
   * Draw a random letter from the games letter collection.
   *
   * @param index the index
   * @return the letter
   */
  public Letter drawLetter(int index) {
    Letter letter = game.letterCollection.drawRandomLetter();
    try {
      letters.set(index, letter);
    } catch (IndexOutOfBoundsException e) {
      if (index < 7) {
        letters.add(index, letter);
      } else {
        throw new IndexOutOfBoundsException();
      }
    }
    return letter;
  }

  /**
   * Swap letters. Turns in the current letters on hand and replaces them with new ones.
   */
  public void swapLetters() {
    int index = 0;
    for (Letter letter : letters) {
      game.letterCollection.put(letter);
      this.letters.set(index, game.letterCollection.drawRandomLetter());
      index++;
    }
  }

  /**
   * Checks if the current game turn belongs to the player.
   *
   * @return true, if is playing
   */
  public boolean isPlaying() {
    return this.game.getCurrentPlayer() == this;
  }

  /**
   * Adds points to the player's score.
   *
   * @param points the points
   */
  public void addPoints(int points) {
    this.pointsProperty.set(this.getPoints() + points);
  }

  /**
   * Sets the player's score.
   *
   * @param points the new points
   */
  public void setPoints(int points) {
    this.pointsProperty.set(points);
  }

  /**
   * Gets the player's points.
   *
   * @return the points
   */
  public int getPoints() {
    return this.pointsProperty.get();
  }

  /**
   * Gets the player's name.
   *
   * @return the name
   */
  public String getName() {
    return this.nameProperty.get();
  }

  /**
   * Sets the player's name.
   *
   * @param name the new name
   */
  public void setName(String name) {
    this.nameProperty.set(name);
  }

  /**
   * To string.
   *
   * @return the string
   */
  @Override
  public String toString() {
    return "Player [name=" + nameProperty.get() + ", playing=" + playing + ", points="
        + pointsProperty.get() + "]";
  }

}
