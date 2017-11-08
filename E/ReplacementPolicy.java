/**
 * A replacement policy for managing a cache. This class keeps track of items in a cache, and when
 * the client requires an item, it notifies the cache via the {@code require} method. The policy
 * then tells the client what item to evict, if any.
 *
 * <p>Items are compared using {@link Object#equals(Object)}.
 *
 * @param <K> keys for cached items
 */
public interface ReplacementPolicy<K> {

  /**
   * Informs the policy manager that a particular item is required and must be brought into the
   * cache if not already there. Returns the evicted item if eviction is required to make room, or
   * {@code null} otherwise.
   *
   * <p>Must run in <em>O</em>({@code capacity()}) time.
   *
   * @param item the required item (non-null)
   * @return the evicted item or {@code null}
   */
  public abstract K require(K item);

  /**
   * Returns the capacity of the cache.
   *
   * @return the capacity
   */
  public abstract int capacity();

  /**
   * Returns the number of items currently in the cache. Note that it should always be the case that
   * {@code size() ≤ capacity()}.
   *
   * @return number of items currently cached
   */
  public abstract int size();
}
