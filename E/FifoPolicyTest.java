import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class FifoPolicyTest {

  ReplacementPolicy<Integer> policy = new FifoPolicy<>(5);
  ReplacementPolicy<String> stringPolicy = new FifoPolicy<>(5);

  @Test
  public void test1() {
    policy.require(10);
    policy.require(5);
    assertEquals(policy.size(), 2);
    assertEquals(policy.capacity(), 5);
  }

  @Test
  public void oneItem() {
    ReplacementPolicy<Integer> policy = new FifoPolicy<>(1);
    assertEquals(policy.size(), 0);
    assertEquals(policy.capacity(), 1);
    assertEquals(policy.require(1), null);
    assertEquals(policy.require(2), (Integer) 1);
    assertEquals(policy.require(3), (Integer) 2);
    assertEquals(policy.require(4), (Integer) 3);
    assertEquals(policy.require(5), (Integer) 4);
  }

  @Test
  public void stringTest() {
    stringPolicy.require("brian");
    stringPolicy.require("crafton");
    assertEquals(stringPolicy.size(), 2);
    assertEquals(stringPolicy.capacity(), 5);
  }

  @Test(expected = Exception.class)
  public void negativeListSize() {
    ReplacementPolicy<Integer> p = new FifoPolicy<>(-1);
  }

  @Test(expected = Exception.class)
  public void zeroListSize() {
    ReplacementPolicy<Integer> p = new FifoPolicy<>(0);
  }

  @Test
  public void FIFOTest() {
    assertEquals(policy.size(), 0);
    assertEquals(policy.capacity(), 5);

    assertEquals(policy.require(1), null);
    assertEquals(policy.require(2), null);
    assertEquals(policy.require(3), null);
    assertEquals(policy.require(4), null);
    assertEquals(policy.require(5), null);

    assertEquals(policy.require(6), (Integer) 1);
    assertEquals(policy.require(7), (Integer) 2);
  }


  @Test
  public void cache_hit_test() {
    assertEquals(policy.require(1), null);
    assertEquals(policy.require(2), null);
    assertEquals(policy.require(3), null);
    assertEquals(policy.require(4), null);
    assertEquals(policy.require(5), null);

    assertEquals(policy.require(1), null);
    assertEquals(policy.require(2), null);
    assertEquals(policy.require(3), null);
    assertEquals(policy.require(4), null);
    assertEquals(policy.require(5), null);
  }

  @Test
  public void size_after_cache_hit() {
    assertEquals(policy.require(1), null);
    assertEquals(policy.require(1), null);

    assertEquals(policy.require(2), null);
    assertEquals(policy.require(3), null);
    assertEquals(policy.require(4), null);
    assertEquals(policy.require(5), null);

    assertEquals(policy.require(1), null);
    assertEquals(policy.require(2), null);
    assertEquals(policy.require(3), null);
    assertEquals(policy.require(4), null);
    assertEquals(policy.require(5), null);

    assertEquals(policy.size(), 5);
  }

  @Test
  public void testCapacity() {
    assertEquals(5, policy.capacity());
  }
  @Test
  public void testCapacityChange()
  {
    assertEquals(5, policy.capacity());

    policy.require(1);
    assertEquals(5, policy.capacity());

    policy.require(2);
    assertEquals(5, policy.capacity());

    policy.require(3);
    assertEquals(5, policy.capacity());

    policy.require(4);
    assertEquals(5, policy.capacity());

    policy.require(5);
    assertEquals(5, policy.capacity());
  }

  @Test
  public void testSize() {
    assertEquals(0, policy.size());

    policy.require(1);
    assertEquals(1, policy.size());

    policy.require(2);
    assertEquals(2, policy.size());

    policy.require(3);
    assertEquals(3, policy.size());

    policy.require(4);
    assertEquals(4, policy.size());

    policy.require(5);
    assertEquals(5, policy.size());
  }
  @Test
  public void testActualCapacity() {
    // this test is to make sure the policy does not store more elements then it is suppose to
    assertEquals(policy.size(), 0);
    assertEquals(policy.capacity(), 5);

    assertEquals(policy.require(1), null);
    assertEquals(policy.require(2), null);
    assertEquals(policy.require(3), null);
    assertEquals(policy.require(4), null);
    assertEquals(policy.require(5), null);

    assertEquals(policy.require(6), (Integer) 1);
    assertEquals(policy.require(7), (Integer) 2);
  }
  @Test
  public void FifoSize100()
  {
    ReplacementPolicy<Integer> p = new FifoPolicy<>(100);
    for(int i = 0; i < 100; i++) {
      p.require(i);
    }
    for(int i = 0; i < 100; i++) {
      assertEquals(p.require(i), null);
    }
    for(int i = 100; i<200; i++) {
      assertEquals(p.require(i), (Integer)(i-100));
    }
  }
}
