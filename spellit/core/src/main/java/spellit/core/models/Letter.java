package spellit.core.models;

import java.io.Serializable;

public class Letter implements Serializable {

	private static final long serialVersionUID = 1L;

	public char character;
	public int points;

	public Letter() {
		super();
	}

	public Letter(char character, int points) {
		this.character = character;
		this.points = points;
	}

	@Override
	public String toString() {
		return "Letter [character=" + character + ", points=" + points + "]";
	}
}
