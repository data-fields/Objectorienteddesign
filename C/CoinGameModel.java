/**
 * An interface for playing a coin game. The rules of a particular coin game will be implemented by
 * classes that implement this interface.
 */
public interface CoinGameModel {

  /**
   * Gets the size of the board (the number of squares)
   *
   * @return the board size
   */
  int boardSize();

  /**
   * Gets the number of coins.
   *
   * @return the number of coins
   */
  int coinCount();

  /**
   * Gets the (zero-based) position of coin number {@code coinIndex}.
   *
   * @param coinIndex which coin to look up
   * @return the coin's position
   * @throws IllegalArgumentException if there is no coin with the requested index
   */
  int getCoinPosition(int coinIndex);

  /**
   * Returns whether the current game is over. The game is over if there are no valid moves.
   *
   * @return whether the game is over
   */
  boolean isGameOver();

  /**
   * Moves coin {@code coinIndex} to position {@code newPosition}, if the requested move is legal.
   * Throws {@code IllegalMoveException} if the requested move is illegal, which can happen in
   * several ways:
   *
   * <ul> <li>There is no coin with the requested index.</li> <li>The new position is occupied by
   * another coin.</li> <li>There is some other reason the move is illegal, as specified by the
   * concrete game.</li> </ul>
   *
   * @param coinIndex   which coin to move (numbered from the left)
   * @param newPosition where to move it to
   * @throws IllegalMoveException the move is illegal
   */
  void move(int coinIndex, int newPosition);

  /**
   * Returns which players turn it is
   *
   * @return which players turn it is
   */
  public int whosTurn();

  /**
   * <p><strong>PRECONDITION:</strong> the game is over and has a winner returns the winner
   *
   * @return returns the winner
   * @throws IllegalStateException is the game has not ended
   */
  public int getWinner();

  /**
   * Adds a player to the game
   */
  public void addPlayer();

  /**
   * Adds a player to the specified position
   *
   * @param position the position to add the player to.
   */
  public void addPlayer(int position);

  /**
   * returns the player count
   *
   * @return the player count
   */
  public int playerCount();

  /**
   * returns the number of turns that have been played
   *
   * @return the number of turns that have been played.
   */
  public int turnCount();


  /**
   * The exception thrown by {@code move} when the requested move is illegal.
   *
   * <p>(Implementation Note: Implementing this interface doesn't require "implementing" the {@code
   * IllegalMoveException} class. Nesting a class within an interface is a way to strongly associate
   * that class with the interface, which makes sense here because the exception is intended to be
   * used specifically by implementations and clients of this interface.)
   */
  static class IllegalMoveException extends IllegalArgumentException {

    /**
     * Constructs a illegal move exception with no description.
     */
    public IllegalMoveException() {
      super();
    }

    /**
     * Constructs a illegal move exception with the given description.
     *
     * @param msg the description
     */
    public IllegalMoveException(String msg) {
      super(msg);
    }
  }
}
