import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/** Performs some basic linked list tests. */
public class LinkedListDeque61BTest {

     @Test
     /** In this test, we have three different assert statements that verify that addFirst works correctly. */
     public void addFirstTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addFirst("back"); // after this call we expect: ["back"]
         assertThat(lld1.toList()).containsExactly("back").inOrder();

         lld1.addFirst("middle"); // after this call we expect: ["middle", "back"]
         assertThat(lld1.toList()).containsExactly("middle", "back").inOrder();

         lld1.addFirst("front"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         /* Note: The first two assertThat statements aren't really necessary. For example, it's hard
            to imagine a bug in your code that would lead to ["front"] and ["front", "middle"] failing,
            but not ["front", "middle", "back"].
          */

         lld1.removeFirst();
         lld1.removeFirst();
         lld1.removeFirst();

         lld1.addFirst("add_first_after_remove_to_empty");
         assertThat(lld1.toList()).containsExactly("add_first_after_remove_to_empty");
     }

     @Test
     /** In this test, we use only one assertThat statement. IMO this test is just as good as addFirstTestBasic.
      *  In other words, the tedious work of adding the extra assertThat statements isn't worth it. */
     public void addLastTestBasic() {
         Deque61B<String> lld1 = new LinkedListDeque61B<>();

         lld1.addLast("front"); // after this call we expect: ["front"]
         lld1.addLast("middle"); // after this call we expect: ["front", "middle"]
         lld1.addLast("back"); // after this call we expect: ["front", "middle", "back"]
         assertThat(lld1.toList()).containsExactly("front", "middle", "back").inOrder();

         lld1.removeFirst();
         lld1.removeFirst();
         lld1.removeFirst();

         lld1.addLast("add_last_after_remove_to_empty");
         assertThat(lld1.toList()).containsExactly("add_last_after_remove_to_empty");
     }

     @Test
     /** This test performs interspersed addFirst and addLast calls. */
     public void addFirstAndAddLastTest() {
         Deque61B<Integer> lld1 = new LinkedListDeque61B<>();

         /* I've decided to add in comments the state after each call for the convenience of the
            person reading this test. Some programmers might consider this excessively verbose. */
         lld1.addLast(0);   // [0]
         lld1.addLast(1);   // [0, 1]
         lld1.addFirst(-1); // [-1, 0, 1]
         lld1.addLast(2);   // [-1, 0, 1, 2]
         lld1.addFirst(-2); // [-2, -1, 0, 1, 2]

         assertThat(lld1.toList()).containsExactly(-2, -1, 0, 1, 2).inOrder();
     }

    // Below, you'll write your own tests for LinkedListDeque61B.

    @Test
    public void testToList() {
        Deque61B<String> lld = new LinkedListDeque61B<>();

        /** Check that toList works with empty LinkedListDeque61B. */
        assertThat(lld.toList()).containsExactly();

        /** Check that toList works with non-empty LinkedListDeque61B. */
        lld.addFirst("Hello");
        lld.addLast("world");
        assertThat(lld.toList()).containsExactly("Hello", "world").inOrder();
    }

    @Test
    public void testIsEmpty() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        /** Check that isEmpty works on an empty deque. */
        assertThat(lld.isEmpty()).isTrue();

