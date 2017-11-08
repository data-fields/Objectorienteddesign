/*
 * DO NOT MODIFY THIS FILE.
 */

import java.util.Comparator;

/**
 * This class has no functionality and should not be altered. Its purpose is to check whether the
 * static factory methods in {@link Intervals} all exist and have the right types. If this file
 * compiles along with your code then there's a good chance that you got that aspect of the API
 * correct. If it doesn't then you probably have a problem.
 */

public class IntervalsCheck {

  private IntervalsCheck() {
  } // prevents instantiation.

  public static void dummyMethod() {
    doubleInterval(Intervals.closed(3d, 5d));
    doubleInterval(Intervals.open(3d, 5d));
    doubleInterval(Intervals.closedOpen(3d, 5d));
    doubleInterval(Intervals.openClosed(3d, 5d));
    doubleInterval(Intervals.singleton(4d));
    doubleInterval(Intervals.empty());
    doubleInterval(Intervals.interval(3d, BoundType.Closed,
                                      5d, BoundType.Open));

    Comparator<Double> comparator = Comparator.reverseOrder();

    doubleInterval(Intervals.closed(8d, 5d, comparator));
    doubleInterval(Intervals.open(8d, 5d, comparator));
    doubleInterval(Intervals.closedOpen(8d, 5d, comparator));
    doubleInterval(Intervals.openClosed(8d, 5d, comparator));
    doubleInterval(Intervals.singleton(8d, comparator));
    doubleInterval(Intervals.empty(comparator));
    doubleInterval(Intervals.interval(8d, BoundType.Closed,
                                      5d, BoundType.Open,
                                      comparator));
  }

  public static void doubleInterval(Interval<Double> dummy) {
  }
}
