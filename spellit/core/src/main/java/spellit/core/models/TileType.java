package spellit.core.models;

/**
 * The Enum TileType. Defines the type of tile, which is further used for calculating the score of
 * placed letters.
 */
public enum TileType {
  TW("TW", "triple-word"), DW("DW", "double-word"), TL("TL", "triple-letter"),
  DL("DL", "double-letter"), STAR("", "star"), DEFAULT("", "");

  private final String value;
  private final String className;

  /**
   * Instantiates a new tile type.
   *
   * @param value the value
   * @param className the class name
   */
  private TileType(String value, String className) {
    this.value = value;
    this.className = className;
  }

  /**
   * Gets the value of the tile type.
   *
   * @return the value
   */
  public String getValue() {
    return this.value;
  }

  /**
   * Gets the tile type's CSS-classname.
   *
   * @return the classname
   */
  public String getClassName() {
    return this.className;
  }
}
