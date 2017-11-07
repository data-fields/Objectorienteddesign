import org.junit.Test;

import static org.junit.Assert.*;

public class BookTest {
    Publication rushdie = new Book("1Q84", "Haruki Murakami",
                                   "Shinchosha", "Japan", 2009);

    @Test
    public void testCiteMla() {
        assertEquals("Haruki Murakami. 1Q84. Japan: "
                       + "Shinchosha, 2009.",
                     rushdie.citeMla());
    }

    @Test
    public void testCiteApa() {
        assertEquals("Haruki Murakami (2009). 1Q84. "
                       + "Japan: Shinchosha.",
                     rushdie.citeApa());
    }
}
