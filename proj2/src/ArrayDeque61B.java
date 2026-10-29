import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private static final double RESIZE_DOWN_FACTOR = 0.25;

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
            resizeUp();
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
            resizeUp();
        }
        
        items[nextLast] = x;

        nextLast = indexMod(nextLast + 1);
        size++;
    }

    public int capacity() {
        return items.length;
    }

    /**
     * Double backing array size to have enough space to add the next element.
     */
    private void resizeUp() {
        T[] resized = (T[]) new Object[capacity() * 2];

        int first = indexMod(nextFirst + 1);
        for (int i = 0; i < size; i++) {
            resized[i] = items[first];
            first = indexMod(first + 1);
        }

        items = resized;
        nextFirst = capacity() - 1;
        nextLast = size;
    }

    /**
     * For arrays of length 16 or more, the usage factor should always be at least 25%.
     * For array length 15 or less, the usage factor can be arbitrarily low.
     * DO NOT recommend using {@code arraycopy} with a circular implementation.
     */
    private void resizeDown() {
        if (capacity() < 16) {
            return;
        }

        if (getUsageFactor() <= RESIZE_DOWN_FACTOR) {
            int newCap = Math.max(size * 2, 15);
            T[] resized = (T[]) new Object[newCap];

            int first = indexMod(nextFirst + 1);
            for (int i = 0; i < size; i++) {
                resized[i] = items[first];
                first = indexMod(first + 1);
            }

            items = resized;
            nextFirst = capacity() - 1;
            nextLast = size;
        }
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

        resizeDown();

        nextFirst = indexMod(nextFirst + 1);
        T returnItem = items[nextFirst];
        items[nextFirst] = null;

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

        resizeDown();

        nextLast = indexMod(nextLast - 1);
        T returnItem = items[nextLast];
        items[nextLast] = null;

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

    /**
     * Return the usage factor of memory space of the backing array.
     *
     * @return the ratio of {@code size} and {@code capacity}
     */
    private double getUsageFactor() {
        return (double) size / capacity();
    }

    public Iterator<T> iterator() {
        return new ArrayDeque61BIterator();
    }

    private class ArrayDeque61BIterator implements Iterator<T> {

        private int count; // number of elements iterated

        public ArrayDeque61BIterator() {
            count = 0;
        }

        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext() {
            return count < size;
        }

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("the iteration has no more elements");
            }

            T returnItem = items[indexMod(nextFirst + 1 + count)];
            count++;
            return returnItem;
        }
    }


    public static void main(String[] args) {
        Deque61B<String> ad = new ArrayDeque61B<>();

        ad.addLast("front");
        ad.addLast("middle");
        ad.addLast("back");
        for (String s : ad) {
            System.out.println(s);
        }
    }
}
