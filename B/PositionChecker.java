import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;




class Checker implements Comparable<Checker> {

  private static List<Checker> checkers;
  private static Map<Integer, Checker> board;

  static {
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

  private Piece piece;
  private Position position;

  /**
   * Constructs a {@code Checker} object.
   */
  Checker(Piece piece, Position position) {
    this.piece = piece;
    this.position = position;
  }

  /**
   * returns the piece associated with the checker
   *
   * @return the piece associated with the checker
   */
  Piece getPiece() {
    return this.piece;
  }

  /**
   * returns the position associated with the checker
   *
   * @return the position associated with the checker
   */
  Position getPosition() {
    return this.position;
  }

  /**
   * returns true if the checker has a move it can make
   *
   * @return true if the checker has a move it can make
   */
  private boolean hasMove() {
    Iterator<Position> iterator;
    if (piece.isCrowned()) {
      iterator = position.adjacentPositions().iterator();
    } else if (piece.player() == Player.First) {
      iterator =
          position.adjacentPositions().filter(otherPosition -> otherPosition.isAbove(position))
              .iterator();
    } else {
      iterator =
          position.adjacentPositions().filter(otherPosition -> otherPosition.isBelow(position))
              .iterator();
    }
    while (iterator.hasNext()) {
      Position to = iterator.next();
      if (!board.containsKey(to.index())) {
        return true;
      }
    }
    return false;
  }

  /**
   * returns all the moves for the checker
   *
   * @return all the moves for the checker
   */



  private List<Position> moves() {
    List<Position> moves = new ArrayList<>();
    Iterator<Position> iterator;
    if (piece.isCrowned()) {
      iterator = position.adjacentPositions().iterator();
    } else if (piece.player() == Player.First) {
      iterator =
          position.adjacentPositions().filter(otherPosition -> otherPosition.isAbove(position))
              .iterator();
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