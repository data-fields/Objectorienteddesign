import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IntervalsTest {

  IntegerComparator integerComparator;

  private static class IntegerComparator<Integer extends Comparable<Integer>>
      implements Comparator<Integer> {

    @Override
    public int compare(Integer o1, Integer o2) {
      return o1.compareTo(o2);
    }
  }

  @Before
  public void initialize() {
    integerComparator = new IntegerComparator();
  }

  @Test
  public void open_closed_contains_test() {
    Interval<Integer> interval = Intervals.openClosed(1, 4);
    assertEquals(interval.isEmpty(), false);
    assertEquals(interval.contains(1), false);
    assertEquals(interval.contains(4), true);
    assertEquals(interval.contains(2), true);

    interval = Intervals.openClosed(1, 4, integerComparator);
    assertEquals(interval.isEmpty(), false);
    assertEquals(interval.contains(1), false);
    assertEquals(interval.contains(4), true);
    assertEquals(interval.contains(2), true);
  }

  @Test
  public void closed_open_contains_test() {
    Interval<Integer> interval = Intervals.closedOpen(1, 4);
    assertEquals(interval.isEmpty(), false);
    assertEquals(interval.contains(1), true);
    assertEquals(interval.contains(4), false);
    assertEquals(interval.contains(2), true);

    interval = Intervals.closedOpen(1, 4, integerComparator);
    assertEquals(interval.isEmpty(), false);
    assertEquals(interval.contains(1), true);
    assertEquals(interval.contains(4), false);
    assertEquals(interval.contains(2), true);
  }

  @Test
  public void open_contains_test() {
    Interval<Integer> interval = Intervals.open(1, 4);
    assertEquals(interval.isEmpty(), false);
    assertEquals(interval.contains(1), false);
    assertEquals(interval.contains(4), false);
    assertEquals(interval.contains(2), true);

    interval = Intervals.open(1, 4, integerComparator);
    assertEquals(interval.isEmpty(), false);
    assertEquals(interval.contains(1), false);
    assertEquals(interval.contains(4), false);
    assertEquals(interval.contains(2), true);
  }

  @Test
  public void closed_contains_test() {
    Interval<Integer> interval = Intervals.closed(1, 4);
    assertEquals(interval.isEmpty(), false);
    assertEquals(interval.contains(1), true);
    assertEquals(interval.contains(4), true);
    assertEquals(interval.contains(2), true);

    interval = Intervals.closed(1, 4, integerComparator);
    assertEquals(interval.isEmpty(), false);
    assertEquals(interval.contains(1), true);
    assertEquals(interval.contains(4), true);
    assertEquals(interval.contains(2), true);
  }

  @Test
  public void open_closed_includes_test() {
    Interval<Integer> interval1 = Intervals.openClosed(1, 4);
    Interval<Integer> interval2 = Intervals.openClosed(4, 5);
    Interval<Integer> interval3 = Intervals.openClosed(2, 3);
    Interval<Integer> interval4 = Intervals.openClosed(2, 4);
    Interval<Integer> interval5 = Intervals.openClosed(1, 3);
    Interval<Integer> empty = Intervals.empty();
    Interval<Integer> singleton1 = Intervals.singleton(1);
    Interval<Integer> singleton2 = Intervals.singleton(4);
    Interval<Integer> singleton3 = Intervals.singleton(3);
    assertEquals(interval1.includes(singleton1), false);
    assertEquals(interval1.includes(singleton2), true);
    assertEquals(interval1.includes(singleton3), true);
    assertEquals(interval1.includes(interval2), false);
    assertEquals(interval1.includes(interval3), true);
    assertEquals(interval1.includes(interval4), true);
    assertEquals(interval1.includes(interval5), false);
    assertEquals(interval1.includes(empty), true);

  }

  @Test
  public void open_includes_closed_test() {
    Interval<Integer> interval1 = Intervals.closed(1, 4);
    Interval<Integer> interval2 = Intervals.open(1, 4);
    assertTrue(interval1.includes(interval2));
    assertFalse(interval2.includes(interval1));

    interval1 = Intervals.closed(1, 4, integerComparator);
    interval2 = Intervals.open(1, 4, integerComparator);
    assertTrue(interval1.includes(interval2));
    assertFalse(interval2.includes(interval1));
  }

  @Test
  public void testSpan() {
    Interval<Integer> interval1 = Intervals.openClosed(3, 4);
    Interval<Integer> interval2 = Intervals.closedOpen(3, 4);
    Interval<Integer> interval3 = interval1.span(interval2);
    assertTrue(interval3.lowerBound().equals(3));
    assertTrue(interval3.upperBound().equals(4));
    assertTrue(interval3.lowerBoundType().equals(BoundType.Closed));
    assertTrue(interval3.upperBoundType().equals(BoundType.Closed));

    interval1 = Intervals.openClosed(3, 4, integerComparator);
    interval2 = Intervals.closedOpen(3, 4, integerComparator);
    interval3 = interval1.span(interval2);
    assertTrue(interval3.lowerBound().equals(3));
    assertTrue(interval3.upperBound().equals(4));
    assertTrue(interval3.lowerBoundType().equals(BoundType.Closed));
    assertTrue(interval3.upperBoundType().equals(BoundType.Closed));
  }

  @Test
  public void testSpan2() {
    Interval<Integer> interval1 = Intervals.open(3, 5);
    Interval<Integer> interval2 = Intervals.closed(4, 6);
    Interval<Integer> interval3 = interval1.span(interval2);
    assertTrue(interval3.lowerBound().equals(3));
    assertTrue(interval3.upperBound().equals(6));
    assertTrue(interval3.lowerBoundType().equals(BoundType.Open));
    assertTrue(interval3.upperBoundType().equals(BoundType.Closed));

    interval1 = Intervals.open(3, 5, integerComparator);
    interval2 = Intervals.closed(4, 6, integerComparator);
    interval3 = interval1.span(interval2);
    assertTrue(interval3.lowerBound().equals(3));
    assertTrue(interval3.upperBound().equals(6));
    assertTrue(interval3.lowerBoundType().equals(BoundType.Open));
    assertTrue(interval3.upperBoundType().equals(BoundType.Closed));
  }

  @Test
  public void testSpan3() {
    Interval<Integer> interval1 = Intervals.open(2, 3);
    Interval<Integer> interval2 = Intervals.open(5, 8);
    Interval<Integer> interval3 = interval1.span(interval2);
    assertTrue(interval3.lowerBound().equals(2));
    assertTrue(interval3.upperBound().equals(8));
    assertTrue(interval3.lowerBoundType().equals(BoundType.Open));
    assertTrue(interval3.upperBoundType().equals(BoundType.Open));

    interval1 = Intervals.open(2, 3, integerComparator);
    interval2 = Intervals.open(5, 8, integerComparator);
    interval3 = interval1.span(interval2);
    assertTrue(interval3.lowerBound().equals(2));
    assertTrue(interval3.upperBound().equals(8));
    assertTrue(interval3.lowerBoundType().equals(BoundType.Open));
    assertTrue(interval3.upperBoundType().equals(BoundType.Open));
  }

  @Test
  public void testSpan4() {
    Interval<Integer> interval1 = Intervals.empty();
    Interval<Integer> interval2 = Intervals.open(5, 8);
    Interval<Integer> interval3 = interval1.span(interval2);
    assertTrue(interval3.lowerBound().equals(5));
    assertTrue(interval3.upperBound().equals(8));
    assertTrue(interval3.lowerBoundType().equals(BoundType.Open));
    assertTrue(interval3.upperBoundType().equals(BoundType.Open));

    interval1 = Intervals.empty(integerComparator);
    interval2 = Intervals.open(5, 8, integerComparator);
    interval3 = interval1.span(interval2);
    assertTrue(interval3.lowerBound().equals(5));
    assertTrue(interval3.upperBound().equals(8));
    assertTrue(interval3.lowerBoundType().equals(BoundType.Open));
    assertTrue(interval3.upperBoundType().equals(BoundType.Open));
  }

  @Test
  public void testIntersection() {
    Interval<Integer> interval1 = Intervals.open(3, 19);
    Interval<Integer> interval2 = Intervals.open(5, 8);
    Interval<Integer> interval3 = interval1.intersection(interval2);
    assertTrue(interval3.lowerBound().equals(5));
    assertTrue(interval3.upperBound().equals(8));
    assertTrue(interval3.lowerBoundType().equals(BoundType.Open));
    assertTrue(interval3.upperBoundType().equals(BoundType.Open));

    interval1 = Intervals.open(3, 19, integerComparator);
    interval2 = Intervals.open(5, 8, integerComparator);
    interval3 = interval1.intersection(interval2);
    assertTrue(interval3.lowerBound().equals(5));
    assertTrue(interval3.upperBound().equals(8));
    assertTrue(interval3.lowerBoundType().equals(BoundType.Open));
    assertTrue(interval3.upperBoundType().equals(BoundType.Open));
  }

  @Test
  public void testIntersection1() {
    Interval<Integer> interval1 = Intervals.open(3, 5);
    Interval<Integer> interval2 = Intervals.closed(4, 6);
    Interval<Integer> interval3 = interval1.intersection(interval2);
    assertEquals(interval3.lowerBound(), (Integer) 4);
    assertEquals(interval3.upperBound(), (Integer) 5);
    assertEquals(interval3.lowerBoundType(), BoundType.Closed);
    assertEquals(interval3.upperBoundType(), BoundType.Open);

    interval1 = Intervals.open(3, 5, integerComparator);
    interval2 = Intervals.closed(4, 6, integerComparator);
    interval3 = interval1.intersection(interval2);
    assertEquals(interval3.lowerBound(), (Integer) 4);
    assertEquals(interval3.upperBound(), (Integer) 5);
    assertEquals(interval3.lowerBoundType(), BoundType.Closed);
    assertEquals(interval3.upperBoundType(), BoundType.Open);
  }

  @Test
  public void testIntersection2() {
    Interval<Integer> interval1 = Intervals.open(0, 5);
    Interval<Integer> interval2 = Intervals.closed(5, 10);
    Interval<Integer> interval3 = interval1.intersection(interval2);
    assertEquals(interval3.isEmpty(), true);

    interval1 = Intervals.open(0, 5, integerComparator);
    interval2 = Intervals.closed(5, 10, integerComparator);
    interval3 = interval1.intersection(interval2);
    assertEquals(interval3.isEmpty(), true);
  }

  @Test
  public void testEquals() {
    Interval<Integer> interval1 = Intervals.open(3, 5);
    Interval<Integer> interval2 = Intervals.open(3, 5);
    Interval<Integer> interval3 = Intervals.closed(3, 5);
    Interval<Integer> interval4 = Intervals.open(4, 6);
    Interval<Integer> interval5 = Intervals.closed(4, 6);
    assertTrue(interval1.equals(interval2));
    assertFalse(interval1.equals(interval3));
    assertFalse(interval1.equals(interval4));
    assertFalse(interval1.equals(interval5));

    interval1 = Intervals.open(3, 5, integerComparator);
    interval2 = Intervals.open(3, 5, integerComparator);
    interval3 = Intervals.closed(3, 5, integerComparator);
    interval4 = Intervals.open(4, 6, integerComparator);
    interval5 = Intervals.closed(4, 6, integerComparator);
    assertTrue(interval1.equals(interval2));
    assertFalse(interval1.equals(interval3));
    assertFalse(interval1.equals(interval4));
    assertFalse(interval1.equals(interval5));
  }

  @Test
  public void testSingleton() {
    Interval<Integer> interval = Intervals.singleton(10);
    assertTrue(interval.contains(10));
    assertTrue(interval.lowerBound().compareTo(interval.upperBound()) == 0);
    assertTrue(interval.lowerBoundType() == BoundType.Closed
               && interval.upperBoundType() == BoundType.Closed);

    interval = Intervals.singleton(10, integerComparator);
    assertTrue(interval.contains(10));
    assertTrue(interval.lowerBound().compareTo(interval.upperBound()) == 0);
    assertTrue(interval.lowerBoundType() == BoundType.Closed
               && interval.upperBoundType() == BoundType.Closed);
  }

  @Test
  public void testGetComparator() {
    Interval<Integer> interval1 = Intervals.open(3, 5);
    Comparator<Integer> comparator = interval1.getComparator();
    assertTrue(comparator.compare(1, 2) < 0);
    assertTrue(comparator.compare(2, 1) > 0);
    assertTrue(comparator.compare(1, 1) == 0);

    interval1 = Intervals.open(3, 5, integerComparator);
    comparator = interval1.getComparator();
    assertTrue(comparator.compare(1, 2) < 0);
    assertTrue(comparator.compare(2, 1) > 0);
    assertTrue(comparator.compare(1, 1) == 0);
  }

  @Test
  public void testStaticConstructorsClosed() {
    Interval<Integer> interval = Intervals.closed(1, 2);
    assertEquals(interval.lowerBound(), (Integer) 1);
    assertEquals(interval.upperBound(), (Integer) 2);
    assertEquals(interval.lowerBoundType(), BoundType.Closed);
    assertEquals(interval.upperBoundType(), BoundType.Closed);

    interval = Intervals.closed(1, 2, integerComparator);
    assertEquals(interval.lowerBound(), (Integer) 1);
    assertEquals(interval.upperBound(), (Integer) 2);
    assertEquals(interval.lowerBoundType(), BoundType.Closed);
    assertEquals(interval.upperBoundType(), BoundType.Closed);
  }

  @Test
  public void testStaticConstructorsOpenClosed() {
    Interval<Integer> interval = Intervals.openClosed(1, 2);
    assertEquals(interval.lowerBound(), (Integer) 1);
    assertEquals(interval.upperBound(), (Integer) 2);
    assertEquals(interval.lowerBoundType(), BoundType.Open);
    assertEquals(interval.upperBoundType(), BoundType.Closed);

    interval = Intervals.openClosed(1, 2, integerComparator);
    assertEquals(interval.lowerBound(), (Integer) 1);
    assertEquals(interval.upperBound(), (Integer) 2);
    assertEquals(interval.lowerBoundType(), BoundType.Open);
    assertEquals(interval.upperBoundType(), BoundType.Closed);
  }

  @Test
  public void testStaticConstructorsClosedOpen() {
    Interval<Integer> interval = Intervals.closedOpen(1, 2);
    assertEquals(interval.lowerBound(), (Integer) 1);
    assertEquals(interval.upperBound(), (Integer) 2);
    assertEquals(interval.lowerBoundType(), BoundType.Closed);
    assertEquals(interval.upperBoundType(), BoundType.Open);

    interval = Intervals.closedOpen(1, 2, integerComparator);
    assertEquals(interval.lowerBound(), (Integer) 1);
    assertEquals(interval.upperBound(), (Integer) 2);
    assertEquals(interval.lowerBoundType(), BoundType.Closed);
    assertEquals(interval.upperBoundType(), BoundType.Open);
  }

  @Test
  public void testStaticConstructorsSingleton() {
    Interval<Integer> interval = Intervals.singleton(1);
    assertEquals(interval.lowerBound(), (Integer) 1);
    assertEquals(interval.upperBound(), (Integer) 1);
    assertEquals(interval.lowerBoundType(), BoundType.Closed);
    assertEquals(interval.upperBoundType(), BoundType.Closed);

    interval = Intervals.singleton(1, integerComparator);
    assertEquals(interval.lowerBound(), (Integer) 1);
    assertEquals(interval.upperBound(), (Integer) 1);
    assertEquals(interval.lowerBoundType(), BoundType.Closed);
    assertEquals(interval.upperBoundType(), BoundType.Closed);
  }

  @Test
  public void testStaticConstructorsOpen() {
    Interval<Integer> interval = Intervals.open(1, 2);
    assertEquals(interval.lowerBound(), (Integer) 1);
    assertEquals(interval.upperBound(), (Integer) 2);
    assertEquals(interval.lowerBoundType(), BoundType.Open);
    assertEquals(interval.upperBoundType(), BoundType.Open);

    interval = Intervals.open(1, 2, integerComparator);
    assertEquals(interval.lowerBound(), (Integer) 1);
    assertEquals(interval.upperBound(), (Integer) 2);
    assertEquals(interval.lowerBoundType(), BoundType.Open);
    assertEquals(interval.upperBoundType(), BoundType.Open);
  }

  @Test
  public void testStaticConstructorsEmpty() {
    Interval<Integer> interval = Intervals.empty();
    assertTrue(interval.isEmpty());

    interval = Intervals.empty(integerComparator);
    assertTrue(interval.isEmpty());
  }

  @Test
  public void testStaticConstructorsVanilla() {
    Interval<Integer> interval = Intervals.interval(1, BoundType.Closed, 2, BoundType.Open);
    assertEquals(interval.lowerBound(), (Integer) 1);
    assertEquals(interval.upperBound(), (Integer) 2);
    assertEquals(interval.lowerBoundType(), BoundType.Closed);
    assertEquals(interval.upperBoundType(), BoundType.Open);

    interval = Intervals.interval(1, BoundType.Closed, 2, BoundType.Open, integerComparator);
    assertEquals(interval.lowerBound(), (Integer) 1);
    assertEquals(interval.upperBound(), (Integer) 2);
    assertEquals(interval.lowerBoundType(), BoundType.Closed);
    assertEquals(interval.upperBoundType(), BoundType.Open);
  }

  @Test
  public void testToString() {
    Interval<Integer> interval1 = Intervals.open(1, 2);
    Interval<Integer> interval2 = Intervals.openClosed(1, 2);
    Interval<Integer> interval3 = Intervals.closedOpen(1, 2);
    Interval<Integer> interval4 = Intervals.closed(1, 2);
    Interval<Integer> empty = Intervals.empty();
    Interval<Integer> singleton = Intervals.singleton(1);

    assertEquals(interval1.toString(), "(1, 2)");
    assertEquals(interval2.toString(), "(1, 2]");
    assertEquals(interval3.toString(), "[1, 2)");
    assertEquals(interval4.toString(), "[1, 2]");
    assertEquals(empty.toString(), "Empty");
    assertEquals(singleton.toString(), "[1, 1]");
  }

}