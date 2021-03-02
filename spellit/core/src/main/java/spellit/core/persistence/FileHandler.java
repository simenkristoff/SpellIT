package spellit.core.persistence;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import spellit.core.models.Game;

public class FileHandler {

	private static final String SAVE_DIR = "saves";

	private static ObjectMapper objectMapper = new ObjectMapper();

	public static void saveGame(Game game, String filename) {
		try {
			objectMapper.writeValue(new File(String.format("%s/%s.json", SAVE_DIR, filename)), game);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Game loadGame(String filename) throws JsonParseException, JsonMappingException, IOException {
		return objectMapper.readValue(new File(String.format("%s/%s", SAVE_DIR, filename)), Game.class);
	}

	public static File[] getSaveFiles() throws IOException {
		return new File(SAVE_DIR).listFiles();
	}
}
