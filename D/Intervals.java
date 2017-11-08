import java.util.Comparator;

public class Intervals {

  /**
   * creates a comparable closed, closed interval
   *
   * @param lower lower value
   * @param upper upper value
   * @param <T>   type
   * @return a new closed, closed interval
   */
  static <T extends Comparable<T>> Interval<T> closed(T lower, T upper) {
    return new ComparableInterval<>(lower, BoundType.Closed, upper, BoundType.Closed);
  }

  /**
   * create a new closed, closed interval with a comparator
   *
   * @param lower      lower bound
   * @param upper      upper bound
   * @param comparator the comparator object
   * @param <T>        type
   * @return a new closed, closed interval
   */
  static <T> Interval<T> closed(T lower, T upper, Comparator<T> comparator) {
    return new ComparatorInterval<>(lower, BoundType.Closed, upper, BoundType.Closed, comparator);
  }

  /**
   * creates a comparable closed, open interval
   *
   * @param lower lower value
   * @param upper upper value
   * @param <T>   type
   * @return a new closed, open interval
   */
  static <T extends Comparable<T>> Interval<T> closedOpen(T lower, T upper) {
    return new ComparableInterval<>(lower, BoundType.Closed, upper, BoundType.Open);
  }

  /**
   * create a new closed, open interval with a comparator
   *
   * @param lower      lower bound
   * @param upper      upper bound
   * @param comparator the comparator object
   * @param <T>        type
   * @return a new closed, open interval
   */
  static <T> Interval<T> closedOpen(T lower, T upper, Comparator<T> comparator) {
    return new ComparatorInterval<>(lower, BoundType.Closed, upper, BoundType.Open, comparator);
  }

  /**
   * create an empty interval
   *
   * @param <T> type
   * @return a new empty interval
   */
  static <T extends Comparable<T>> Interval<T> empty() {
    return Intervals.closedOpen(null, null);
  }

  /**
   * create an empty interval with a comparator
   *
   * @param comparator the comparator object
   * @param <T>        type
   * @return a new empty intervals
   */
  static <T> Interval<T> empty(Comparator<T> comparator) {
    return Intervals.closedOpen(null, null, comparator);
  }

  /**
   * create a new interval
   *
   * @param lower     the lower value
   * @param lowerType the lower type
   * @param upper     the upper value
   * @param upperType the upper type
   * @param <T>       type
   * @return a new interval
   */
  static <T extends Comparable<T>> Interval<T> interval(T lower, BoundType lowerType, T upper,
                                                        BoundType upperType) {
    return new ComparableInterval<>(lower, lowerType, upper, upperType);
  }

  /**
   * create a new interval, takes a comparator
   *
   * @param lower      the lower value
   * @param lowerType  the lower type
   * @param upper      the upper value
   * @param upperType  the upper type
   * @param comparator the comparator object
   * @param <T>        type
   * @return a new interval
   */
  static <T> Interval<T> interval(T lower, BoundType lowerType, T upper, BoundType upperType,
                                  Comparator<T> comparator) {
    return new ComparatorInterval<>(lower, lowerType, upper, upperType, comparator);
  }

  /**
   * create a new open, open interval
   *
   * @param lower the lower value
   * @param upper the upper value
   * @param <T>   type
   * @return a new open, open value
   */
  static <T extends Comparable<T>> Interval<T> open(T lower, T upper) {
    return new ComparableInterval<>(lower, BoundType.Open, upper, BoundType.Open);
  }

  /**
   * create a new open, open interval with a comparator
   *
   * @param lower      the lower value
   * @param upper      the upper value
   * @param comparator the comparator object
   * @param <T>        type
   * @return a new open, open interval
   */
  static <T> Interval<T> open(T lower, T upper, Comparator<T> comparator) {
    return new ComparatorInterval<>(lower, BoundType.Open, upper, BoundType.Open, comparator);
  }

  /**
   * create a new open, closed interval
   *
   * @param lower the lower bound
   * @param upper the upper bound
   * @param <T>   type
   * @return a open, closed interval
   */
  static <T extends Comparable<T>> Interval<T> openClosed(T lower, T upper) {
    return new ComparableInterval<>(lower, BoundType.Open, upper, BoundType.Closed);
  }

  /**
   * create a new open, closed interval with a comparator
   *
   * @param lower      the lower bound
   * @param upper      the upper bound
   * @param comparator the comparator object
   * @param <T>        type
   * @return a new open, closed interval
   */
  static <T> Interval<T> openClosed(T lower, T upper, Comparator<T> comparator) {
    return new ComparatorInterval<>(lower, BoundType.Open, upper, BoundType.Closed, comparator);
  }

  /**
   * create a new singleton interval
   *
   * @param value the value
   * @param <T>   type
   * @return a new singleton interval
   */
  static <T extends Comparable<T>> Interval<T> singleton(T value) {
    return new ComparableInterval<>(value, BoundType.Closed, value, BoundType.Closed);
  }

  /**
   * create a new singleton interval with a comparator object
   *
   * @param value      the value
   * @param comparator the comparator object
   * @param <T>        type
   * @return a new singleton interval
   */
  static <T> Interval<T> singleton(T value, Comparator<T> comparator) {
    return new ComparatorInterval<>(value, BoundType.Closed, value, BoundType.Closed, comparator);
  }
}