package spellit.core.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

@JsonIgnoreProperties(value = { "tileType" })
public class Tile {

	private final int row, col;
	private TileType tileType;
	private ObjectProperty<Letter> letterProperty = new SimpleObjectProperty<Letter>(null);
	private BooleanProperty processedProperty = new SimpleBooleanProperty(false);

	public Tile(int row, int col, TileType tileType) {
		this.row = row;
		this.col = col;
		this.tileType = tileType;
	}

	@JsonCreator
	public Tile(@JsonProperty("row") int row, @JsonProperty("col") int col, @JsonProperty("letter") Letter letter) {
		this.row = row;
		this.col = col;
		this.letterProperty.set(letter);
	}

	public boolean hasLetter() {
		return letterProperty.get() != null;
	}

	public Letter getLetter() {
		return this.letterProperty.get();
	}

	public void setLetter(Letter letter) {
		this.letterProperty.set(letter);
	}

	public void removeLetter() {
		this.letterProperty.set(null);
	}

	public boolean isProcessed() {
		return this.processedProperty.get();
	}

	public void setProcessed(boolean value) {
		this.processedProperty.set(value);
	}

	public int getRow() {
		return this.row;
	}

	public int getCol() {
		return this.col;
	}

	public TileType getTileType() {
		return this.tileType;
	}

	@Override
	public String toString() {
		return "Tile [row=" + row + ", col=" + col + ", tileType=" + tileType + ", letter=" + letterProperty.get()
				+ ", isProcessed=" + processedProperty.get() + "]";
	}

}
