import java.util.ArrayList;
import java.util.Iterator;

public final class StrictCoinGameModel implements CoinGameModel {

  public static enum Entity {Coin, Square,}

  Entity[] gameBoard;

  private ArrayList<Integer> players;
  private Integer currentPlayer = 0;
  private Integer turnCount = 0;

  /*
   CLASS INVARIANTS:
   game board must contain only 'O' and '-' characters
   Moves must be made in the left direction
   Moves cannot be made to a position occupied by a coin
   Moves cannot be made past another coin
   Moves cannot be made if the game is over
   in order to make a move the number of players >= 1
   No two players can have the same integer number/ID
      -(example: there cannot be two PLayer 3's)
   No two players can move a coin during the same turn
   Only one player can win
   Only one move can be made each turn
   Players must take turns in the correct order
   players can be added anywhere in the order or at the end
        -cannot be added to position 5 if there are only 3 players.
  */

  /**
   * Constructs a StrictGameModel object with a board string.
   *
   * @param board the game board
   * @throws NullPointerException     if the board is Null
   * @throws IllegalArgumentException if board contains an invalid value (not O or -)
   */
  public StrictCoinGameModel(String board) {
    players = new ArrayList<Integer>();
    final int boardSize = board.length();
    gameBoard = new Entity[boardSize];
    if (board == null) {
      throw new NullPointerException("Board cannot be null");
    }
    for (int counter = 0; counter < boardSize; counter++) {
      if (board.charAt(counter) != 'O'
          && board.charAt(counter) != '-') {
        throw new IllegalArgumentException("The board contains "
                                           + "an invalid character:"
                                           + " position = " + counter);
      } else {
        if (board.charAt(counter) == 'O') {
          gameBoard[counter] = Entity.Coin;
        } else {
          gameBoard[counter] = Entity.Square;
        }
      }
    }
  }

  /**
   * Constructs a StrictGameModel object with a board string and a initial number of players
   *
   * @param board           the game board.
   * @param numberOfPlayers the starting number of players.
   * @throws NullPointerException     if the board is Null
   * @throws IllegalArgumentException if board contains an invalid value (not O or -)
   * @throws IllegalArgumentException if [numberOfPlayers] is negative
   */
  public StrictCoinGameModel(String board, int numberOfPlayers) {
    players = new ArrayList<Integer>();
    addPlayers(numberOfPlayers);
    final int boardSize = board.length();
    gameBoard = new Entity[boardSize];
    if (board == null) {
      throw new NullPointerException("Board cannot be null");
    }
    for (int counter = 0; counter < boardSize; counter++) {
      if (board.charAt(counter) != 'O'
          && board.charAt(counter) != '-') {
        throw new IllegalArgumentException("The board contains "
                                           + "an invalid character:"
                                           + " position = " + counter);
      } else {
        if (board.charAt(counter) == 'O') {
          gameBoard[counter] = Entity.Coin;
        } else {
          gameBoard[counter] = Entity.Square;
        }
      }
    }
  }

  @Override
  public int coinCount() {
    final int boardSize = this.boardSize();
    int coinCount = 0;
    for (int indexCounter = 0; indexCounter < boardSize; indexCounter++) {
      if (gameBoard[indexCounter] == Entity.Coin) {
        coinCount++;
      }
    }
    return coinCount;
  }

  @Override
  public int boardSize() {
    return this.gameBoard.length;
  }

  @Override
  public int getCoinPosition(int coinIndex) {
    if (coinIndex >= coinCount()) {
      throw new IllegalArgumentException();
    }

    final int boardSize = this.boardSize();
    int coinCount = 0;

    for (int indexCounter = 0; indexCounter < boardSize; indexCounter++) {
      if (gameBoard[indexCounter] == Entity.Coin) {
        if (coinIndex == coinCount) {
          return indexCounter;
        } else {
          coinCount++;
        }
      }
    }
    throw new IllegalArgumentException();
  }

  /**
   * Checks whether {@code newPosition} is occupied.
   *
   * @param coinPosition the position that is being checked
   * @return true or false, whether or not the {@code coinPosition} is occupied
   */
  protected boolean isOccupied(int coinPosition) {
    return (gameBoard[coinPosition] == Entity.Coin);
  }

