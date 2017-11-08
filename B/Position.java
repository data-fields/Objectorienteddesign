import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Represents game board positions. A checkers board consists of 64 squares, 32 of which are
 * positions used in the game.
 *
 * <p>There are two different ways to refer to positions that this class relates:
 *
 * <ul> <li> As a pair of coordinates, the row and the column. We use integers from 0 to 7, with the
 * 0 row being the farthest from the first player and the 0 column being on the first player's
 * left.
 *
 * An advantage of this representation is that makes it easy to see how positions are related in
 * board-space. A disadvantage is that even if both a row and column are in bounds, they may not
 * represent a valid position as a pair, since only half the squares are used.
 *
 * <li>As an index, based on numbering the valid positions in a fixed order. Traditionally, checkers
 * uses integers from 1 to 32 to refer to valid positions, counting left-to-right along each row,
 * and then down to the next row, starting in the first player's upper-left and finishing in their
 * lower-right. This class uses the same order, but with zero-based indexing. That is, we count
 * along row 0, starting in column 0 and moving toward column 7, then row 1 from column 0 to column
 * 7, and so on.
 *
 * An advantage of this representation is that it's easy to check for validity (is it in the range?)
 * and as an index into an array or list. A disadvantage is that it's less clearly related to the
 * geometry of the game. </ul>
 *
 * <p>Positions are interned (generalization of the singleton pattern), and only one object for each
 * valid position will be created. This means that positions can always be compared by physical
 * equality.
 */



public final class Position implements Comparable<Position> {

  /**
   * The board is a {@code BOARD_SIZE}-by-{@code BOARD_SIZE} square. This constant should likely be
   * defined somewhere else in your code and only referenced from here...
   */
  public static final int BOARD_SIZE = 8;

  /**
   * The number of valid positions on the board, given the board size.
   */
  public static final int N_POSITIONS = BOARD_SIZE * BOARD_SIZE / 2;

  /**
   * The row, numbered from the top from first player-perspective.
   */
  private final int row;

  /**
   * The column, numbered from the left from first player-perspective.
   */
  private final int column;

  /**
   * We will create one instance for each board position up front and store them in this array.
   * After this initialization, no more positions will ever be created, since we will have all 32
   * that we need.
   */
  private static final List<Position> CACHE;

  /*
   * This is a static initialization block, which is run to initialize the
   * class, before any instances are constructed. Here we initialize the
   * position cache.
   */
  static {
    List<Position> newCache = new ArrayList<>(N_POSITIONS);

    for (int row = 0; row < BOARD_SIZE; ++row) {
      for (int column = 0; column < BOARD_SIZE; ++column) {
        if (isValidPosition(row, column)) {
          newCache.add(new Position(row, column));
        }
      }
    }

    CACHE = Collections.unmodifiableList(newCache);
  }

  /**
   * Returns a position object for the given row and column, if the row and column represent a valid
   * position. This static factory method may not allocate a new object every time it's called.
   *
   * @param row    the row
   * @param column the column
   * @return the position
   * @throws IndexOutOfBoundsException if row and column do not refer to a valid position.
   */
  public static Position fromRowColumn(int row, int column) {
    return fromIndex(toIndex(row, column));
  }

  /**
   * Returns a position object for the given index. Indices start at 0 in the first player's upper
   * left (actually, row 0 column 1) and proceed left-to-right along each row before continuing in
   * the next row. This static factory method may not allocate a new object every time it's called.
   *
   * @param index the row
   * @return the position
   * @throws IndexOutOfBoundsException if row and column do not refer to a valid position.
   */
  public static Position fromIndex(int index) {
    return CACHE.get(index);
  }

  /**
   * Determines whether the given row and column together represent a valid position.
   *
   * @param row    the row
   * @param column the column
   * @return whether the position is valid
   */
  public static boolean isValidPosition(int row, int column) {
    return row >= 0 && row < BOARD_SIZE
           && column >= 0 && column < BOARD_SIZE
           && (row + column) % 2 == 1;
  }

