package spellit.core.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import spellit.core.exceptions.InvalidWordException;
import spellit.core.exceptions.TurnException;
import spellit.core.models.Dictionary;
import spellit.core.models.Game;
import spellit.core.models.Tile;
import spellit.core.models.TileMap;
import spellit.core.models.TileType;

/**
 * The Class BoardParser. Util-class for parsing the tiles on the board and calculate the score of
 * recent placed/unprocessed tiles.
 */
public class BoardParser {

  private final Game game;
  private final TileMap tileMap;
  private final Dictionary dictionary;
  private final ArrayList<Tile> placedTiles;

  /**
   * Instantiates a new board parser.
   *
   * @param game the game
   * @param tileMap the tile map
   * @param dictionary the dictionary
   * @param placedTiles the placed tiles
   */
  public BoardParser(Game game, TileMap tileMap, Dictionary dictionary,
      ArrayList<Tile> placedTiles) {
    this.game = game;
    this.tileMap = tileMap;
    this.dictionary = dictionary;
    this.placedTiles = placedTiles;
  }

  /**
   * Parses the recently placed tiles and validates the word it makes up and potential subwords.
   * Throws error if any placement is wrong, or any of the words are invalid according to the
   * dictionary.
   *
   * @param unprocessedTiles the unprocessed tiles
   * @throws TurnException the turn exception
   */
  public void parseBoard(ArrayList<Tile> unprocessedTiles) throws TurnException {
    Tile horizOrigin = null;
    Tile vertiOrigin = null;
    ArrayList<Tile> queue = new ArrayList<Tile>();
    Map<Tile, Integer> subwords = new LinkedHashMap<Tile, Integer>();

    // Check if any letters are placed.
    if (unprocessedTiles.isEmpty()) {
      throw new TurnException("Du må legge et ord før du kan fortsette");
    }

    // Check if the first word placed goes through the board's center.
    if (game.getTurnCount() == 0) {
      verifyInitialTurn(unprocessedTiles);
    }

    for (Tile tile : unprocessedTiles) {

      // Verify tile placement. Error if any tile is placed alone
      if (!tileMap.hasLeft(tile) && !tileMap.hasRight(tile) && !tileMap.hasUp(tile)
          && !tileMap.hasDown(tile)) {
        throw new TurnException("Ugyldig plassering");
      }

      // Find the horizontal text origin, if any
      if (tileMap.hasLeft(tile) && horizOrigin == null) {
        horizOrigin = findOrigin(tileMap.getLeft(tile), Dir.HORIZONTAL);
      }

      // Find the vertical text origin, if any
      if (tileMap.hasUp(tile) && vertiOrigin == null) {
        vertiOrigin = findOrigin(tileMap.getUp(tile), Dir.VERTICAL);
      }
    }

    // Error if player placed in both directions
    if ((horizOrigin != null && !tileMap.getTile(horizOrigin).isProcessed())
        && (vertiOrigin != null && !tileMap.getTile(vertiOrigin).isProcessed())) {
      if ((tileMap.hasRight(horizOrigin) && !tileMap.getRight(horizOrigin).isProcessed())
          && (tileMap.hasDown(vertiOrigin) && !tileMap.getDown(vertiOrigin).isProcessed())) {
        throw new TurnException("Du kan kun plassere ord i én retning");
      }
    }

    // Lookup Horizontal word
    if (horizOrigin != null && tileMap.hasRight(horizOrigin)) {
      Tile parser = horizOrigin;

      while (true) {

        if (!tileMap.getTile(parser).isProcessed()) {
          if (tileMap.hasUp(parser)) {
            subwords.put(parser, findSubwords(tileMap.getUp(parser), Dir.VERTICAL));
          }
          if (tileMap.hasDown(parser)) {
            subwords.put(parser, findSubwords(tileMap.getDown(parser), Dir.VERTICAL));
          }
        }

        queue.add(parser);

        if (!tileMap.hasRight(parser)) {
          break;
        }
        parser = tileMap.getRight(parser);
      }
    } else if (vertiOrigin != null && tileMap.hasDown(vertiOrigin)) {
      Tile parser = vertiOrigin;

      while (true) {

        if (!tileMap.getTile(parser).isProcessed()) {
          if (tileMap.hasLeft(parser)) {
            subwords.put(parser, findSubwords(tileMap.getLeft(parser), Dir.HORIZONTAL));
          }
          if (tileMap.hasRight(parser)) {
            subwords.put(parser, findSubwords(tileMap.getRight(parser), Dir.HORIZONTAL));
          }
        }

        queue.add(parser);

        if (!tileMap.hasDown(parser)) {
          break;
        }
        parser = tileMap.getDown(parser);
      }
    }

    calculateScore(queue, subwords);
  }

