public class SLList {
    private class IntNode {
        public int item;
        public IntNode next;

        public IntNode(int item, IntNode next) {
            this.item = item;
            this.next = next;
        }
    }

    private IntNode sentinel; // dummy node
    private int size;

    public SLList() {
        sentinel = new IntNode(42, null);
        size = 0;
    }

    public SLList(int item, IntNode next) {
        sentinel.next = new IntNode(item, next);
        size = 1;
    }

    public int size() {
        return size;
    }

    /** Returns the first item in the list. */
    public int getFirst() {
        return sentinel.next.item;
    }

    private static int get(IntNode p, int i) {
        if (i == 0)
            return p.item;
        return get(p.next, i - 1);
    }

    public int get(int i) {
        return get(sentinel.next, i);
    }

    /** Adds item at the front of the list. */
    public void addFirst(int item) {
        size++;

        sentinel.next = new IntNode(item, sentinel.next);
    }

    /** Adds item at the end of the list. */
    public void addLast(int item ) {
        size++;

        IntNode p = sentinel;
        while (p.next != null)
            p = p.next;

        p.next = new IntNode(item, null);
    }

    /*
     * =================================================================
     * 03 Testing, IntLists, SLLists: Discussion
     * =================================================================
     */

    /**
     *  This method inserts x at the given position of the list.
     *
     *  For example:
     *      If the SLList is 5 → 6 → 2, then insert(10, 1) results in 5 → 10 → 6 → 2.
     *      If the SLList is 5 → 6 → 2, insert(10, 7) results in 5 → 6 → 2 → 10.
     *
     *  Assume that position is a non-negative integer. For an easier challenge,
     *  assume position is between 0 and this.size() + 1. For a bigger challenge,
     *  if position is less than 0 or greater than this.size(), then insert
     *  at the leftmost or rightmost end, respectively.
     */
    public void insert(int x, int position) {
        size++;

        IntNode p = sentinel;
        // Advance p to the node immediately BEFORE the insertion point.
        // 1. position > 0 ensures we don't skip the head (or handles negative inputs).
        // 2. p.next != null ensures we stop at the end if position > size.
        while (position > 0 && p.next != null) {
            p = p.next;
            position--;
        }

        p.next = new IntNode(x, p.next);
    }
}