  // Helper for validity checks
  private static void ensureValidPosition(int row, int column) {
    if (!isValidPosition(row, column)) {
      throw new IndexOutOfBoundsException("bad row and column," + row + column);
    }
  }

  /**
   * The row of this position, numbered from top from the first player's perspective. Zero based.
   *
   * @return [0, 7]
   */
  public int row() {
    return row;
  }

  /**
   * The column of this position, numbered from left from the first player's perspective. Zero
   * based.
   *
   * @return [0, 7]
   */
  public int column() {
    return column;
  }

  /**
   * The index of this position, numbered from left to right and from top to bottom, from the first
   * player's perspective. Zero based.
   *
   * @return [0, 31]
   */
  public int index() {
    return toIndexUnchecked(row, column);
  }

  /**
   * Converts a row and column to their index representation.
   *
   * @param row    the row
   * @param column the column
   * @return the corresponding index
   * @throws IndexOutOfBoundsException if row and column do not refer to a valid position
   */
  public static int toIndex(int row, int column) {
    ensureValidPosition(row, column);
    return toIndexUnchecked(row, column);
  }

  // Helper for doing the arithmetic once we know a row-column pair is good.
  private static int toIndexUnchecked(int row, int column) {
    return (row * BOARD_SIZE + column) / 2;
  }

  /**
   * Determines whether the row of this position appears above the row of the other position from
   * the perspective of the first player.
   *
   * @param other the other position
   * @return {@code this.row() < other.row()}
   */
  public boolean isAbove(Position other) {
    return this.row < other.row;
  }

  /**
   * Determines whether the row of this position appears below the row of the other position from
   * the perspective of the first player.
   *
   * @param other the other position
   * @return {@code this.row() > other.row()}
   */
  public boolean isBelow(Position other) {
    return this.row > other.row;
  }

  /**
   * Determines whether the other position is a single non-jump move away from this position.
   *
   * @param other the other position
   * @return whether the positions are adjacent
   */
  public boolean isAdjacentTo(Position other) {
    return Math.abs(this.row - other.row) == 1
           && Math.abs(this.column - other.column) == 1;
  }

  /**
   * Determines whether the other position is a single jump move away from this position.
   *
   * @param other the other position
   * @return whether the positions are one jump apart
   */
  public boolean isJumpAdjacentTo(Position other) {
    return Math.abs(this.row - other.row) == 2
           && Math.abs(this.column - other.column) == 2;
  }

  /**
   * Returns the position that would be jumped over from this position to another position.
   *
   * @param other the other position (must be jump-adjacent to this)
   * @return the position in between
   * @throws IllegalArgumentException f {@code !this.isJumpAdjacentTo(other)}
   */
  public Position findJumpedPosition(Position other) {
    if (!isJumpAdjacentTo(other)) {
      throw new IllegalArgumentException("not jump-adjacent");
    }

    return fromRowColumn((this.row + other.row) / 2,
                         (this.column + other.column) / 2);
  }

  /**
   * Returns a stream of the positions adjacent to this position.
   *
   * @return the adjacent positions
   */
  public Stream<Position> adjacentPositions() {
    return positionsAtDistance(1);
  }

  /**
   * Returns a stream of the positions jump-adjacent to this position.
   *
   * @return the jump-adjacent positions
   */
  public Stream<Position> jumpAdjacentPositions() {
    return positionsAtDistance(2);
  }

  private Stream<Position> positionsAtDistance(int distance) {
    Stream.Builder<Position> builder = Stream.builder();
    addIfValid(builder, this.row - distance, this.column - distance);
    addIfValid(builder, this.row - distance, this.column + distance);
    addIfValid(builder, this.row + distance, this.column - distance);
    addIfValid(builder, this.row + distance, this.column + distance);
    return builder.build();
  }