  /**
   * Find origin of a placed character in order to read the word it is a part of. As the words in
   * this case are read from left to right (horizontal direction) or up to down (vertical
   * direction), the function will find the left/up tile (based on direction parameter) until there
   * is no neighbouring tile in this direction.
   *
   * @param tile the reference tile
   * @param direction the direction to look
   * @return the word's origin tile
   */
  private Tile findOrigin(Tile tile, Dir direction) {
    Tile origin = tile;
    switch (direction) {
      case HORIZONTAL:
        while (tileMap.hasLeft(origin)) {
          origin = tileMap.getLeft(origin);
        }
        break;
      case VERTICAL:
        while (tileMap.hasUp(origin)) {
          origin = tileMap.getUp(origin);
        }
        break;
      default:
        break;
    }

    return origin;
  }

  /**
   * Parses letters and verifies the word they make up. Throws an error if the word is invalid.
   *
   * @param tile the origin tile
   * @param direction the parse direction
   * @return the score of the word
   * @throws InvalidWordException the invalid word exception
   */
  private int parseLetters(Tile tile, Dir direction) throws InvalidWordException {
    StringBuilder sb = new StringBuilder();
    Tile parser = tile;
    sb.append(parser.getLetter().character);
    int points = parser.getLetter().points;
    switch (direction) {
      case HORIZONTAL:
        while (tileMap.hasRight(parser)) {
          parser = tileMap.getRight(parser);
          sb.append(parser.getLetter().character);
          points += parser.getLetter().points;
        }
        break;
      case VERTICAL:
        while (tileMap.hasDown(parser)) {
          parser = tileMap.getDown(parser);
          sb.append(parser.getLetter().character);
          points += parser.getLetter().points;
        }
        break;
      default:
        break;
    }
    System.out.println("Subword: " + sb.toString() + ", Points: " + points);
    if (this.dictionary.lookup(sb.toString())) {
      return points;
    } else {
      throw new InvalidWordException(sb.toString());
    }
  }

  /**
   * Find newly created subwords and verify that they exists in the dictionary.
   *
   * @param tile the tile
   * @param direction the direction
   * @return the int
   * @throws InvalidWordException the invalid word exception
   */
  private int findSubwords(Tile tile, Dir direction) throws InvalidWordException {
    Tile origin = findOrigin(tile, direction);
    return parseLetters(origin, direction);
  }

  /**
   * Calculates score of the main word and subwords - if any.
   *
   * @param tiles the tiles
   * @param subwords the subwords
   * @throws InvalidWordException the invalid word exception
   */
  private void calculateScore(ArrayList<Tile> tiles, Map<Tile, Integer> subwords)
      throws InvalidWordException {
    ArrayList<Integer> wordMultipliers = new ArrayList<Integer>();
    StringBuilder sb = new StringBuilder();
    int score = 0;
    int subscore = 0;

    for (Tile tile : tiles) {
      TileType bonus = tileMap.getBonus(tile);
      if (!tileMap.getTile(tile).isProcessed()) {
        tileMap.getTile(tile).setProcessed(true);

        // Add bonus to unprocessed tiles
        switch (bonus) {
          case TW:
            if (subwords.containsKey(tile)) {
              subscore += subwords.get(tile) * 3;
            }
            wordMultipliers.add(3);
            break;
          case DW:
            if (subwords.containsKey(tile)) {
              subscore += subwords.get(tile) * 2;
            }
            wordMultipliers.add(2);
            break;
          case TL:
            if (subwords.containsKey(tile)) {
              subscore += subwords.get(tile) + tile.getLetter().points * 2;
            }
            score += tile.getLetter().points * 3;
            break;
          case DL:
            if (subwords.containsKey(tile)) {
              subscore += subwords.get(tile) + tile.getLetter().points;
            }
            score += tile.getLetter().points * 2;
            break;
          default:
            score += tile.getLetter().points;
            break;
        }
      } else {
        score += tile.getLetter().points;
      }
      this.placedTiles.add(tileMap.getTile(tile));
      sb.append(tile.getLetter().character);
    }
    String word = sb.toString();
    System.out.println(String.format("Placed word: %s, Points: %d", word, score));
    System.out.println(String.format("Total score: %d", (score + subscore)));
    if (this.dictionary.lookup(word)) {

      // Apply word multiplier bonuses, if any
      for (Integer multiplier : wordMultipliers) {
        score *= multiplier;
      }

      this.game.getCurrentPlayer().addPoints(score + subscore);
    } else {
      throw new InvalidWordException(word);
    }
  }

  /**
   * Verify initial turn. Check if any letter is placed in the center of the board.
   *
   * @param unprocessedTiles the unprocessed tiles
   * @throws TurnException the turn exception
   */
  private void verifyInitialTurn(ArrayList<Tile> unprocessedTiles) throws TurnException {
    boolean verified = false;
    for (Tile tile : unprocessedTiles) {
      if (tile.getRow() == 7 && tile.getCol() == 7) {
        verified = true;
        break;
      }
    }
    if (!verified) {
      throw new TurnException("Ordet må ha et utgangspunkt i sentrum av brettet");
    }
  }

  /**
   * The Enum Dir, represents parsing direction.
   */
  private enum Dir {
    HORIZONTAL, VERTICAL,
  }
}
