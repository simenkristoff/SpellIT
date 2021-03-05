package spellit.core.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import spellit.core.events.NextTurnListener;

@JsonIgnoreProperties(value = { "board", "currentPlayer", "letterCollection" })
public class Game {

	public Board board;
	public final LetterCollection letterCollection;
	private Player player1, player2, currentPlayer;
	private int turnCount;

	private List<NextTurnListener> nextTurnListeners = new ArrayList<NextTurnListener>();

	public Game() {
		this.board = new Board(this);
		this.letterCollection = new LetterCollection();
		this.player1 = new Player(this, "Player 1");
		this.player2 = new Player(this, "Player 2");
		this.currentPlayer = player1;
		this.turnCount = 0;
	}

	public void addListener(NextTurnListener listener) {
		nextTurnListeners.add(listener);
	}

	public void addListener(NextTurnListener... listeners) {
		for (NextTurnListener listener : listeners) {
			nextTurnListeners.add(listener);
		}
	}

	public void removeListener(NextTurnListener listener) {
		nextTurnListeners.remove(listener);
	}

	public int getTurnCount() {
		return this.turnCount;
	}

	public void setTurnCount(int turnCount) {
		this.turnCount = turnCount;
	}

	public List<Player> getPlayers() {
		return List.of(player1, player2);
	}

	public void setPlayers(List<Player> players) {
		loadPlayer(player1, players.get(0));
		loadPlayer(player2, players.get(1));
		setCurrentPlayer();
	}

	private void loadPlayer(Player gamePlayer, Player json) {
		gamePlayer.setName(json.getName());
		gamePlayer.setPoints(json.getPoints());
		gamePlayer.setLetters(json.getLetters());
	}

	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}

	private void setCurrentPlayer() {
		switch (turnCount % 2) {
		case 1:
			currentPlayer = player2;
			break;
		default:
			currentPlayer = player1;
			break;
		}
	}

	public ArrayList<Tile> getPlacedTiles() {
		return this.board.getPlacedTiles();
	}

	public void setPlacedTiles(List<Tile> placedTiles) {
		this.board.loadTiles(placedTiles);
	}

	public void initiateNextTurn() {
		turnCount++;
		setCurrentPlayer();
		for (NextTurnListener listeners : nextTurnListeners) {
			listeners.onNextTurn();
		}
	}

	@Override
	public String toString() {
		return "Game [board=" + board + ", letterCollection=" + letterCollection + ", player1=" + player1 + ", player2="
				+ player2 + ", currentPlayer=" + currentPlayer + ", turnCount=" + turnCount + ", nextTurnListeners="
				+ nextTurnListeners + "]";
	}

}
