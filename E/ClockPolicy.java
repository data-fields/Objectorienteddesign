/**
 * A cache policy implementing the <a href="http://en.wikipedia.org/wiki/Page_replacement_algorithm#Clock">clock
 * algorithm</a>.
 */
public final class ClockPolicy<K> implements ReplacementPolicy<K> {

  // The capacity of the cache:
  private final int capacity;

  // The circular buffer of items:
  private final K[] buffer;

  // Whether each item has been referenced since the last eviction cycle:
  private final boolean[] refBits;

  // The hand of the clock (i.e., the start index for the queue):
  private int hand = 0;

  // The number of items in the cache:
  private int size = 0;

  /*
   * CLASS INVARIANTS:
   *
   *  - buffer.length == capacity
   *  - refBits.length == capacity
   *  - hand < capacity
   *  - size <= capacity
   *  - items in buffer are unique
   *
   * REPRESENTATION NOTE:
   *
   * The main idea here is that the two arrays are circular buffers,
   * in the sense that the starting index {@code hand} can be anywhere in
   * the buffer, and the logical queue wraps around from the end to the
   * beginning.
   *
   * Thus, the nth element of the queue can be found at (hand + n) % capacity.
   *
   * Example: the array { 3, 4, 0, 0, 0, 1, 2 } with hand = 5 and size = 4
   * stands for the logical queue 1, 2, 3, 4. This is because the head of
   * the queue starts at the 5th (0-based) element of the array and then
   * wraps around to include 4 items.
   *
   * (Note that the above example will not happen with this class,
   * because the array fills from left to right, and once full stays full.
   * Thus, it will always be the case that if hand != 0 then size ==
   * capacity. Nowhere do we rely on this invariant.
   */

  /**
   * Creates a new clock queue with capacity {@code capacity}.
   *
   * @param cap the capacity of the queue.
   * @throws IllegalArgumentException {@code cap < 1}
   */
  public ClockPolicy(int cap) {
    if (cap < 1) {
      throw new IllegalArgumentException("capacity must be at least 1");
    }

    /*
     * Java doesn't allow directly creating arrays of type parameters such as
     * {@code K}, so instead we need to create an {@code Object} array and
     * then cast. This cast produces a warning because it cannot actually be
     * checked here, and instead gets checked whenever we access the array.
     * The @SuppressWarnings annotation suppresses that warning for just the
     * declaration that follows it. (The annotation has to be attached to a
     * declaration, not an arbitrary expression, so we declare a temporary
     * to minimize the scope of the annotation to just the unchecked cast
     * operation.)
     */
    @SuppressWarnings("unchecked")
    K[] temp = (K[]) new Object[cap];

    capacity = cap;
    buffer = temp;
    refBits = new boolean[capacity];
  }

  @Override
  public int capacity() {
    return capacity;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public K require(K item) {
    // First see if item is in the cache. If so, we mark it as referenced
    // and don't need to evict:
    for (int i = 0; i < size; ++i) {
      if (buffer[offset(i)].equals(item)) {
        // It's a hit!
        refBits[offset(i)] = true;
        return null;
      }
    }

    // It's a miss!

    K evicted = null;

    // If it's full, we need to evict something. We cycle through the buffer,
    // looking for an unset reference bit, and clearing as we go,
    // which guarantees that we'll see one the next time around. Advancing
    // {@code hand} without changing {@code size} rotates through the buffer.
    if (size == capacity) {
      while (refBits[hand]) {
        refBits[hand] = false;
        hand = offset(1);
      }

      // Now we've found a false reference bit, so that's what we'll evict.
      // This means we advance the hand past it and decrement the size.
      evicted = buffer[hand];
      hand = offset(1);
      --size;
    }

    // At this point, we know that size < capacity, so we can store the item
    // on the new end of the queue and increment the size.

    buffer[offset(size)] = item;
    refBits[offset(size)] = true;
    ++size;

    return evicted;
  }

  /**
   * Returns an absolute index into the arrays {@code buffer} and {@code refBits} given given a
   * relative index from the logical start of the queue. This implements the circular buffer's
   * wrap-around.
   *
   * @param i the relative index
   * @return the absolute index
   */
  private int offset(int i) {
    return (hand + i) % capacity;
  }
}
