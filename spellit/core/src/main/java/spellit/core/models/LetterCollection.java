package spellit.core.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LetterCollection {

	private final ObservableList<Letter> letters = FXCollections.observableArrayList();
	private StringProperty textProperty = new SimpleStringProperty();
	private Random r = new Random();

	public LetterCollection() {
		try (InputStream in = this.getClass().getResourceAsStream("letters.csv")) {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
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

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}

		textProperty.bind(Bindings.createStringBinding(() -> {
			return String.valueOf(letters.size());
		}, letters));
	}

	public final StringProperty textProperty() {
		return this.textProperty;
	}

	public int size() {
		return letters.size();
	}

	public boolean isEmpty() {
		return letters.isEmpty();
	}

	public void put(Letter letter) {
		this.letters.add(letter);
	}

	public void removeLetter(Letter letter) {
		for (Letter l : letters) {
			if (l.character == letter.character) {
				letters.remove(l);
				break;
			}
		}
	}

	public void removeLetters(ArrayList<Letter> letterCollection) {
		for (Letter letter : letterCollection) {
			for (Letter l : letters) {
				if (l.character == letter.character) {
					letters.remove(l);
					break;
				}
			}
		}
	}

	public ArrayList<Letter> drawInitialLetters() {
		ArrayList<Letter> initialLetters = new ArrayList<Letter>();
		for (int i = 0; i < 7; i++) {
			initialLetters.add(letters.remove(r.nextInt(letters.size())));
		}
		return initialLetters;
	}

	public Letter drawRandomLetter() {
		return letters.remove(r.nextInt(letters.size()));
	}

}
