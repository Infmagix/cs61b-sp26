import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private class Node {
        public T item;
        public Node prev;
        public Node next;
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque61B() {
        sentinel = new Node();
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        Node p = new Node();
        p.item = x;
        p.prev = sentinel;
        p.next = sentinel.next;

        sentinel.next.prev = p;
        sentinel.next = p;

        size++;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        Node p = new Node();
        p.item = x;
        p.prev = sentinel.prev;
        p.next = sentinel;

        sentinel.prev.next = p;
        sentinel.prev = p;

        size++;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();

        Node p = sentinel.next;
        while (p != sentinel) {
            returnList.add(p.item);
            p = p.next;
        }

        return returnList;
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() { return size == 0; }

    /**
     * Returns the size of the deque. Does not alter the deque.
     *
     * @return the number of items in the deque.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Return the element at the front of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getFirst() {
        if (isEmpty()) return null;
        return sentinel.next.item;
    }

    /**
     * Return the element at the back of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getLast() {
        if (isEmpty()) return null;
        return sentinel.prev.item;
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) return null;

        Node head = sentinel.next;
        T returnItem = head.item;

        sentinel.next = head.next;
        head.next.prev = sentinel;

        head.prev = head.next = null;
        size--;

        return returnItem;
    }

    /**
     * Remove and return the element at the back of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeLast() {
        if (isEmpty()) return null;

        Node tail = sentinel.prev;
        T returnItem = tail.item;

        sentinel.prev = tail.prev;
        tail.prev.next = sentinel;

        tail.prev = tail.next = null;
        size--;

        return returnItem;
    }

    /**
     * The Deque61B abstract data type does not typically have a get method,
     * but we've included this extra operation to provide you with some
     * extra programming practice. Gets the element, iteratively. Returns
     * null if index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T get(int index) {
        if (0 > index || index >= size)
            return null;

        Node p = sentinel;
        while (index > 0) {
            p = p.next;
            index--;
        }
        return p.next.item;
    }

    /**
     * This method technically shouldn't be in the interface, but it's here
     * to make testing nice. Gets an element, recursively. Returns null if
     * index is out of bounds. Does not alter the deque.
     *
     * @param index index to get
     * @return element at {@code index} in the deque
     */
    @Override
    public T getRecursive(int index) {
        if (0 > index || index >= size)
            return null;

        return getRecursiveHelper(sentinel.next, index);
    }

    private T getRecursiveHelper(Node p, int index) {
        if (0 == index)
            return p.item;

        return getRecursiveHelper(p.next, index - 1);
    }

    /*
     * =================================================================
     * 04. DLLists, Default Methods, ALists: Discussion
     * =================================================================
     */

    /** Rotates the Deque left by x places. Assume x is non-negative.
     *  Example: [3, 6, 9, 12, 15, 18].rotateLeft(4) yields [15, 18, 3, 6, 9, 12].
     */
    public void rotateLeft(int x) {
        // part c: Do not call any other methods. Each rotation
        // operation should set six pointers.
        // note: this implementation must have either if (size <= 1)
        // OR must mode x by size, otherwise there is a subtle bug.
//        if (size <= 1) {
//            return;
//        }

//        for (int i = 0; i < x; i++) {
//            Node oldLast = sentinel.prev;
//            Node oldFirst = sentinel.next;
//            Node oldSecond = oldFirst.next;
//
//            sentinel.next = oldSecond;
//            oldSecond.prev = sentinel;
//
//            oldFirst.next = sentinel;
//            oldFirst.prev = oldLast;
//
//            sentinel.prev = oldFirst;
//            oldLast.next = oldFirst;
//        }

//        Node p = sentinel.next;
//        while (x-- > 0) {
//            p = p.next;
//        }
//
//        Node oldHead = sentinel.next;
//        Node oldTail = sentinel.prev;
//
//        sentinel.next = p;
//        sentinel.prev = p.prev;
//        p.prev.next = sentinel;
//        p.prev = sentinel;
//
//        oldHead.prev = oldTail;
//        oldTail.next = oldHead;

        // part d: Rewrite this method in terms of other methods.
        // note: we can also mod x by size for efficiency
//        for (int i = 0; i < x % size; i++) {
//            addLast(removeFirst());
//        }

        while (x > 0) {
            addLast(removeFirst());
            x--;
        }
    }

    public static void main(String[] args) {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addLast(0);  // [0]
        lld.addLast(1);  // [0, 1]
        lld.addFirst(-1); // [-1, 0, 1]
    }
}
