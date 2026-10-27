public interface MyStack<E> {
    void push(E element);   // adds an element to the top of the stack
    E pop();                // removes and returns the top element of the stack
    boolean isEmpty();      // returns true if the stack is empty
    int size();             // returns the number of elements in the stack

    // inserts the item at the bottom of the stack using push, pop, isEmpty and size
    private void insertAtBottom(E item) {
        if (isEmpty()) {
            push(item);
            return;
        }

        E e = pop();
        insertAtBottom(item);
        push(e);
    }

    // flips the stack upside down (hint: use insertAtBottom)
    default void flip() {
        if (isEmpty()) {
            return;
        }

        E item = pop();
        flip();
        insertAtBottom(item);
    }
}
