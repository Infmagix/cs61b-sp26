import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

public class ArrayDeque61BEnhancementTest {
    @Test
    public void testRemoveFirstResizeDown() {
        ArrayDeque61B<Integer> lld = new ArrayDeque61B<>();

        for (int i = 0; i < 16; i++) lld.addFirst(i); // cap: 8 -> 16
        assertThat(lld.capacity()).isEqualTo(16);

        // size=4 => cap: max(8,15)=15 and then removeFirst => size=3
        for (int i = 0; i < 13; i++) lld.removeFirst();
        assertThat(lld.size()).isEqualTo(3);
        assertThat(lld.capacity()).isEqualTo(15);
    }

    @Test
    public void testRemoveLastResizeDown() {
        ArrayDeque61B<Integer> lld = new ArrayDeque61B<>();

        for (int i = 0; i < 16; i++) lld.addFirst(i);
        assertThat(lld.capacity()).isEqualTo(16);

        for (int i = 0; i < 13; i++) lld.removeLast();
        assertThat(lld.size()).isEqualTo(3);
        assertThat(lld.capacity()).isEqualTo(15);
    }

    @Test
    public void testResizeUpAndResizeDown() {
        ArrayDeque61B<Integer> lld = new ArrayDeque61B();

        for (int i = 0; i < 10000; i++) lld.addLast(i);
        assertThat(lld.capacity()).isEqualTo(16384);

        // cap halving：16384 -> 8192 -> 4096 -> ... -> 256 (size=100)
        for (int i = 0; i < 9900; i++) lld.removeLast();
        assertThat(lld.capacity()).isEqualTo(256);

        // size=16: szie=32 => cap: 128/2=64
        for (int i = 0; i < 84; i++) lld.removeLast();
        assertThat(lld.capacity()).isEqualTo(64);

        // size=1：cap: 16 -> 32 -> 16 -> 15 (size=4 => cap: max(8,15)=15)
        for (int i = 0; i < 15; i++) lld.removeLast();
        assertThat(lld.capacity()).isEqualTo(15);
    }

    @Test
    public void testAddAfterShrinkToEmpty() {
        ArrayDeque61B<Integer> lld = new ArrayDeque61B();

        for (int i = 0; i < 20; i++) lld.addLast(i);   // resize up to 32
        for (int i = 0; i < 20; i++) lld.removeFirst(); // remove to empty
        assertThat(lld.size()).isEqualTo(0);
        assertThat(lld.isEmpty()).isTrue();
        // Resizing down triggers in removal to empty: cap <= 32 && >= 15
        assertThat(lld.capacity()).isAtMost(32);
        assertThat(lld.capacity()).isAtLeast(15);

        lld.addFirst(1);
        lld.addLast(2);
        assertThat(lld.toList()).containsExactly(1, 2).inOrder();
    }

    @Test
    public void testIterator() {
        Deque61B<String> ad = new ArrayDeque61B<>();

        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");
        for (String s : ad) {
            System.out.println(s);
        }
    }

    @Test
    public void addLastTestBasicWithoutToList() {
        Deque61B<String> ad = new ArrayDeque61B<>();

        ad.addLast("front"); // after this call we expect: ["front"]
        ad.addLast("middle"); // after this call we expect: ["front", "middle"]
        ad.addLast("back"); // after this call we expect: ["front", "middle", "back"]
        assertThat(ad).containsExactly("front", "middle", "back");
    }

    @Test
    public void testEqualsDeque61B() {
        Deque61B<String> ad = new ArrayDeque61B<>();
        Deque61B<String> ad2 = new ArrayDeque61B<>();

        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");
        ad.addLast(null);

        ad2.addLast("front");
        ad2.addLast("middle");
        ad2.addLast("back");
        ad2.addLast(null);

        assertThat(ad).isEqualTo(ad2);
    }

    @Test
    public void testToStringDeque61B() {
        Deque61B<String> ad = new ArrayDeque61B<>();

        // to_string_empty: Check that toString works on an empty deque.
        assertThat(ad.toString()).isEqualTo("[]");

        // to_string_nonempty: Check that toString works on a non-empty deque.
        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");
        assertThat(ad.toString()).isEqualTo("[front, middle, back]");
    }
}
