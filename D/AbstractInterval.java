public abstract class AbstractInterval<T> implements Interval<T> {

  Bound<T> upper;
  Bound<T> lower;

  /**
   * constructor for abstract interval
   *
   * @param lower     the lower bound
   * @param lowerType the lower bound type
   * @param upper     the upper bound
   * @param upperType the lower bound type
   */
  AbstractInterval(T lower, BoundType lowerType, T upper, BoundType upperType) {
    this.lower = new Bound<>(lower, lowerType);
    this.upper = new Bound<>(upper, upperType);
  }

  @Override
  public boolean contains(T value) {
    return leftOf(upper, value) && rightOf(lower, value);
  }

  @Override
  public boolean isEmpty() {
    if (upper.type() == BoundType.Closed && lower.type() == BoundType.Open && areEqual(
        upper.bound(), lower.bound())) {
      return true;
    }
    if (upper.type() == BoundType.Open && lower.type() == BoundType.Closed && areEqual(
        upper.bound(), lower.bound())) {
      return true;
    }
    return false;
  }

  @Override
  public T lowerBound() {
    if (isEmpty()) {
      throw new IllegalStateException();
    }
    return lower.bound();
  }

  @Override
  public T upperBound() {
    if (isEmpty()) {
      throw new IllegalStateException();
    }
    return upper.bound();
  }

  @Override
  public BoundType lowerBoundType() {
    return lower.type();
  }

  @Override
  public BoundType upperBoundType() {
    return upper.type();
  }

  @Override
  public Interval<T> intersection(Interval<T> other) {
    Bound<T> otherLower = new Bound<>(other.lowerBound(), other.lowerBoundType());
    Bound<T> otherUpper = new Bound<>(other.upperBound(), other.upperBoundType());

    if (this.isEmpty() || other.isEmpty()) {
      return emptyInterval();
    }
    if (this.includes(other)) {
      return other;
    }
    if (other.includes(this)) {
      return this;
    }
    if (greaterThan(otherLower, upper) || greaterThan(lower, otherUpper)) {
      return emptyInterval();
    }
    return fromBounds(MaxBound(lower, otherLower), MinBound(upper, otherUpper));
  }

  @Override
  public boolean includes(Interval<T> other) {
    if (other.isEmpty()) {
      return true;
    }
    if (areEqual(this.lowerBound(), other.lowerBound())) {
      if (!this.lowerBoundType().includes(other.lowerBoundType())) {
        return false;
      }
    }

    if (areEqual(this.upperBound(), other.upperBound())) {
      if (!this.upperBoundType().includes(other.upperBoundType())) {
        return false;
      }
    }
    return contains(other.lowerBound()) && contains(other.upperBound());
  }


  @Override
  public Interval<T> span(Interval<T> other) {
    Bound<T> otherLower = new Bound<>(other.lowerBound(), other.lowerBoundType());
    Bound<T> otherUpper = new Bound<>(other.upperBound(), other.upperBoundType());
    if (this.isEmpty()) {
      return other;
    }
    if (other.isEmpty()) {
      return this;
    }
    return fromBounds(LeftMost(lower, otherLower), RightMost(upper, otherUpper));
  }

  /**
   * returns the max bound of two bounds
   *
   * @param bound1 a bound
   * @param bound2 a bound
   * @return the greater of the two bounds
   */
  protected abstract Bound<T> MaxBound(Bound<T> bound1, Bound<T> bound2);

  /**
   * returns the min bound of two bounds
   *
   * @param bound1 a bound
   * @param bound2 a bound
   * @return the lesser of the two bounds
   */
  protected abstract Bound<T> MinBound(Bound<T> bound1, Bound<T> bound2);

  /**
   * returns true if bound1 is greater than bound 2
   *
   * @param bound1 a bound
   * @param bound2 a bound
   * @return true if bound1 > bound2
   */
  protected abstract boolean greaterThan(Bound<T> bound1, Bound<T> bound2);

  /**
   * return true if the bounds are equal
   *
   * @param bound1 a bound
   * @param bound2 a bound
   * @return true if the bounds are equal
   */
  protected abstract boolean areEqual(T bound1, T bound2);

  /**
   * returns an empty interval
   *
   * @return an empty interval
   */
  protected abstract Interval<T> emptyInterval();

  /**
   * factory method to create an interval object
   *
   * @param lower lower bound
   * @param upper upper bound
   * @return a new interval object
   */
  protected abstract Interval<T> fromBounds(Bound<T> lower, Bound<T> upper);

  /**
   * returns the left most bound
   *
   * @param bound1 a bound
   * @param bound2 a bound
   * @return the left most bound
   */
  protected abstract Bound<T> LeftMost(Bound<T> bound1, Bound<T> bound2);

  /**
   * returns the right most bound
   *
   * @param bound1 a bound
   * @param bound2 a bound
   * @return the rightmost bound
   */
  protected Bound<T> RightMost(Bound<T> bound1, Bound<T> bound2) {
    return MaxBound(bound1, bound2);
  }

  /**
   * returns true if value is to the left of bound
   *
   * @param bound a bound
   * @param value a value
   * @return true if the value is to the left of the bound
   */
  protected abstract boolean leftOf(Bound<T> bound, T value);

  /**
   * returns true if value is to the right of bound
   *
   * @param bound a bound
   * @param value a value
   * @return true if the value is to the right of the bound
   */
  protected abstract boolean rightOf(Bound<T> bound, T value);

  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object other) {
    if (other instanceof Interval) {
      Interval<T> interval = (Interval<T>) other;
      if (this.isEmpty() && interval.isEmpty()) {
        return true;
      }
      return areEqual(interval.lowerBound(), lowerBound()) &&
             areEqual(interval.upperBound(), upperBound()) &&
             interval.lowerBoundType() == lowerBoundType() &&
             interval.upperBoundType() == upperBoundType();
    } else {
      throw new IllegalArgumentException("other is not an Interval object");
    }
  }

  @Override
  public int hashCode() {
    return upper.bound.hashCode() + lower.bound.hashCode() + lowerBoundType().hashCode()
           + upperBoundType().hashCode();
  }

  @Override
  public String toString() {
    if (isEmpty()) {
      return "Empty";
    }
    String interval = "";
    if (lower.type() == BoundType.Closed) {
      interval = interval + "[";
    } else {
      interval = interval + "(";
    }
    interval = interval + lower.bound() + ", " + upper.bound();
    if (upper.type() == BoundType.Closed) {
      interval = interval + "]";
    } else {
      interval = interval + ")";
    }
    return interval;
  }

  protected static class Bound<T> {

    private T bound;
    private BoundType type;

    public Bound(T bound, BoundType type) {
      this.bound = bound;
      this.type = type;
    }

    public T bound() {
      return bound;
    }

    public BoundType type() {
      return type;
    }
  }
}