        /** Check that isEmpty works on an non-empty deque. */
        lld.addLast(0);   // [0]
        lld.addLast(1);   // [0, 1]
        lld.addFirst(-1); // [-1, 0, 1]
        assertThat(lld.isEmpty()).isFalse();
    }

    @Test
    public void testSize() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        /** Check that size works. */
        assertThat(lld.size()).isEqualTo(0);

        /** Add some elements to a deque and remvoe them all,
         * then check that size still works. */
        lld.addLast(0);   // [0]
        lld.addLast(1);   // [0, 1]
        lld.addFirst(-1); // [-1, 0, 1]
        assertThat(lld.size()).isEqualTo(3);

        lld.removeFirst();
        lld.removeFirst();
        lld.removeFirst();
        assertThat(lld.size()).isEqualTo(0);

        /** Remove from an empty deque, then check that size still works. */
        lld.removeFirst();
        assertThat(lld.size()).isEqualTo(0);
    }

    @Test
    public void testGetFirst() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        /** Check that getFirst works on an empty deque. */
        assertThat(lld.getFirst()).isNull();

        /** Check that getFirst works on a valid deque. */
        lld.addLast(0);   // [0]
        assertThat(lld.getFirst()).isEqualTo(0);

        lld.addLast(1);   // [0, 1]
        assertThat(lld.getFirst()).isEqualTo(0);

        lld.addFirst(-1); // [-1, 0, 1]
        assertThat(lld.getFirst()).isEqualTo(-1);
    }

    @Test
    public void testGetLast() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        /** Check that getLast works on an empty deque. */
        assertThat(lld.getLast()).isNull();

        /** Check that getLast works on a valid deque. */
        lld.addLast(0);   // [0]
        assertThat(lld.getLast()).isEqualTo(0);

        lld.addLast(1);   // [0, 1]
        assertThat(lld.getLast()).isEqualTo(1);

        lld.addFirst(-1); // [-1, 0, 1]
        assertThat(lld.getLast()).isEqualTo(1);
    }

    @Test
    public void testGet() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        lld.addLast(0);   // [0]
        assertThat(lld.get(0)).isEqualTo(0); // Check that get works on a valid index.
        assertThat(lld.get(-1)).isNull(); // Check that get works on a negative index.
        assertThat(lld.get(10)).isNull(); // Check that get works on a large, out of bounds index.
    }

    @Test
    public void testGetRecursive() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        lld.addLast(0);   // [0]
        assertThat(lld.getRecursive(0)).isEqualTo(0);
        assertThat(lld.getRecursive(-1)).isNull();
        assertThat(lld.getRecursive(10)).isNull();
    }

    @Test
    public void testRemoveFirst() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        /** Check that removeFirst works. */
        Integer item = lld.removeFirst();
        assertThat(item).isNull();
        assertThat(lld.toList()).containsExactly();

        /** Add some elements to a deque and remove almost all of them.
         *  Check that removing the last element with removeFirst works.
         *  And check that removing the second to last element with removeFirst works. */
        lld.addLast(0);   // [0]
        lld.addLast(1);   // [0, 1]
        lld.addFirst(-1); // [-1, 0, 1]

        item = lld.removeFirst(); // [0, 1]
        assertThat(item).isEqualTo(-1);
        assertThat(lld.toList()).containsExactly(0, 1).inOrder();

        item = lld.removeFirst(); // [1]
        assertThat(item).isEqualTo(0);
        assertThat(lld.toList()).containsExactly(1);

        item = lld.removeFirst(); // []
        assertThat(item).isEqualTo(1);
        assertThat(lld.toList()).containsExactly();
    }

    @Test
    public void testRemoveLast() {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();

        /** Check that removeLast works. */
        Integer item = lld.removeLast();
        assertThat(item).isNull();
        assertThat(lld.toList()).containsExactly();

        /** Add some elements to a deque and remove almost all of them.
         *  Check that removing the last element with removeLast works.
         *  And check that removing the second to last element with removeLast works. */
        lld.addLast(0);   // [0]
        lld.addLast(1);   // [0, 1]
        lld.addFirst(-1); // [-1, 0, 1]

        item = lld.removeLast(); // [-1, 0]
        assertThat(item).isEqualTo(1);
        assertThat(lld.toList()).containsExactly(-1, 0).inOrder();

        item = lld.removeLast(); // [-1]
        assertThat(item).isEqualTo(0);
        assertThat(lld.toList()).containsExactly(-1);

        item = lld.removeLast(); // []
        assertThat(item).isEqualTo(-1);
        assertThat(lld.toList()).containsExactly();
    }
}