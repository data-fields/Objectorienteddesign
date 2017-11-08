/*
 * DO NOT MODIFY THIS FILE.
 */

/**
 * The type of bounds for intervals. Each bound can be open (exclusive) or closed (inclusive).
 */
public enum BoundType {
  Open, Closed;

  /**
   * Determines whether this bound type is open.
   *
   * @return {@code this == Open}
   */
  public final boolean isOpen() {
    return this == Open;
  }

  /**
   * Determines whether this bound type is closed.
   *
   * @return {@code this == Closed}
   */
  public final boolean isClosed() {
    return this == Closed;
  }

  /**
   * Whether an interval with this bound type may include an interval with the other bound type,
   * provided the bounds are equal. Both bound types include themselves, and closed includes open.
   * The only false case is that open does not include closed.
   *
   * @param other the other bound type to check (non-null)
   * @return {@code this.isClosed() || other.isOpen()}
   */
  public final boolean includes(BoundType other) {
    return this.isClosed() || other.isOpen();
  }
}
