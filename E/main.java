
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class main {

  public static void main(String[] args) {
    List<Integer> l = new LinkedList<>();
    for (int i = 0; i < 10; i++) {
      l.add(i);
    }
    ListIterator<Integer> itr = l.listIterator();
    while (itr.hasNext()) {
      Integer temp;
      temp = itr.next();
      if (temp.equals(5)) {
        itr.remove();
      }
    }
    itr = l.listIterator();
    while (itr.hasNext()) {
      System.out.println(itr.next());
    }
  }
}
