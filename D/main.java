import java.util.Comparator;

public class main {

  public static void main(String[] args) {
    Interval<Integer> interval1 = Intervals.open(0, 5);
    Interval<Integer> interval2 = Intervals.open(5, 10);
    Interval<Integer> interval3 = interval1.intersection(interval2);
    System.out.print(interval3.lowerBound());
    System.out.print(interval3.upperBound());
    ComparableInterval<Integer>
        i =
        new ComparableInterval<>(1, BoundType.Closed, 4, BoundType.Open);
    Comparator<Integer> x = i.getComparator();
  }
}
