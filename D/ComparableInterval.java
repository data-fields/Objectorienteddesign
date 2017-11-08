import java.util.Comparator;

class ComparableInterval<T extends Comparable<T>> extends AbstractInterval<T> {

  /**
   * constructor for ComparableInterval
   *
   * @param lower     the lower bound
   * @param lowerType the lower bound type
   * @param upper     the upper bound
   * @param upperType the lower bound type
   */
  public ComparableInterval(T lower, BoundType lowerType, T upper, BoundType upperType) {
    super(lower, lowerType, upper, upperType);
  }

  @Override
  protected boolean leftOf(Bound<T> bound, T value) {
    if (value.compareTo(bound.bound()) > 0) {
      return false;
    }
    if (value.compareTo(bound.bound()) == 0 && bound.type() == BoundType.Open) {
      return false;
    }
    return true;
  }

  @Override
  protected boolean rightOf(Bound<T> bound, T value) {
    if (value.compareTo(bound.bound()) < 0) {
      return false;
    }
    if (value.compareTo(bound.bound()) == 0 && bound.type() == BoundType.Open) {
      return false;
    }
    return true;
  }

  @Override
  protected boolean areEqual(T bound1, T bound2) {
    if (bound1 == null && bound2 == null) {
      return true;
    }
    if (bound1 == null || bound2 == null) {
      return false;
    }
    return bound1.compareTo(bound2) == 0;
  }

  @Override
  protected Interval<T> emptyInterval() {
    return new ComparableInterval<>(null, BoundType.Closed, null, BoundType.Open);
  }

  @Override
  protected Interval<T> fromBounds(Bound<T> lower, Bound<T> upper) {
    return new ComparableInterval<>(lower.bound(), lower.type(), upper.bound(), upper.type());
  }

  @Override
  protected Bound<T> MaxBound(Bound<T> bound1, Bound<T> bound2) {
    if (bound1.bound().compareTo(bound2.bound()) == 0) {
      if (bound1.type() == BoundType.Closed && bound2.type() == BoundType.Open) {
        return bound1;
      }
      return bound2;
    }
    if (bound1.bound().compareTo(bound2.bound()) > 0) {
      return bound1;
    }
    return bound2;
  }

  @Override
  protected Bound<T> MinBound(Bound<T> bound1, Bound<T> bound2) {
    if (bound1.bound().compareTo(bound2.bound()) == 0) {
      if (bound1.type() == BoundType.Open && bound2.type() == BoundType.Closed) {
        return bound1;
      }
      return bound2;
    }
    if (bound1.bound().compareTo(bound2.bound()) < 0) {
      return bound1;
    }
    return bound2;
  }

  @Override
  protected boolean greaterThan(Bound<T> bound1, Bound<T> bound2) {
    if (bound1.bound().compareTo(bound2.bound()) == 0) {
      if (bound1.type() == BoundType.Closed && bound2.type() == BoundType.Open) {
        return true;
      }
      return false;
    }
    if (bound1.bound().compareTo(bound2.bound()) > 0) {
      return true;
    }
    return false;
  }

  @Override
  protected Bound<T> LeftMost(Bound<T> bound1, Bound<T> bound2) {
    if (bound1.bound().compareTo(bound2.bound()) == 0) {
      if (bound1.type() == BoundType.Closed && bound2.type() == BoundType.Open) {
        return bound1;
      }
      return bound2;
    }
    if (bound1.bound().compareTo(bound2.bound()) < 0) {
      return bound1;
    }
    return bound2;
  }


  @Override
  @SuppressWarnings("unchecked")
  public Comparator<T> getComparator() {
    return new ComparableIntervalComparator();
  }

  private static class ComparableIntervalComparator<T extends Comparable<T>>
      implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
      return o1.compareTo(o2);
    }
  }
}
