import java.util.Comparator;


public class ComparatorInterval<T> extends AbstractInterval<T> {

  Comparator<T> comparator;

  /**
   * constructor for ComparatorInterval
   *
   * @param lower      the lower bound
   * @param lowerType  the lower bound type
   * @param upper      the upper bound
   * @param upperType  the lower bound type
   * @param comparator a comparator for type T
   */
  public ComparatorInterval(T lower, BoundType lowerType, T upper, BoundType upperType,
                            Comparator<T> comparator) {
    super(lower, lowerType, upper, upperType);
    this.comparator = comparator;
  }

  @Override
  protected boolean leftOf(Bound<T> bound, T value) {
    if (comparator.compare(value, bound.bound()) > 0) {
      return false;
    }
    if (comparator.compare(value, bound.bound()) == 0 && bound.type() == BoundType.Open) {
      return false;
    }
    return true;
  }

  @Override
  protected boolean rightOf(Bound<T> bound, T value) {
    if (comparator.compare(value, bound.bound()) < 0) {
      return false;
    }
    if (comparator.compare(value, bound.bound()) == 0 && bound.type() == BoundType.Open) {
      return false;
    }
    return true;
  }

  @Override
  public Comparator<T> getComparator() {
    return comparator;
  }

  @Override
  protected Bound<T> MaxBound(Bound<T> bound1, Bound<T> bound2) {
    if (comparator.compare(bound1.bound(), bound2.bound()) == 0) {
      if (bound1.type() == BoundType.Closed && bound2.type() == BoundType.Open) {
        return bound1;
      }
      return bound2;
    }
    if (comparator.compare(bound1.bound(), bound2.bound()) > 0) {
      return bound1;
    }
    return bound2;
  }

  @Override
  protected Bound<T> MinBound(Bound<T> bound1, Bound<T> bound2) {
    if (comparator.compare(bound1.bound(), bound2.bound()) == 0) {
      if (bound1.type() == BoundType.Open && bound2.type() == BoundType.Closed) {
        return bound1;
      }
      return bound2;
    }
    if (comparator.compare(bound1.bound(), bound2.bound()) < 0) {
      return bound1;
    }
    return bound2;
  }

  @Override
  protected Interval<T> emptyInterval() {
    return new ComparatorInterval<>(null, BoundType.Closed, null, BoundType.Open, comparator);
  }

  @Override
  protected Interval<T> fromBounds(Bound<T> lower, Bound<T> upper) {
    return new ComparatorInterval<>(lower.bound(), lower.type(), upper.bound(), upper.type(),
                                    comparator);
  }

  @Override
  protected Bound<T> LeftMost(Bound<T> bound1, Bound<T> bound2) {
    if (comparator.compare(bound1.bound(), bound2.bound()) == 0) {
      if (bound1.type() == BoundType.Closed && bound2.type() == BoundType.Open) {
        return bound1;
      }
      return bound2;
    }
    if (comparator.compare(bound1.bound(), bound2.bound()) < 0) {
      return bound1;
    }
    return bound2;
  }

  @Override
  protected boolean greaterThan(Bound<T> bound1, Bound<T> bound2) {
    if (comparator.compare(bound1.bound(), bound2.bound()) == 0) {
      if (bound1.type() == BoundType.Closed && bound2.type() == BoundType.Open) {
        return true;
      }
      return false;
    }
    if (comparator.compare(bound1.bound(), bound2.bound()) > 0) {
      return true;
    }
    return false;
  }

  @Override
  protected boolean areEqual(T bound1, T bound2) {
    if (bound1 == null && bound2 == null) {
      return true;
    }
    return comparator.compare(bound1, bound2) == 0;
  }
}


