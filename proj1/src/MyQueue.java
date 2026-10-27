public interface MyQueue<E> {
    void enqueue(E element);    // adds an element to the end of the queue
    E dequeue();                // removes and returns the front element of the queue
    boolean isEmpty();          // returns true if the queue is empty
    int size();                 // returns the number of elements in the queue

    // removes all items from the queue
    default void clear() {
        while (!isEmpty()) {
            dequeue();
        }
    }

    // removes all items equal to item from the queue
    // the remaining items should be in the same order as they were before
    // use .equals to compare items rather than ==
    default void remove(E item) {
        int currSize = size();
        for (int i = 0; i < currSize; i++) {
            E currItem = dequeue();
            if (!currItem.equals(item)) {
                enqueue(currItem);
            }
        }
    }

    // appends all items from the other queue into this queue
    // this method should be non-destructive on the otherQueue!
    default void appendALl(MyQueue<E> otherQueue) {
        int n = otherQueue.size();
        for (int i = 0; i < n; i++) {
            E item = otherQueue.dequeue();
            otherQueue.enqueue(item);
            enqueue(item);
        }
    }
}
