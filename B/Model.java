import java.io.IOException;

interface Model extends ReadOnlyBoardViewModel {

  /**
   * @return whether the game is over
   */
  public boolean gameOver();

  /**
   * Move a piece on the game board.
   */
  public void move(int what, int where) throws IOException;

  /**
   *
   * @return
   */
  public Player getWinner();

  /**
   * @return the number of
   */
  Integer setDisplayPieces();

  /**
   * Sets the display to show the possible moves for a selected piece.
   *
   * @param what the index of the checker to be moved
   * @return the number of current valid moves
   */
  Integer setDisplayMoves(int what);

  /**
   * Sets game display to normal state.
   */
  void setDisplayNormal();

  /**
   * @return the {@code Player} whose turn it is
   */
  public Player getTurn();

  public void flip();
}