  private static void addIfValid(Consumer<Position> acc, int row, int column) {
    if (isValidPosition(row, column)) {
      acc.accept(fromRowColumn(row, column));
    }
  }

  /*
   * equals(Object) and hashCode() are not overridden because the interning
   * means that physical equality and extensional equality are the same.
   */

  /**
   * Compares positions by their indices.
   *
   * @param other the other position to compare to
   * @return {@code Integer.compare(this.index(), other.index())}
   */
  @Override
  public int compareTo(Position other) {
    return Integer.compare(this.index(), other.index());
  }

  /**
   * Returns an {@code Iterable<Position>} whose {@link Iterable#iterator()} iterates over all the
   * positions in index order.
   *
   * @return an iterable for all positions
   */
  public static Iterable<Position> all() {
    // The iterator of the List<Position> is the iterator we want:
    return CACHE;
  }

  // Only called to create the 32 interned positions, and never again.
  private Position(int row, int column) {
    assert CACHE == null : "no new positions once initialized";

    this.row = row;
    this.column = column;
  }
}
    } else {
      iterator =
          position.adjacentPositions().filter(otherPosition -> otherPosition.isBelow(position))
              .iterator();
    }
    while (iterator.hasNext()) {
      Position to = iterator.next();
      if (!board.containsKey(to.index())) {
        moves.add(to);
      }
    }
    return moves.stream().collect(Collectors.toList());
  }

  /**
   * returns true if the checker has a jump
   *
   * @return true if the checker has a jump
   */



  public boolean hasJump() {
    Iterator<Position> iterator;
    if (piece.isCrowned()) {
      iterator = position.jumpAdjacentPositions().iterator();
    } else if (piece.player() == Player.First) {
      iterator =
          position.jumpAdjacentPositions()
              .filter(otherPosition -> otherPosition.isAbove(position)).iterator();
    } else {
      iterator =
          position.jumpAdjacentPositions()
              .filter(otherPosition -> otherPosition.isBelow(position)).iterator();
    }
    while (iterator.hasNext()) {
      Position to = iterator.next();
      if (board.containsKey(position.findJumpedPosition(to).index()) && !board
          .containsKey(to.index())) {
        if (board.get(position.findJumpedPosition(to).index()).getPiece().player() == this
            .getPiece().player().other()) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * returns the junps the checker can make
   *
   * @return the jumps the checker can make
   */
  private List<Position> jumps() {
    List<Position> jumps = new ArrayList<>();
    Iterator<Position> iterator;
    if (piece.isCrowned()) {
      iterator = position.jumpAdjacentPositions().iterator();
    } else if (piece.player() == Player.First) {
      iterator =
          position.jumpAdjacentPositions()
              .filter(otherPosition -> otherPosition.isAbove(position)).iterator();
    } else {
      iterator =
          position.jumpAdjacentPositions()
              .filter(otherPosition -> otherPosition.isBelow(position)).iterator();
    }
    while (iterator.hasNext()) {
      Position to = iterator.next();
      if (board.containsKey(position.findJumpedPosition(to).index()) && !board
          .containsKey(to.index())) {
        if (board.get(position.findJumpedPosition(to).index()).getPiece().player() == this
            .getPiece().player().other()) {
          jumps.add(to);
        }
      }
    }
    return jumps.stream().collect(Collectors.toList());
  }

  /**
   * return true if the given player has a move
   *
   * @param player the given player
   * @return true if the given player has a move
   */
  static boolean hasMove(Player player) {
    Iterator<Checker>
        iterator =
        checkers.stream().filter(checker -> checker.getPiece().player() == player).iterator();
    while (iterator.hasNext()) {
      Checker next = iterator.next();
      if (next.hasMove() || next.hasJump()) {
        return true;
      }
    }
    return false;
  }

  /**
   * return true if the player has a jump
   *
   * @param player the given player
   * @return true if the player has a jump
   */
  static boolean hasJump(Player player) {
    Iterator<Checker>
        iterator =
        checkers.stream().filter(checker -> checker.getPiece().player() == player).iterator();
    while (iterator.hasNext()) {
      Checker next = iterator.next();
      if (next.hasJump()) {
        return true;
      }
    }
    return false;
  }

 

 /**
   * returns the movable checkers for the given player
   *
   * @param player the given player
   * @return the movable checkers for the given player
   */



  static List<Checker> getMovableCheckers(Player player) {
    if (hasJump(player)) {
      return checkers.stream().filter(checker -> checker.getPiece().player() == player)
          .filter(checker -> checker.hasJump()).sorted().collect(
              Collectors.toList());
    } else {
      return checkers.stream().filter(checker -> checker.getPiece().player() == player)
          .filter(checker -> checker.hasMove()).sorted().collect(
              Collectors.toList());
    }
  }

  /**
   * gets all the checkers for the given player
   *
   * @param player the given player
   * @return all the checkers for the given player
   */
  static List<Checker> getPlayersCheckers(Player player) {
    return checkers.stream().filter(checker -> checker.getPiece().player() == player).collect(
        Collectors.toList());
  }

  /**
   * returns all the checkers
   *
   * @return all the checkers
   */
  static Stream<Checker> getCheckers() {
    return checkers.stream();
  }

  /**
   * returns all the moves for a checker
   *
   * @return all the moves for the checker
   */
  List<Position> getMoves() {
    if (this.hasJump()) {
      return this.jumps();
    } else if (this.hasMove()) {
      return this.moves();
    } else {
      return null;
    }
  }

  /**
   * makes the checker a king if it has reached the other side of the board
   */
  public void makeKing() {
    if (piece.player() == Player.First && position.row() == 0) {
      piece = Piece.CrownedFirst;
    } else if (piece.player() == Player.Second && position.row() == Board.NUM_ROWS - 1) {
      piece = Piece.CrownedSecond;
    }
  }

  /**
   * moves the checker
   *
   * @param newPosition the new position for the checker
   */
  public void move(Position newPosition) {
    board.remove(position.index());
    board.put(newPosition.index(), this);
    this.position = newPosition;
  }

  public static List<Checker> all() {
    return getCheckers().collect(Collectors.toList());
  }

  /**
   * jumps the checker
   *
   * @param newPosition the new position for the checker
   */
  public void jump(Position newPosition) {
    checkers.remove(board.get(position.findJumpedPosition(newPosition).index()));
    board.remove(position.index());
    board.remove(position.findJumpedPosition(newPosition).index());
    board.put(newPosition.index(), this);
    this.position = newPosition;
  }

  /**
   * returns true if the position is a jump for the checker
   *
   * @param position the position to be determined
   * @return true if the psoition is a jump
   */
  public boolean isJump(Position position) {
    Iterator<Position> jumps = this.getPosition().jumpAdjacentPositions().iterator();
    while (jumps.hasNext()) {
      if (position.compareTo(jumps.next()) == 0) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int compareTo(Checker o) {
    return Integer.compare(getPosition().index(), o.getPosition().index());
  }

  public static void reset() {
    checkers = new ArrayList<>();
    board = new Hashtable<>();
    Iterator<Position> iterator = Position.all().iterator();
    while (iterator.hasNext()) {
      Position next = iterator.next();
      if (next.index() < Board.NUM_CHECKERS) {
        Checker newChecker = new Checker(Piece.NormalSecond, next);
        checkers.add(newChecker);
        board.put(next.index(), newChecker);
      } else if (next.index() >= Position.N_POSITIONS - Board.NUM_CHECKERS) {
        Checker newChecker = new Checker(Piece.NormalFirst, next);
        checkers.add(newChecker);
        board.put(next.index(), newChecker);
      }
    }
  }
}