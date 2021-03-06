package spellit.core.persistence;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import spellit.core.models.Game;

/**
 * The Class FileHandler. Handles logic for the game's save files.
 */
public class FileHandler {

  private static final String SAVE_DIR = "saves";

  private static ObjectMapper objectMapper = new ObjectMapper();

  /**
   * Saves a game instance to the save directory.
   *
   * @param game the game instance to save
   * @param filename the filename
   */
  public static void saveGame(Game game, String filename) {
    try {
      objectMapper.writeValue(new File(String.format("%s/%s.json", SAVE_DIR, filename)), game);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads a game instance from the save directory.
   *
   * @param filename the name of the file to load
   * @return the game instance
   * @throws JsonParseException the JSON parse exception
   * @throws JsonMappingException the JSON mapping exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static Game loadGame(String filename)
      throws JsonParseException, JsonMappingException, IOException {
    return objectMapper.readValue(new File(String.format("%s/%s", SAVE_DIR, filename)), Game.class);
  }

  /**
   * Lists all the save files in the save directory.
   *
   * @return the save files
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static File[] getSaveFiles() throws IOException {
    return new File(SAVE_DIR).listFiles();
  }
}
