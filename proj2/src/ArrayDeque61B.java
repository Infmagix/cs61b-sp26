import java.util.ArrayList;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;
    }

    /**
     * Add {@code x} to the front of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addFirst(T x) {
        // full array triggers resizing
        if (size == capacity()) {
            resize();
        }

        items[nextFirst] = x;

        nextFirst = indexMod(nextFirst - 1);
        size++;
    }

    /**
     * Add {@code x} to the back of the deque. Assumes {@code x} is never null.
     *
     * @param x item to add
     */
    @Override
    public void addLast(T x) {
        if (size == capacity()) {
            resize();
        }
        
        items[nextLast] = x;

        nextLast = indexMod(nextLast + 1);
        size++;
    }

    private int capacity() {
        return items.length;
    }

    private void resize() {
        int currCap = capacity();
        T[] resized = (T[]) new Object[currCap * 2];

        int first = indexMod(nextFirst + 1);
        for (int i = 0; i < currCap; i++) {
            resized[i] = items[first];
            first = indexMod(first + 1);
        }

        items = resized;
        nextFirst = capacity() - 1;
        nextLast = currCap;
    }

    /**
     * Returns a List copy of the deque. Does not alter the deque.
     *
     * @return a new list copy of the deque.
     */
    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();

        if (size > 0) {
            int first = indexMod(nextFirst + 1);
            for (int i = 0; i < size; i++) {
                returnList.add(items[first]);
                first = indexMod(first + 1);
            }
        }

        return returnList;
    }

    private int indexMod(int index) {
        return (index + capacity()) % capacity();
    }

    /**
     * Returns if the deque is empty. Does not alter the deque.
     *
     * @return {@code true} if the deque has no elements, {@code false} otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

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
        if (isEmpty()) {
            return null;
        }
        return items[indexMod(nextFirst + 1)];
    }

    /**
     * Return the element at the back of the deque, if it exists.
     *
     * @return element, otherwise {@code null}.
     */
    @Override
    public T getLast() {
        if (isEmpty()) {
            return null;
        }
        return items[indexMod(nextLast - 1)];
    }

    /**
     * Remove and return the element at the front of the deque, if it exists.
     *
     * @return removed element, otherwise {@code null}.
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        nextFirst = indexMod(nextFirst + 1);
        T returnItem = items[nextFirst];
        items[nextFirst] = null; // Null out references that are no longer in the deque.

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
        if (isEmpty()) {
            return null;
        }

        nextLast = indexMod(nextLast - 1);
        T returnItem = items[nextLast];
        items[nextLast] = null;

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
        if (index < 0 || index >= size) {
            return null;
        }
        return items[indexMod(nextFirst + index + 1)];
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
        throw new UnsupportedOperationException("No need to implement getRecursive for ArrayDeque61B");
    }
}
