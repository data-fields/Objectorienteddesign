import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Board implements Model {

  String display[][];
  boolean flipped;
  static final int NUM_CHECKERS = 12;
  static final int NUM_ROWS = 8;
  static final int NUM_COLUMNS = 8;
  private Player turn;

  public Board() {
    display = new String[NUM_ROWS][NUM_COLUMNS];
    turn = Player.First;
    flipped = false;
  }

  @Override
  public boolean gameOver() {
    if (Checker.getPlayersCheckers(Player.First).size() == 0 || !Checker.hasMove(Player.First)
        || Checker.getPlayersCheckers(Player.Second).size() == 0 || !Checker
        .hasMove(Player.Second)) {
      setDisplayNormal();
      return true;
    }
    return false;
  }

  /**
   * Flips the representation of the board from one player to the next.
   */
  void flipBoard() {
    String[][] newDisplay = new String[NUM_ROWS][NUM_COLUMNS];
    for (int rowCounter = 0; rowCounter < NUM_ROWS; rowCounter++) {
      for (int columnCounter = 0; columnCounter < NUM_COLUMNS; columnCounter++) {
        newDisplay[rowCounter][columnCounter] =
            display[NUM_ROWS - rowCounter - 1][NUM_COLUMNS - columnCounter - 1];
      }
    }
    display = newDisplay;
  }

  @Override
  public void setDisplayNormal() {
    clearDisplay();
    Iterator<Checker> iterator = Checker.getCheckers().iterator();
    while (iterator.hasNext()) {
      Checker checker = iterator.next();
      setBoard(checker, String.valueOf(checker.getPiece().toChar()));
    }
  }

  @Override
  public Player getTurn() {
    return turn;
  }

  /**
   * @return the list of checkers that can be moved
   */
  List<Checker> getMovableCheckers() {
    List<Checker> movableCheckers = Checker.getMovableCheckers(turn);
    if (flipped) {
      Collections.reverse(movableCheckers);
    }
    return movableCheckers;
  }

  /**
   * Gets the possible positions to move a certain checker to.
   *
   * @return the list of possible Positions to move the checker to
   */
  List<Position> getMoves(int what) {
    List<Checker> movableCheckers = getMovableCheckers();
    Checker checker = movableCheckers.get(what);
    List<Position> moves = checker.getMoves();
    if (flipped) {
      Collections.reverse(moves);
    }
    return moves;
  }

  @Override
  public Integer setDisplayMoves(int what) {
    Checker checker = getMovableCheckers().get(what);
    List<Position> moves = getMoves(what);
    clearDisplay();
    setDisplayNormal();
    Iterator<Position> positions = moves.iterator();
    setBoard(checker, "<" + String.valueOf(checker.getPiece().toChar()) + ">");
    while (positions.hasNext()) {
      Position next = positions.next();
      int row = next.row();
      int column = next.column();
      this.display[row][column] =
          "[" + (moves.indexOf(Position.fromRowColumn(row, column)) + 1) + "]";
    }
    if (flipped) {
      flipBoard();
    }
    return moves.size();
  }

  /**
   * Clears display
   */
  private void clearDisplay() {
    for (int row = 0; row < NUM_ROWS; row++) {
      for (int column = 0; column < NUM_COLUMNS; column++) {
        display[row][column] = null;
      }
    }
  }

  /**
   * Sets the representation of a certain element in the board.
   *
   * @param checker container for a piece and its position
   */
  private void setBoard(Checker checker, String index) {
    int row = checker.getPosition().row();
    int column = checker.getPosition().column();
    display[row][column] = index;
  }

  @Override
  public Integer setDisplayPieces() {
    List<Checker> checkers = getMovableCheckers();
    clearDisplay();
    Iterator<Checker> iterator = Checker.getCheckers().iterator();
    while (iterator.hasNext()) {
      Checker checker = iterator.next();
      if (checkers.contains(checker)) {
        if (checker.getPiece().isCrowned()) {
          setBoard(checker, "[[" + (checkers.indexOf(checker) + 1) + "]]");
        } else {
          setBoard(checker, "[" + (checkers.indexOf(checker) + 1) + "]");
        }

      } else {
        setBoard(checker, String.valueOf(checker.getPiece().toChar()));
      }
    }
    if (flipped) {
      flipBoard();
    }
    return checkers.size();
  }

  public void flip() {
    flipped = turn == Player.Second;
  }

  @Override
  public void move(int what, int where) throws IOException {
    List<Checker> movableCheckers = getMovableCheckers();
    Checker checker = movableCheckers.get(what);
    List<Position> positions = getMoves(what);
    Position to = positions.get(where);
    boolean madeJump = false;
    if (checker.isJump(to)) {
      checker.jump(to);
      madeJump = true;
    } else {
      checker.move(to);
    }
    if (!madeJump || !checker.hasJump()) {
      turn = turn.other();
    }
    checker.makeKing();
  }

  @Override
  public Player getWinner() {
    if (!gameOver()) {
      throw new IllegalStateException();
    } else {
      if (!(Checker.getPlayersCheckers(Player.First).size() > 0 && Checker
          .hasMove(Player.First))) {
        return Player.Second;
      } else {
        return Player.First;
      }
    }
  }

  public List<Checker> all() {
    return Checker.all();
  }

  @Override
  public String get(int row, int column, int width) {
    if (display[row][column] == null) {
      return "     ";
    } else {
      return display[row][column];
    }
  }

  @Override
  public boolean isValidPosition(int row, int column) {
    return row >= 0 && row < NUM_ROWS
           && column >= 0 && column < NUM_COLUMNS
           && (row + column) % 2 == 1;
  }

  @Override
  public Iterator<Integer> rows() {
    List<Integer> l = new ArrayList<>();
    for (int counter = 0; counter < NUM_ROWS; counter++) {
      l.add(counter);
    }
    return l.iterator();
  }

  @Override
  public Iterator<Integer> columns() {
    List<Integer> l = new ArrayList<>();
    for (int counter = 0; counter < NUM_COLUMNS; counter++) {
      l.add(counter);
    }
    return l.iterator();
  }
}