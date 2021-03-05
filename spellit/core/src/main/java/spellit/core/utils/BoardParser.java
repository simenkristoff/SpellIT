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

public class BoardParser {

	private final Game game;
	private final TileMap tileMap;
	private final Dictionary dictionary;
	private final ArrayList<Tile> placedTiles;

	public BoardParser(Game game, TileMap tileMap, Dictionary dictionary, ArrayList<Tile> placedTiles) {
		this.game = game;
		this.tileMap = tileMap;
		this.dictionary = dictionary;
		this.placedTiles = placedTiles;
	}

	public void parseBoard(ArrayList<Tile> unprocessedTiles) throws TurnException {
		Tile hOrigin = null;
		Tile vOrigin = null;
		ArrayList<Tile> queue = new ArrayList<Tile>();
		Map<Tile, Integer> subwords = new LinkedHashMap<Tile, Integer>();

		if (unprocessedTiles.isEmpty()) {
			throw new TurnException("Du må legge et ord før du kan fortsette");
		}

		if (game.getTurnCount() == 0) {
			verifyInitialTurn(unprocessedTiles);
		}

		for (Tile tile : unprocessedTiles) {

			// Find the horizontal text origin, if any
			if (tileMap.hasLeft(tile) && hOrigin == null) {
				hOrigin = findOrigin(tileMap.getLeft(tile), Dir.HORIZONTAL);
			}

			// Find the vertical text origin, if any
			if (tileMap.hasUp(tile) && vOrigin == null) {
				vOrigin = findOrigin(tileMap.getUp(tile), Dir.VERTICAL);
			}

		}

		// Error if player placed in both directions
		if ((hOrigin != null && !tileMap.getTile(hOrigin).isProcessed())
				&& (vOrigin != null && !tileMap.getTile(vOrigin).isProcessed())) {
			if ((tileMap.hasRight(hOrigin) && !tileMap.getRight(hOrigin).isProcessed())
					&& (tileMap.hasDown(vOrigin) && !tileMap.getDown(vOrigin).isProcessed())) {
				throw new TurnException("Du kan kun plassere ord i én retning");
			}
		}

		// Lookup Horizontal word
		if (hOrigin != null && tileMap.hasRight(hOrigin)) {
			Tile parser = hOrigin;

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
		} else if (vOrigin != null && tileMap.hasDown(vOrigin)) {
			Tile parser = vOrigin;

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
		} else {
			throw new TurnException("Ugyldig plassering");
		}

		if (subwords.isEmpty() && queue.size() < 2) {
			throw new TurnException("Ordet må bestå av minst 2 bokstaver");
		}

		calculateScore(queue, subwords);
	}

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
		}

		return origin;
	}

	/**
	 * Parses a word in horizontal direction
	 * 
	 * @param tile the horizontal origin tile, i.e start tile
	 */
	private int parseLetters(Tile tile, Dir direction) {
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
		}
		System.out.println("Subword: " + sb.toString() + ", Points: " + points);
		if (this.dictionary.lookup(sb.toString())) {
			return points;
		}

		return 0;
	}

	private int findSubwords(Tile tile, Dir direction) {
		Tile origin = findOrigin(tile, direction);
		return parseLetters(origin, direction);
	}

	private void calculateScore(ArrayList<Tile> tiles, Map<Tile, Integer> subwords) throws InvalidWordException {
		ArrayList<Integer> wordMultipliers = new ArrayList<Integer>();
		StringBuilder sb = new StringBuilder();
		int score = 0;
		int subscore = 0;

		for (Tile tile : tiles) {
			TileType bonus = tileMap.getBonus(tile);
			if (!tileMap.getTile(tile).isProcessed()) {
				tileMap.getTile(tile).setProcessed(true);
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
		System.out.println("Placed word: " + word + ", Points: " + score);
		System.out.println("Total score: " + score + subscore);
		if (this.dictionary.lookup(word)) {
			for (Integer multiplier : wordMultipliers) {
				score *= multiplier;
			}

			this.game.getCurrentPlayer().addPoints(score + subscore);
		} else {
			throw new InvalidWordException(word);
		}
	}

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

	private enum Dir {
		HORIZONTAL, VERTICAL,
	}
}
