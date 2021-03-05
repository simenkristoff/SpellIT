package spellit.core.models;

import java.util.ArrayList;
import java.util.List;

import spellit.core.exceptions.TurnException;
import spellit.core.utils.BoardParser;

public class Board {
	private final int BOARD_WIDTH = 540;
	private final int BOARD_HEIGHT = BOARD_WIDTH;
	private final int COLS = 15;
	private final int ROWS = COLS;
	private final double COL_SIZE = BOARD_WIDTH / COLS;

	private final Game game;
	private final TileMap tileMap;
	private final Dictionary dictionary;
	private final BoardParser parser;
	private ArrayList<Tile> unprocessedTiles = new ArrayList<Tile>();
	private ArrayList<Tile> placedTiles = new ArrayList<Tile>();

	public Board(Game game) {
		this.game = game;
		this.dictionary = new Dictionary();
		this.tileMap = new TileMap(this);
		this.parser = new BoardParser(this.game, this.tileMap, this.dictionary, this.placedTiles);
	}

	public void setTile(int row, int col, Letter letter) {
		tileMap.tiles[row][col].setLetter(letter);
		unprocessedTiles.add(new Tile(row, col, letter));
	}

	public void removeTile(int row, int col) {
		tileMap.tiles[row][col].setLetter(null);
		for (Tile tile : unprocessedTiles) {
			if (tile.getRow() == row && tile.getCol() == col) {
				unprocessedTiles.remove(tile);
				break;
			}
		}
	}

	public int getBoardWidth() {
		return this.BOARD_WIDTH;
	}

	public int getBoardHeight() {
		return this.BOARD_HEIGHT;
	}

	public TileMap getTileMap() {
		return this.tileMap;
	}

	public ArrayList<Tile> getPlacedTiles() {
		return this.placedTiles;
	}

	public void loadTiles(List<Tile> placedTiles) {
		for (Tile tile : placedTiles) {
			this.game.letterCollection.removeLetter(tile.getLetter());
			this.tileMap.setTile(tile);
		}
		this.placedTiles = new ArrayList<Tile>(placedTiles);
	}

	public int getCols() {
		return this.COLS;
	}

	public int getRows() {
		return this.ROWS;
	}

	public Double getColSize() {
		return this.COL_SIZE;
	}

	public TileType getTileType(int row, int col) {
		return tileMap.tiles[row][col].getTileType();
	}

	public void processTurn() throws TurnException {
		parser.parseBoard(unprocessedTiles);
		cleanUp();
	}

	private void cleanUp() {
		unprocessedTiles = new ArrayList<Tile>();
	}

	public static void main(String[] args) {
		Game game = new Game();
		Board board = new Board(game);
	}

	private void loadTestTiles() {
		ArrayList<Tile> testTiles = new ArrayList<Tile>();
		testTiles.add(new Tile(7, 1, new Letter('D', 2)));
		testTiles.add(new Tile(7, 2, new Letter('I', 2)));
		testTiles.add(new Tile(7, 3, new Letter('K', 2)));
		testTiles.add(new Tile(7, 4, new Letter('T', 2)));
		testTiles.add(new Tile(7, 5, new Letter('E', 2)));
		testTiles.add(new Tile(7, 6, new Letter('D', 2)));
		testTiles.add(new Tile(7, 7, new Letter('E', 2)));
		for (Tile tile : testTiles) {
			tile.setProcessed(true);
		}
		Tile B = new Tile(6, 7, new Letter('B', 2));
		Tile O = new Tile(6, 8, new Letter('O', 2));
		Tile N = new Tile(6, 9, new Letter('N', 2));
		Tile U = new Tile(6, 10, new Letter('U', 2));
		Tile S = new Tile(6, 11, new Letter('S', 2));
		testTiles.addAll(List.of(B, O, N, U, S));
		for (Tile tile : testTiles) {
			this.tileMap.setTile(tile);
		}
		unprocessedTiles.addAll(List.of(B, O, N, U, S));
		try {
			processTurn();
		} catch (TurnException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
