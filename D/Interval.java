import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Generic intervals over comparable types. <p> In interval represents a continuous (or more
 * formally, convex) set of values between two endpoints. Either endpoint may be included in the
 * interval, in which case we say that the endpoint is closed, or excluded, in which case the
 * endpoint is open. For example (assuming we are working with {@code Double}s): <ul> <li>The
 * interval (0, 1) represents all the numbers between 0 and 1, with both 0 and 1 excluded. <li>The
 * interval [0, 1) represents all the numbers between 0 and 1, with 0 included and 1 excluded.
 * <li>The interval (0, 1] represents all the numbers between 0 and 1, with 0 excluded and 1
 * included. <li>The interval [0, 1] represents all the numbers between 0 and 1, with both 0 and 1
 * included. </ul> <p> Additionally, a closed interval where both endpoints are the same represents
 * the singleton set of that one number. That is, [3, 3] represents the set containing only 3. A
 * half-open-half-closed interval where both endpoints are the same, such as [5, 5) or (9, 9]
 * represents the empty set. Because the bound in such cases is arbitrary, all intervals
 * representing the empty set are equal. Intervals where the lower bound is greater than the upper
 * bound are undefined, and implementations of this interface must avoid constructing them. <p> Note
 * that the notions of less-than and greater for type {@code T} used to define the interval are
 * determined by a comparator, which can be retrieved using {@link #getComparator()}. Even for
 * numeric types, it may be the case that that the order is not the natural order. Operations that
 * involve multiple intervals ({@link #includes(Interval) #includes(Interval&lt;T&gt;)}, {@link
 * #intersection(Interval) #intersection(Interval&lt;T&gt;)}, and {@link #span(Interval)
 * #span(Interval&lt;T&gt;)}) require that both intervals involved use the same comparator (or at
 * least comparators that are observably equivalent); if the comparators disagree then the behavior
 * of the operations is undefined and may be incoherent.
 *
 * @param <T> the type of the interval endpoints
 */
public interface Interval<T> extends Predicate<T> {

  /**
   * Determines whether a particular value lies in this interval.
   *
   * @param value the value to check (non-null)
   * @return whether the value is in the interval
   */
  boolean contains(T value);

  /**
   * Determines whether this interval is empty.
   *
   * @return whether this interval is empty
   */
  boolean isEmpty();

  /**
   * The lower bound for this interval. Whether the lower bound is included or excluded depends on
   * the result of {@link #lowerBoundType()}.
   *
   * @return the lower bound
   * @throws IllegalStateException if this interval is empty
   */
  T lowerBound();

  /**
   * The upper bound for this interval. Whether the upper bound is included or excluded depends on
   * the result of {@link #upperBoundType()}.
   *
   * @return the upper bound
   * @throws IllegalStateException if this interval is empty
   */
  T upperBound();

  /**
   * Whether the lower bound of this interval is open (i.e., excluded from the interval) or closed
   * (i.e., included in the interval). <p> By convention, the lower bound of the empty interval is
   * closed.
   *
   * @return whether the lower bound is open or closed
   */
  BoundType lowerBoundType();

  /**
   * Whether the upper bound of this interval is open (i.e., excluded from the interval) or closed
   * (i.e., included in the interval). <p> By convention, the upper bound of the empty interval is
   * open.
   *
   * @return whether the upper bound is open or closed
   */
  BoundType upperBoundType();

  /**
   * Determines whether this interval includes the other interval. In terms of sets, that means that
   * this interval is a superset of the other interval. The empty interval, regardless of its
   * (arbitrary) endpoints, is included in every interval.
   *
   * @param other the interval to check (non-null)
   * @return whether this interval includes the other
   */
  boolean includes(Interval<T> other);

  /**
   * Returns the intersection of two intervals. The intersection is the interval that contains all
   * points that are in both intervals; in other words, it is the largest interval that is included
   * in both this interval and the other interval. If the intervals do not intersect then the result
   * is the empty interval. <p> For example, the intersection of (1, 5) and [3, 10] is [3, 5), since
   * 3 is included in both, all the points between 3 and 5 are included in both, but 5 is not
   * included in both. <p> The comparators used by both this interval and the other must define the
   * same order. However, this condition cannot be checked by the method, and thus passing it
   * intervals with inconsistent comparators will produce undefined results.
   *
   * @param other the interval to intersect with this interval (non-null)
   * @return the intersection
   */
  Interval<T> intersection(Interval<T> other);

  /**
   * Returns an interval spanning two intervals. The span is the smallest interval that includes
   * both this interval and the other interval. Note that this is different from a set union,
   * because if there is a gap between the two intervals then the span includes both intervals
   * <em>and</em> the gap. <p> For example, the span of (1, 2) and [4, 10] is (1, 10], since the
   * smaller lower bound of the two intervals is 1 (exclusive), and the larger upper bound of the
   * two intervals is 10 (inclusive). Both (1, 2) and [4, 10] are included in (1, 10], but if we
   * moved either bound inward then at least one of them would not be included. <p> The comparators
   * used by both this interval and the other must define the same order. However, this condition
   * cannot be checked by the method, and thus passing it intervals with inconsistent comparators
   * will produce undefined results.
   *
   * @param other the interval to span along with this interval (non-null)
   * @return the span
   */
  Interval<T> span(Interval<T> other);

  /**
   * Returns the comparator that defines the order for this interval. That is, the lower bound must
   * be less than or equal to the upper bound according to the returned comparator, and the values
   * in the interval are those that are between the lower and upper bounds according to the
   * comparator. Equality as needed for closed bounds is defined by where the comparator returns 0,
   * not where the {@link Object#equals(Object)} method for type {@code T} considers them to be
   * equal.
   *
   * @return the comparator for this interval
   */
  Comparator<T> getComparator();

  /**
   * Determines whether two intervals are equal. Two intervals are equal if and only if: <ul>
   * <li>they share the same lower endpoint type and the same upper endpoint type (though lower may
   * of course differ from upper), and their endpoints are equal according to {@code equals}, or
   * <li>they are both the empty interval. </ul>
   *
   * @param other the interval to compare to this one
   * @return whether the intervals are equal
   */
  @Override
  boolean equals(Object other);

  /**
   * Determines whether a particular value lies in this interval. This method should always be the
   * same as {@link #contains(Object) #contains(T)}, and is included so that intervals implement the
   * {@link Predicate} interface. Implementations of the {@code Interval} interface need not and
   * probably should not override this method.
   *
   * @param value the value to check (non-null)
   * @return whether the value is in the interval
   */
  @Override
  default boolean test(T value) {
    return contains(value);
  }
}
