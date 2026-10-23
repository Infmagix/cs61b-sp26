import java.util.ArrayList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    private static class Node<T> {
        public T item;
        public Node<T> prev;
        public Node<T> next;
    }

    private Node<T> sentinel;
    private int size;

    public LinkedListDeque61B() {
        sentinel = new Node<>();
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
        Node<T> p = new Node<>();
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
        Node<T> p = new Node<>();
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

        Node<T> p = sentinel.next;
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

        Node<T> head = sentinel.next;
        T returnItem = head.item;

        sentinel.next = head.next;
        head.next.prev = sentinel;

        head.prev = head.next = null;

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

        Node<T> tail = sentinel.prev;
        T returnItem = tail.item;

        sentinel.prev = tail.prev;
        tail.prev.next = sentinel;

        tail.prev = tail.next = null;

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

        Node<T> p = sentinel;
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

    private T getRecursiveHelper(Node<T> p, int index) {
        if (0 == index)
            return p.item;

        return getRecursiveHelper(p.next, index - 1);
    }

    public static void main(String[] args) {
        Deque61B<Integer> lld = new LinkedListDeque61B<>();
        lld.addLast(0);  // [0]
        lld.addLast(1);  // [0, 1]
        lld.addFirst(-1); // [-1, 0, 1]
    }
}
