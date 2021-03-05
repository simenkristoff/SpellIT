package spellit.core.models;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

public class TileMap {

	private Board board;
	public Tile[][] tiles;

	public TileMap(Board board) {
		this.board = board;
		tiles = new Tile[board.getCols()][board.getRows()];
		try (InputStream in = this.getClass().getResourceAsStream("tilemap_standard.txt")) {

			Scanner sc = new Scanner(in);
			int row = 0;
			while (sc.hasNextLine()) {
				String[] list = sc.nextLine().split("\\|");
				int col = 0;
				for (String tile : list) {
					switch (tile.toUpperCase()) {
					case "TW":
						tiles[row][col] = new Tile(row, col, TileType.TW);
						break;
					case "DW":
						tiles[row][col] = new Tile(row, col, TileType.DW);
						break;
					case "TL":
						tiles[row][col] = new Tile(row, col, TileType.TL);
						break;
					case "DL":
						tiles[row][col] = new Tile(row, col, TileType.DL);
						break;
					case "S":
						tiles[row][col] = new Tile(row, col, TileType.STAR);
						break;
					default:
						tiles[row][col] = new Tile(row, col, TileType.DEFAULT);
						break;
					}
					col++;
				}
				row++;
			}
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Tile getTile(int row, int col) {
		return tiles[row][col];
	}

	public Tile getTile(Tile tile) {
		return tiles[tile.getRow()][tile.getCol()];
	}

	public void setTile(Tile tile) {
		tiles[tile.getRow()][tile.getCol()].setLetter(tile.getLetter());
		tiles[tile.getRow()][tile.getCol()].setProcessed(tile.isProcessed());
	}

	public TileType getBonus(Tile tile) {
		return tiles[tile.getRow()][tile.getCol()].getTileType();
	}

	public boolean hasLeft(Tile tile) {
		return tile.getCol() == 0 ? false : tiles[tile.getRow()][tile.getCol() - 1].hasLetter();
	}

	public boolean hasRight(Tile tile) {
		return tile.getCol() == board.getCols() - 1 ? false : tiles[tile.getRow()][tile.getCol() + 1].hasLetter();
	}

	public boolean hasUp(Tile tile) {
		return tile.getRow() == 0 ? false : tiles[tile.getRow() - 1][tile.getCol()].hasLetter();
	}

	public boolean hasDown(Tile tile) {
		return tile.getRow() == board.getRows() - 1 ? false : tiles[tile.getRow() + 1][tile.getCol()].hasLetter();
	}

	public Tile getLeft(Tile tile) {
		return tiles[tile.getRow()][tile.getCol() - 1];
	}

	public Tile getRight(Tile tile) {
		return tiles[tile.getRow()][tile.getCol() + 1];
	}

	public Tile getUp(Tile tile) {
		return tiles[tile.getRow() - 1][tile.getCol()];
	}

	public Tile getDown(Tile tile) {
		return tiles[tile.getRow() + 1][tile.getCol()];
	}

	@Override
	public String toString() {
		return "TileMap [tiles=" + Arrays.toString(tiles) + "]";
	}

}
