package spellit.core.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Class LetterCollection. Represents a collection of all the available letters in a game
 * instance. The letters are loaded from a file with points and a given amount of the respective
 * letters.
 *
 * @see Letter
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LetterCollection {

  private final ObservableList<Letter> letters = FXCollections.observableArrayList();
  private StringProperty textProperty = new SimpleStringProperty();
  private Random rand = new Random();

  /**
   * Instantiates a new letter collection.
   */
  public LetterCollection() {
    try (BufferedReader br = new BufferedReader(
        new InputStreamReader(this.getClass().getResourceAsStream("letters.csv"), "UTF-8"))) {
      String line = br.readLine();
      while ((line = br.readLine()) != null) {
        String[] values = line.split(";");
        int count = Integer.parseInt(values[2]);
        for (int i = 0; i < count; i++) {
          char letter = values[0].charAt(0);
          int points = Integer.parseInt(values[1]);
          letters.add(new Letter(letter, points));
        }

      }
      // Add blank letter tile
      for (int i = 0; i < 2; i++) {
        letters.add(new Letter(' ', 0));
      }
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    textProperty.bind(Bindings.createStringBinding(() -> {
      return String.valueOf(letters.size());
    }, letters));
  }

  /**
   * Text property.
   *
   * @return the string property
   */
  public final StringProperty textProperty() {
    return this.textProperty;
  }

  /**
   * Returns the size of the letter collection.
   *
   * @return the size
   */
  public int size() {
    return letters.size();
  }

  /**
   * Checks if the letter collection is empty.
   *
   * @return true, if empty
   */
  public boolean isEmpty() {
    return letters.isEmpty();
  }

  /**
   * Put a letter into the letter collection.
   *
   * @param letter the letter to insert
   */
  public void put(Letter letter) {
    this.letters.add(letter);
  }

  /**
   * Removes a letter from the letter collection.
   *
   * @param letter the letter to remove
   */
  public void removeLetter(Letter letter) {
    for (Letter l : letters) {
      if (l.getCharacter() == letter.getCharacter()) {
        letters.remove(l);
        break;
      }
    }
  }

  /**
   * Removes a list of letters.
   *
   * @param letterCollection the list of letters to remove
   */
  public void removeLetters(ArrayList<Letter> letterCollection) {
    for (Letter letter : letterCollection) {
      for (Letter l : letters) {
        if (l.getCharacter() == letter.getCharacter()) {
          letters.remove(l);
          break;
        }
      }
    }
  }

  /**
   * Draw 7 initial letters from the collection.
   *
   * @return the initial letters
   */
  public ArrayList<Letter> drawInitialLetters() {
    ArrayList<Letter> initialLetters = new ArrayList<Letter>();
    for (int i = 0; i < 7; i++) {
      initialLetters.add(letters.remove(rand.nextInt(letters.size())));
    }
    return initialLetters;
  }

  /**
   * Draw random letter.
   *
   * @return the random letter
   */
  public Letter drawRandomLetter() {
    if (!this.isEmpty()) {
      return letters.remove(rand.nextInt(letters.size()));
    }
    return null;
  }

}
