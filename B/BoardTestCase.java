import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;

public class BoardTest {

  @Test
  public void testGameOver() throws Exception {
    Board b = new Board();
    assertEquals(b.gameOver(), false);
  }


  @Test
  public void testSetDisplayNormal() throws Exception {
    Board b = new Board();
    b.setDisplayNormal();
    assertEquals(b.get(0, 1, 5), "o");
    assertEquals(b.get(0, 2, 5), "     ");
    assertEquals(b.get(1, 1, 5), "     ");
    assertEquals(b.get(6, 3, 5), "+");
  }


  @Test(expected = IllegalStateException.class)
  public void testGetWinner() throws Exception {
    Board b = new Board();
    b.getWinner();
  }

  @Test
  public void testGet() throws Exception {
    Board b = new Board();
    b.setDisplayNormal();
    assertEquals(b.get(0, 1, 5), "o");
    assertEquals(b.get(0, 2, 5), "     ");
    assertEquals(b.get(1, 1, 5), "     ");
    assertEquals(b.get(6, 3, 5), "+");
  }

  @Test
  public void testIsValidPosition() throws Exception {
    Board b = new Board();
    assertEquals(b.isValidPosition(0, 0), false);
    assertEquals(b.isValidPosition(0, 1), true);
    assertEquals(b.isValidPosition(8, 8), false);
  }

  @Test
  public void testRows() throws Exception {
    Board b = new Board();
    Iterator<Integer> r = b.rows();
    int counter = 0;
    while (r.hasNext()) {
      assertEquals(r.next(), (Integer) counter);
      counter++;
    }
  }

  @Test
  public void testColumns() throws Exception {
    Board b = new Board();
    Iterator<Integer> c = b.columns();
    int counter = 0;
    while (c.hasNext()) {
      assertEquals(c.next(), (Integer) counter);
      counter++;
    }
  }
}