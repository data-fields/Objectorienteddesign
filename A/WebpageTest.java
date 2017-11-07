import static org.junit.Assert.*;

import org.junit.Test;

public class WebpageTest {
  Webpage cs3500 = new Webpage("CS3500: Object-Oriented Design",
      "http://www.ccs.neu.edu/home/tov/course/cs3500/",
      "August 11, 2014");

  @Test
  public void testCiteApa() {
    assertEquals("CS3500: Object-Oriented Design. Retrieved "
        + "August 11, 2014, "
        + "from http://www.ccs.neu.edu/home/tov/course/cs3500/.",
        cs3500.citeApa());
  }

  @Test
  public void testCiteMla() {
    assertEquals("\"CS3500: Object-Oriented Design.\" Web. "
        + "August 11, 2014 "
        + "<~http://www.ccs.neu.edu/home/tov/course/cs3500/>.",
        cs3500.citeMla());
  }
}