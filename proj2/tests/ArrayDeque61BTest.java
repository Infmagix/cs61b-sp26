import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BTest {

    @Test
    public void testToList() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();

        // empty deque
        assertThat(lld.toList()).containsExactly();

        // valid deque
        lld.addFirst(23);
        lld.addFirst(22);
        lld.addLast(54); // [22, 23, 54]

        assertThat(lld.toList()).containsExactly(22, 23, 54).inOrder();

        // test for resizing array
        lld.addLast(1); // [22, 23, 54, 1]
        lld.addLast(2); // [22, 23, 54, 1, 2]
        lld.addLast(3); // [22, 23, 54, 1, 2, 3]
        lld.addLast(4); // [22, 23, 54, 1, 2, 3, 4]
        lld.addLast(5); // [22, 23, 54, 1, 2, 3, 4, 5]
        lld.addLast(6); // [22, 23, 54, 1, 2, 3, 4, 5, 6]

        assertThat(lld.toList()).containsExactly(22, 23, 54, 1, 2, 3, 4, 5, 6).inOrder();
    }

    @Test
    public void testAddFirst() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();

        lld.addFirst(23); // [23]
        lld.addFirst(22); // [22, 23]
        lld.addFirst(24); // [24, 22, 23]

        assertThat(lld.toList()).containsExactly(24, 22, 23).inOrder();

        // resize backing array of deque
        lld.addFirst(1); // [1, 24, 22, 23]
        lld.addFirst(2); // [2, 1, 24, 22, 23]
        lld.addFirst(3); // [3, 2, 1, 24, 22, 23]
        lld.addFirst(4); // [4, 3, 2, 1, 24, 22, 23]
        lld.addFirst(5); // [5, 4, 3, 2, 1, 24, 22, 23]
        lld.addFirst(6); // [6, 5, 4, 3, 2, 1, 24, 22, 23]

        assertThat(lld.toList()).containsExactly(6, 5, 4, 3, 2, 1, 24, 22, 23).inOrder();
    }

    @Test
    public void testAddLast() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();

        lld.addLast(23); // [23]
        lld.addLast(22); // [23, 22]
        lld.addLast(24); // [23, 22, 24]

        assertThat(lld.toList()).containsExactly(23, 22, 24).inOrder();

        // resize backing array of deque
        lld.addLast(1); // [23, 22, 24, 1]
        lld.addLast(2); // [23, 22, 24, 1, 2]
        lld.addLast(3); // [23, 22, 24, 1, 2, 3]
        lld.addLast(4); // [23, 22, 24, 1, 2, 3, 4]
        lld.addLast(5); // [23, 22, 24, 1, 2, 3, 4, 5]
        lld.addLast(6); // [23, 22, 24, 1, 2, 3, 4, 5, 6]

        assertThat(lld.toList()).containsExactly(23, 22, 24, 1, 2, 3, 4, 5, 6).inOrder();
    }

    @Test
    public void testIsEmpty() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();

        // empty deque
        assertThat(lld.isEmpty()).isTrue();

        // valid deque
        lld.addLast(23); // [23]
        lld.addLast(22); // [23, 22]
        lld.addLast(24); // [23, 22, 24]

        assertThat(lld.isEmpty()).isFalse();

        // resize backing array of deque
        lld.addLast(1); // [23, 22, 24, 1]
        lld.addLast(2); // [23, 22, 24, 1, 2]
        lld.addLast(3); // [23, 22, 24, 1, 2, 3]
        lld.addLast(4); // [23, 22, 24, 1, 2, 3, 4]
        lld.addLast(5); // [23, 22, 24, 1, 2, 3, 4, 5]
        lld.addLast(6); // [23, 22, 24, 1, 2, 3, 4, 5, 6]

        assertThat(lld.isEmpty()).isFalse(); // result determined by different resize strategy
    }

    @Test
    public void testSize() {
        Deque61B<Integer> lld = new ArrayDeque61B<>();

        // empty deque
        assertThat(lld.size()).isEqualTo(0);

        // valid deque
        lld.addLast(23); // [23]
        lld.addLast(22); // [23, 22]
        lld.addLast(24); // [23, 22, 24]

        assertThat(lld.size()).isEqualTo(3);

        // resize backing array of deque
        lld.addLast(1); // [23, 22, 24, 1]
        lld.addLast(2); // [23, 22, 24, 1, 2]
        lld.addLast(3); // [23, 22, 24, 1, 2, 3]
        lld.addLast(4); // [23, 22, 24, 1, 2, 3, 4]
        lld.addLast(5); // [23, 22, 24, 1, 2, 3, 4, 5]
        lld.addLast(6); // [23, 22, 24, 1, 2, 3, 4, 5, 6]

        assertThat(lld.size()).isEqualTo(9);
    }
}
