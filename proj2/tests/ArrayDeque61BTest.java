import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {

    @Test
    public void testToList() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        // to_list_empty: Check that toList works with empty ArrayDeque.
        assertThat(ad.toList()).containsExactly();

        // to_list_nonempty: Check that toList works with non-empty ArrayDeque.
        ad.addFirst(23);
        ad.addFirst(22);
        ad.addLast(54); // [22, 23, 54]
        assertThat(ad.toList()).containsExactly(22, 23, 54).inOrder();
    }

    @Test
    public void testAddFirst() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        // add_first_from_empty: Check that addFirst works on an empty deque.
        ad.addFirst(23); // [23]
        assertThat(ad.toList()).containsExactly(23);

        // add_first_nonempty: Check that addFirst works on a non-empty deque.
        ad.addFirst(22); // [22, 23]
        ad.addFirst(24); // [24, 22, 23]
        assertThat(ad.toList()).containsExactly(24, 22, 23).inOrder();

        // add_first_trigger_resize: Check that addFirst works when called on a full underlying array
        ad.addFirst(1); // [1, 24, 22, 23]
        ad.addFirst(2); // [2, 1, 24, 22, 23]
        ad.addFirst(3); // [3, 2, 1, 24, 22, 23]
        ad.addFirst(4); // [4, 3, 2, 1, 24, 22, 23]
        ad.addFirst(5); // [5, 4, 3, 2, 1, 24, 22, 23]
        ad.addFirst(6); // [6, 5, 4, 3, 2, 1, 24, 22, 23]
        assertThat(ad.toList()).containsExactly(6, 5, 4, 3, 2, 1, 24, 22, 23).inOrder();

        // add_first_after_remove_to_empty: Add some elements to a deque and remove them all, then check that addFirst still works.
        for (int i = 0; i < 4; i++) ad.removeFirst();
        for (int i = 0; i < 5; i++) ad.removeLast();
        ad.addFirst(23);
        assertThat(ad.toList()).containsExactly(23);
    }

    @Test
    public void testAddLast() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        // add_last_from_empty: Check that addLast works on an empty deque.
        ad.addLast(23); // [23]
        assertThat(ad.toList()).containsExactly(23);

        // add_last_nonempty: Check that addLast works on a non-empty deque.
        ad.addLast(22); // [23, 22]
        ad.addLast(24); // [23, 22, 24]
        assertThat(ad.toList()).containsExactly(23, 22, 24).inOrder();

        // add_last_trigger_resize: Check that addLast works when called on a full underlying array
        for (int i = 1; i < 7; i++) ad.addLast(i); // [23, 22, 24, 1, 2, 3, 4, 5, 6]
        assertThat(ad.toList()).containsExactly(23, 22, 24, 1, 2, 3, 4, 5, 6).inOrder();

        // add_last_after_remove_to_empty: Add some elements to a deque and remove them all, then check that addLast still works.
        for (int i = 0; i < 4; i++) ad.removeFirst();
        for (int i = 0; i < 5; i++) ad.removeLast();
        ad.addLast(23);
        assertThat(ad.toList()).containsExactly(23);
    }

    @Test
    public void testIsEmpty() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        // is_empty_true: Check that size works on an empty deque.
        assertThat(ad.isEmpty()).isTrue();

        // is_empty_false: Check that size works on a non-empty deque.
        ad.addLast(23); // [23]
        ad.addLast(22); // [23, 22]
        ad.addLast(24); // [23, 22, 24]
        assertThat(ad.isEmpty()).isFalse();
    }

    @Test
    public void testSize() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        // size: Check that size works.
        assertThat(ad.size()).isEqualTo(0);

        ad.addLast(23); // [23]
        ad.addLast(22); // [23, 22]
        ad.addLast(24); // [23, 22, 24]
        assertThat(ad.size()).isEqualTo(3);

        // size_after_remove_to_empty: Add some elements to a deque and remove them all,
        // then check that size still works.
        ad.addLast(1); // [23, 22, 24, 1]
        ad.addLast(2); // [23, 22, 24, 1, 2]
        ad.addLast(3); // [23, 22, 24, 1, 2, 3]
        ad.addLast(4); // [23, 22, 24, 1, 2, 3, 4]
        ad.addLast(5); // [23, 22, 24, 1, 2, 3, 4, 5]
        ad.addLast(6); // [23, 22, 24, 1, 2, 3, 4, 5, 6]
        assertThat(ad.size()).isEqualTo(9);

        for (int i = 0; i < 4; i++) ad.removeFirst();
        for (int i = 0; i < 5; i++) ad.removeLast();
        assertThat(ad.size()).isEqualTo(0);

        // size_after_remove_from_empty: Remove from an empty deque,
        // then check that size still works.
        ad.removeLast();
        assertThat(ad.size()).isEqualTo(0);
    }

    @Test
    public void testGetFirst() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        // get_first_empty: Check that getFirst works on an empty ArrayDeque.
        assertThat(ad.getFirst()).isNull();

        // get_first_valid: Check that getFirst works on a nonempty ArrayDeque.
        ad.addFirst(22); // [22]
        assertThat(ad.getFirst()).isEqualTo(22);

        ad.addFirst(23); // [23, 22]
        assertThat(ad.getFirst()).isEqualTo(23);
    }

    @Test
    public void testGetLast() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        // get_last_empty: Check that getLast works on an empty ArrayDeque.
        assertThat(ad.getLast()).isNull();

        // get_last_valid: Check that getLast works on a nonempty ArrayDeque.
        ad.addFirst(22); // [22]
        assertThat(ad.getLast()).isEqualTo(22);

        ad.addFirst(23); // [23, 22]
        assertThat(ad.getLast()).isEqualTo(22);
    }

    @Test
    public void testGet() {
        Deque61B<Integer> ad = new ArrayDeque61B<>();

        ad.addFirst(22); // [22]
        ad.addFirst(23); // [23, 22]

        // get_valid: Check that get works on a valid index.
        assertThat(ad.get(0)).isEqualTo(23);

        // get_oob_neg: Check that get works on a negative index.
        assertThat(ad.get(-1)).isNull(); // negative index

        // get_oob_large: Check that get works on a large, out of bounds index.
        assertThat(ad.get(2)).isNull(); // larger index
    }

    @Test
    public void testRemoveFirst() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();

        // remove_first: Check that removeFirst works.
        assertThat(ad.removeFirst()).isNull();

        // remove_first_to_one: Add some elements to a deque and remove almost all of them.
        // Check that removing the second to last element with removeFirst works.
        ad.addFirst(22); // [22]
        ad.addFirst(23); // [23, 22]
        ad.addLast(24);  // [23, 22, 24]
        assertThat(ad.removeFirst()).isEqualTo(23); // [22, 24]
        assertThat(ad.removeFirst()).isEqualTo(22); // [24]

        // remove_first_to_empty: Add some elements to a deque and remove almost all of them.
        // Check that removing the last element with removeFirst works.
        assertThat(ad.removeFirst()).isEqualTo(24); // []

        // remove_first_trigger_resize: Called when usage factor is <= 25% and array size > 8.
        // Checks that the array resizes appropriately.
        for (int i = 0; i < 16; i++) ad.addFirst(i);
        for (int i = 0; i < 13; i++) ad.removeFirst();
        assertThat(ad.size()).isEqualTo(3);
        assertThat(ad.getFirst()).isEqualTo(2);
        assertThat(ad.getLast()).isEqualTo(0);
    }

    @Test
    public void testRemoveLast() {
        ArrayDeque61B<Integer> ad = new ArrayDeque61B<>();

        // remove_last: Check that removeLast works.
        assertThat(ad.removeLast()).isNull();

        // remove_last_to_one: Add some elements to a deque and remove almost all of them.
        // Check that removing the second to last element with removeLast works.
        ad.addFirst(22); // [22]
        ad.addFirst(23); // [23, 22]
        ad.addLast(24);  // [23, 22, 24]
        assertThat(ad.removeLast()).isEqualTo(24); // [23, 22]
        assertThat(ad.removeLast()).isEqualTo(22); // [23]

        // remove_last_to_empty: Add some elements to a deque and remove almost all of them.
        // Check that removing the last element with removeLast works.
        assertThat(ad.removeLast()).isEqualTo(23); // []

        // remove_last_trigger_resize: Called when usage factor is <= 25% and array size > 8.
        // Checks that the array resizes appropriately.
        for (int i = 0; i < 16; i++) ad.addFirst(i);
        for (int i = 0; i < 13; i++) ad.removeLast();
        assertThat(ad.size()).isEqualTo(3);
        assertThat(ad.getFirst()).isEqualTo(15);
        assertThat(ad.getLast()).isEqualTo(13);
    }

    // resize_up_and_resize_down: Trigger a resize up and then a resize down in the same test.
    @Test
    public void testResizeUpAndDown() {
        Deque61B<Integer> ad = new ArrayDeque61B();

        for (int i = 0; i < 10000; i++) ad.addLast(i);
        assertThat(ad.size()).isEqualTo(10000);
        assertThat(ad.getFirst()).isEqualTo(0);
        assertThat(ad.getLast()).isEqualTo(9999);

        for (int i = 0; i < 9900; i++) ad.removeLast();
        assertThat(ad.size()).isEqualTo(100);
        assertThat(ad.getFirst()).isEqualTo(0);
        assertThat(ad.getLast()).isEqualTo(99);

        for (int i = 0; i < 99; i++) ad.removeLast();
        assertThat(ad.size()).isEqualTo(1);
        assertThat(ad.getFirst()).isEqualTo(0);
        assertThat(ad.getLast()).isEqualTo(0);
    }
}
