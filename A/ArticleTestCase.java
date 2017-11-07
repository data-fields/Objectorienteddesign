import org.junit.Test;

import static org.junit.Assert.*;

public class ArticleTest {
    Publication turing =
           new Article("Minds, brains, and programs",
                       "John R. Searle", "Behavioral and Brain Sciences", 3, 3, 1980);

    @Test
    public void testCiteApa() {
        assertEquals("John R. Searle (1980). Minds, brains,and "
                       + "programs. Behavioral and Brain Sciences, 3(3).",
                     turing.citeApa());
    }

    @Test
    public void testCiteMla() {
        assertEquals("John R. Searle  \"Minds, brains, and "
                       + "programs.\" Behavioral and Brain Sciences, 3(3).",
                     turing.citeMla());
    }
}