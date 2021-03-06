package spellit.core.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

@JsonIgnoreProperties(value = { "playing" })
public class Player {

	private final IntegerProperty pointsProperty = new SimpleIntegerProperty();
	private final StringProperty scoreProperty = new SimpleStringProperty();
	private final StringProperty nameProperty = new SimpleStringProperty();
	private Game game;
	private boolean playing = false;
	private ArrayList<Letter> letters;

	public Player(Game game, String name) {
		this.game = game;
		this.nameProperty.set(name);
		this.pointsProperty.set(0);
		this.letters = new ArrayList<Letter>();
		scoreProperty.bind(Bindings.createStringBinding(() -> {
			return String.format("%s: %d", nameProperty.get(), pointsProperty.get());
		}, nameProperty, pointsProperty));
	}

	@JsonCreator
	public Player(@JsonProperty("name") String name, @JsonProperty("letters") List<Letter> letters) {
		this.nameProperty.set(name);
		this.pointsProperty.set(0);
		this.letters = new ArrayList<Letter>(letters);
	}

	public final IntegerProperty pointsProperty() {
		return this.pointsProperty;
	}

	public final StringProperty scoreProperty() {
		return this.scoreProperty;
	}

	public final StringProperty nameProperty() {
		return this.nameProperty;
	}

	public ArrayList<Letter> getLetters() {
		return this.letters;
	}

	public void setLetters(ArrayList<Letter> letters) {
		this.letters = letters;
		this.game.letterCollection.removeLetters(letters);
	}

	public void drawInitialLetters() {
		this.letters.addAll(game.letterCollection.drawInitialLetters());
	}

	public Letter drawLetter(int index) {
		Letter letter = game.letterCollection.drawRandomLetter();
		letters.set(index, letter);
		return letter;
	}

	public void swapLetters() {
		int index = 0;
		for (Letter letter : letters) {
			game.letterCollection.put(letter);
			this.letters.set(index, game.letterCollection.drawRandomLetter());
			index++;
		}
	}

	public boolean isPlaying() {
		return this.game.getCurrentPlayer() == this;
	}

	public void addPoints(int points) {
		this.pointsProperty.set(this.getPoints() + points);
	}

	public void setPoints(int points) {
		this.pointsProperty.set(points);
	}

	public int getPoints() {
		return this.pointsProperty.get();
	}

	public String getName() {
		return this.nameProperty.get();
	}

	public void setName(String name) {
		this.nameProperty.set(name);
	}

	@Override
	public String toString() {
		return "Player [name=" + nameProperty.get() + ", playing=" + playing + ", points=" + pointsProperty.get() + "]";
	}

}
