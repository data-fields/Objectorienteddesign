import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class LruPolicy<K> implements ReplacementPolicy<K> {

  // The capacity of the cache:
  private final int capacity;

  // The circular buffer of items:
  private LinkedList<K> buffer;

  // The number of items in the cache:
  private int size = 0;

  /**
   * Creates a new least recently used queue with capacity {@code capacity}.
   *
   * @param capacity the capacity of the queue.
   * @throws IllegalArgumentException {@code capacity < 1}
   */
  public LruPolicy(int capacity) {
    if (capacity < 1) {
      throw new IllegalArgumentException("capacity must be at least 1");
    }
    // use linked list and iterator for constant time removes and insertions
    buffer = new LinkedList<>();
    this.capacity = capacity;
  }

  @Override
  public K require(K item) {
    // Use iterator rather than get for linear search time and constant add(to head of list)/remove time.
    ListIterator<K> itr = buffer.listIterator();
    while (itr.hasNext()) {
      if (itr.next().equals(item)) {
        itr.remove();
        buffer.addFirst(item);
        return null;
      }
    }
    if (size < capacity) {
      buffer.addFirst(item);
      size++;
      return null;
    }
    buffer.addFirst(item);
    return buffer.removeLast();
  }

  @Override
  public int capacity() {
    return capacity;
  }

  @Override
  public int size() {
    return size;
  }
}
