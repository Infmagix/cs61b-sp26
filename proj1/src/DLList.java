public class DLList {
    Node sentinel;

    public DLList() {
        // Constructor not shown...
    }

    public class Node {
        int item;
        Node prev, next;
    }

    /**
     * Removes all duplicate items form the DLList.
     * For example, if initial list is [8, 4, 4, 6, 4, 10, 12, 12],
     * the final list should be [8, 4, 6, 10, 12].
     */
    public void removeDuplicates() {
        Node p = sentinel.next;
        while (p != sentinel) {
            Node curr = p.next;
            while (curr != sentinel) {
                Node prev = curr.prev;
                Node next = curr.next;

                if (curr.item == p.item) {
                    prev.next = next;
                    next.prev = prev;
                    curr.prev = curr.next = null;
                }

                curr = next;
            }

            p = p.next;
        }
    }

    public void removeDuplicates_sol() {
        Node ref = sentinel.next;
        Node checker;

        while (ref != sentinel) {
            checker = ref.next;
            while (checker != sentinel) {
                if (ref.item == checker.item) {
                    Node checkerPrev = checker.prev;
                    Node checkerNext = checker.next;

                    checkerPrev.next = checker.next;
                    checkerNext.prev = checker.prev;
                }
                checker = checker.next;
            }
            ref = ref.next;
        }
    }
}
