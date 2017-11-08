import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ClockPolicyTest {

  ReplacementPolicy<Integer> policy = new ClockPolicy<>(5);

  /**
   * Sets up the test environment before each test case. Thus, each test case begins with the cache
   * {@code ^1+ 2+ 3+ 4+ _} (where the pluses indicate that reference bits are set and {@code ^}
   * marks the position of the clock hand).
   */
  @Before
  public void setUp() {
    policy.require(1);    // ^1+ _  _  _  _
    policy.require(2);    // ^1+ 2+ _  _  _
    policy.require(3);    // ^1+ 2+ 3+ _  _
    policy.require(4);    // ^1+ 2+ 3+ 4+ _
  }

  @Test
  public void testCapacity() {
    assertEquals(5, policy.capacity());
  }

  @Test
  public void testSize() {
    assertEquals(4, policy.size());
  }

  @Test
  public void testSize_afterRef() {
    policy.require(1);                  // ^1+ 2+ 3+ 4+ _
    assertEquals(4, policy.size());
  }

  @Test
  public void testSize_afterAdd() {
    policy.require(5);                  // ^1+ 2+ 3+ 4+ 5+
    assertEquals(5, policy.size());
  }

  @Test
  public void testSize_afterAdd2() {
    policy.require(5);                  // ^1+ 2+ 3+ 4+ 5+
    policy.require(6);                  //  6+^2  3  4  5
    assertEquals(5, policy.size());
  }

  @Test
  public void testRequire_ref() {
    assertNull(policy.require(1));
  }

  @Test
  public void testRequire_add() {
    assertNull(policy.require(5));      // ^1+ 2+ 3+ 4+ 5+
  }

  @Test
  public void testRequire_addRef() {
    policy.require(5);
    assertNull(policy.require(1));
  }

  @Test
  public void testRequire_addAdd() {
    policy.require(5);                            // ^1+ 2+ 3+ 4+ 5+
    assertEquals((Integer) 1, policy.require(6)); //  6+^2  3  4  5
  }

  @Test
  public void testRequire_refRefAddAdd() {
    policy.require(1);                            // ^1+ 2+ 3+ 4+ _
    policy.require(2);                            // ^1+ 2+ 3+ 4+ _
    policy.require(5);                            // ^1+ 2+ 3+ 4+ 5+
    assertEquals((Integer) 1, policy.require(6)); //  6+^2  3  4  5
  }

  @Test
  public void testRequire_long() {
    policy.require(5);                            // ^1+ 2+ 3+ 4+ 5+
    assertEquals((Integer) 1, policy.require(6)); //  6+^2  3  4  5
    assertNull(policy.require(5));                //  6+^2  3  4  5+
    assertEquals((Integer) 2, policy.require(1)); //  6+ 1+^3  4  5+
    assertNull(policy.require(4));                //  6+ 1+^3  4+ 5+
    assertNull(policy.require(5));                //  6+ 1+^3  4+ 5+
    assertEquals((Integer) 3, policy.require(7)); //  6+ 1+ 7+^4+ 5+
    assertNull(policy.require(4));                //  6+ 1+ 7+^4+ 5+
    assertEquals((Integer) 4, policy.require(3)); //  6  1  7  3+^5
    assertNull(policy.require(5));                //  6  1  7  3+^5+
    assertNull(policy.require(1));                //  6  1+ 7  3+^5+
    assertEquals((Integer) 6, policy.require(2)); //  2+^1+ 7  3+ 5+
    assertEquals((Integer) 7, policy.require(4)); //  2+ 1  4+^3+ 5+
  }
}