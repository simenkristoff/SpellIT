package spellit.core.models;

public enum TileType {
	TW("TW", "triple-word"), DW("DW", "double-word"), TL("TL", "triple-letter"), DL("DL", "double-letter"),
	STAR("", "star"), DEFAULT("", "");

	private final String value, className;

	private TileType(String value, String className) {
		this.value = value;
		this.className = className;
	}

	public String getValue() {
		return this.value;
	}

	public String getClassName() {
		return this.className;
	}
}
