import java.util.Objects;

/**
 * Represents a draughts piece. A piece may belong to either player and may or may not be "crowned."
 * In the traditional game, a king is represented by a stack of two ordinary pieces, but for our
 * purposes that is better considered a single piece of a different sort.
 */




public enum Piece {
  /**
   * First player, uncrowned
   */
  NormalFirst('+', Player.First, false) {
    public Piece crowned() {
      return CrownedFirst;
    }
  },

  /**
   * First player, crowned.
   */
  CrownedFirst('#', Player.First, true),

  /**
   * Second player, uncrowned.
   */
  NormalSecond('o', Player.Second, false) {
    public Piece crowned() {
      return CrownedSecond;
    }
  },

  /**
   * Second player, crowned.
   */
  CrownedSecond('@', Player.Second, true);

  /**
   * Determines whether this piece is allowed to move in the main/forward direction of the given
   * player.
   *
   * @param which the player (non-null)
   * @return whether this piece can move in which's direction
   */
  public boolean canMoveDirectionOf(Player which) {
    Objects.requireNonNull(which);

    return isCrowned || player() == which;
  }

  /**
   * Returns the player whose piece this is.
   *
   * @return the player
   */
  public Player player() {
    return player;
  }

  private final Player player;

  /**
   * Returns the piece that this piece becomes when crowned. Idempotent.
   *
   * @return this piece, crowned
   */
  public Piece crowned() {
    return this;
  }

  /**
   * Determines whether this piece is crowned.
   *
   * @return whether this piece is crowned
   */
  public boolean isCrowned() {
    return isCrowned;
  }

  private final boolean isCrowned;

  /**
   * Returns a printable single-character representation of this piece.
   *
   * @return '+', 'o', '#', or '@'
   */
  public char toChar() {
    return character;
  }

  private final char character;

  @Override
  public String toString() {
    return String.valueOf(character);
  }

  /**
   * Constructs each enumeration variant by initializing its metadata. This constructor cannot be
   * called by the client, and is called exactly once for each of the four enum variants listed
   * above.
   *
   * @param character the character representation of this piece
   * @param player    the player this piece belongs to (non-null)
   * @param isCrowned whether this piece is crowned
   */



  Piece(char character, Player player, boolean isCrowned) {
    Objects.requireNonNull(player);

    this.isCrowned = isCrowned;
    this.player = player;
    this.character = character;
  }
}