  /**
   * Swaps coin character in {@code currentPosition} with character in {@code newPosition}.
   *
   * @param currentPosition the current position of the coin to be moved
   * @param newPosition     the new position the coin is being moved to
   */
  protected void swap(int currentPosition, int newPosition) {
    Entity temp = gameBoard[currentPosition];
    gameBoard[currentPosition] = gameBoard[newPosition];
    gameBoard[newPosition] = temp;
  }

  @Override
  public boolean isGameOver() {
    final int coinCount = coinCount();
    for (int indexCounter = 0; indexCounter < coinCount; indexCounter++) {
      if (gameBoard[indexCounter] != Entity.Coin) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks to see if the move being made is to the left.
   *
   * @param coinIndex   the coin number from the left
   * @param newPosition the position the coin is being moved to
   * @return whether the coin is moving to the left or not.
   */
  protected boolean isLeftMove(int coinIndex, int newPosition) {
    return newPosition < getCoinPosition(coinIndex);
  }

  @Override
  public void move(int coinIndex, int newPosition) {
    int currentPosition = getCoinPosition(coinIndex);
    if (isOccupied(newPosition) || !isPathClear(coinIndex, newPosition)
        || !isLeftMove(coinIndex, newPosition)) {
      throw new IllegalMoveException();
    } else if (isGameOver() || players.size() <= 0) {
      throw new IllegalStateException();
    } else {
      swap(currentPosition, newPosition);
      if (!isGameOver()) {
        iterateTurn();
      }
    }
  }

  @Override
  public String toString() {
    String board = "";
    final int boardSize = boardSize();
    for (int counter = 0; counter < boardSize; counter++) {
      if (gameBoard[counter] == Entity.Coin) {
        board = board + "O";
      } else {
        board = board + "-";
      }
    }
    return board;
  }

  /**
   * Checks if the path is clear between {@code coinIndex} and {@code newPosition}.
   *
   * @param coinIndex   which coin to move
   * @param newPosition where to move it to
   * @return true of false if it is clear or not
   */
  private boolean isPathClear(int coinIndex, int newPosition) {
    int coinPosition = this.getCoinPosition(coinIndex);

    for (int indexCounter = coinPosition - 1; indexCounter > newPosition;
         indexCounter--) {
      if (isOccupied(indexCounter)) {
        return false;
      }
    }
    return true;
  }


  /**
   * sets the turn to the next player
   */
  private void iterateTurn() {
    currentPlayer = (currentPlayer + 1) % players.size();
    turnCount++;
  }

  @Override
  public void addPlayer() {
    players.add(players.size());
  }

  @Override
  public void addPlayer(int position) {
    if (position > playerCount()) {
      throw new IllegalArgumentException();
    } else {
      players.add(position, players.size());
      // if a new player is added before
      if (position <= currentPlayer)
      {
        currentPlayer++;
      }
    }
  }

  /**
   * returns a string representing the order of play with "|" around whos
   *  turn it is.
   * @return the order of play
   */
  public String playerOrder() {
    String playerList = "";
    Iterator<Integer> itr = players.iterator();
    int player;
    while(itr.hasNext()) {
      player = itr.next();
      if(player == whosTurn()) {
        playerList = playerList + "|" + player + "|";
      }
      else {
        playerList = playerList + player;
      }
      if(itr.hasNext()) {
        playerList = playerList + " ";
      }
    }
    return playerList;
  }

  /**
   * adds the number of players specified
   *
   * @param numberOfPlayers the number of players to be added
   */
  public void addPlayers(int numberOfPlayers) {
    for (int counter = 0; counter < numberOfPlayers; counter++) {
      addPlayer();
    }
  }

  @Override
  public int getWinner() {
    if (!isGameOver()) {
      throw new IllegalStateException();
    } else {
      return whosTurn();
    }
  }

  @Override
  public int whosTurn() {
    if (playerCount() <= 0) {
      throw new IllegalStateException();
    } else {
      return players.get(currentPlayer);
    }
  }

  @Override
  public int playerCount() {
    return players.size();
  }

  @Override
  public int turnCount() {
    if (playerCount() <= 0) {
      throw new IllegalStateException();
    } else {
      return turnCount;
    }
  }
}