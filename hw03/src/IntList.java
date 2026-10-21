public class IntList {
    int first;
    IntList rest;

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }

    /** Return the size of the list using... recursion! */
    public int size() {
        if (rest == null) {
            return 1;
        }
        return 1 + this.rest.size();
    }

    /** Return the size of the list using no recursion! */
    public int iterativeSize() {
        IntList p = this;
        int totalSize = 0;
        while (p != null) {
            totalSize += 1;
            p = p.rest;
        }
        return totalSize;
    }

    /** Returns the ith item of this IntList. */
    public int get(int i) {
        if (i == 0) {
            return first;
        }
        return rest.get(i - 1);
    }

    /**
     * Returns an IntList identical to L, but with
     * each element incremented by x. Modifies the original list.
     * You are not allowed to use "new" in this method.
     */
    public static IntList incrRecursiveDestructive(IntList L, int x) {
        if (L == null) return L;

        L.first += x;
        incrRecursiveDestructive(L.rest, x);

        return L;
    }

    /*
     * =================================================================
     * OPTIONAL METHODS
     * =================================================================
     */

    /**
     * Returns the sum of all elements in the IntList.
     */
    public int sum() {
        int sum = 0;

        IntList p = this;
        while (p != null) {
            sum += p.first;
            p = p.rest;
        }

        return sum;
    }

    /**
     * Destructively adds x to the end of the list.
     */
    public void addLast(int x) {
        IntList p = this;
        while (p != null && p.rest != null) {
            p = p.rest;
        }

        p.rest = new IntList(x, null);
    }

    /**
     * Destructively adds x to the front of this IntList.
     * This is a bit tricky to implement. The standard way to do this would be
     * to return a new IntList, but for practice, this implementation should
     * be destructive.
     */
    public void addFirst(int x) {
        IntList p = this;
        while (p.rest != null) {
            int tmp = p.first;
            p.first = x;
            x = tmp;
        }

        p.rest = new IntList(x, null);
    }

    /*
     * =================================================================
     * 03 Testing, IntLists, SLLists: Discussion
     * =================================================================
     */

    /**
     * Returns an IntList of the given integers.
     */
    public static IntList list(int... nums) {
        IntList L = new IntList(nums[0], null);
        IntList curr = L;
        for (int i = 1; i < nums.length; i++) {
            curr.rest = new IntList(nums[i], null);
            curr = curr.rest;
        }
        return L;
    }

    /** Replaces all instances of a with b in L.
     *  Modifies the passed list. Non-recursive.
     */
    public static void replaceIterativeDestructive(IntList L, int a, int b) {
        IntList p = L;
        while (p != null) {
            if (p.first == a)
                p.first = b;
            p = p.rest;
        }
    }

    /** Returns a copy of the provided list but with all occurrences
     *  of a replaced by b. Does not modify the current list. Recursive.
     */
    public static IntList replaceRecursiveNonDestructive(IntList L, int a, int b) {
        if (L == null) return null;

        int first = (L.first == a) ? b : L.first;
        return new IntList(first, replaceRecursiveNonDestructive(L.rest, a, b));
    }

    /** Replaces all occurrences of a with b. Modifies the current list. Recursive. */
    public static void replaceRecursiveDestructive(IntList L, int a, int b) {
        if (L == null) return;

        if (L.first == a) L.first = b;
        replaceRecursiveDestructive(L.rest, a, b);
    }

    /** Given a sorted linked list of items, remove the duplicates.
     *  For example, given 1->2->2->2->3, mutate it to become 1->2->3 destructively. Non-recursive.
     * */
    public static void removeDuplicates(IntList L) {
//        if (L == null) return;
//
//        IntList curr = L;
//        while (curr.rest != null) {
//            IntList next = curr.rest;
//            if (curr.first == next.first) {
//                curr.rest = next.rest;
//            } else {
//                curr = curr.rest;
//            }
//        }

        if (L == null) return;

        IntList curr = L.rest;
        IntList prev = L;
        while (curr != null) {
            if (curr.first == prev.first) {
                prev.rest = curr.rest;
            } else {
                prev = curr;
            }
            curr = curr.rest;
        }
    }

    /*
     * =================================================================
     * 03 Testing, IntLists, SLLists: Exam-Level
     * =================================================================
     */

    /**
     * Destructively interweaves lst into k IntLists using round-robin distribution:
     * the first element goes to index 0, the second to index 1, ..., wrapping around
     * after index k-1. No new nodes are created; existing next pointers are rewired.
     * Relative order within each sublist is preserved. Assumes lst's length is
     * evenly divisible by k.
     *
     * @param lst the IntList to interweave (consumed/modified in place)
     * @param k   number of output lists
     * @return an array of k IntLists, where index i holds elements originally at
     *         positions i, i+k, i+2k, ... of lst, in original order
     */
    public static IntList[] interweave(IntList lst, int k) {
//        IntList[] array = new IntList[k];
//        IntList[] tails = new IntList[k];
//
//        int idx = -1;
//        IntList curr = lst;
//        while (curr != null) {
//            idx = (idx + 1) % k;
//            IntList next = curr.rest;
//
//            if (array[idx] == null)
//                array[idx] = curr;
//            else
//                tails[idx].rest = curr;
//            tails[idx] = curr;
//
//            curr.rest = null;
//            curr = next;
//        }
//
//        return array;

        IntList[] array = new IntList[k];

        int index = k - 1;
        IntList L = reverse(lst);
        while (L != null) {
            IntList prevAtIndex = array[index];
            IntList next = L.rest;

            // insert at the front
            array[index] = L;
            array[index].rest = prevAtIndex;

            L = next;
            index--;
            if (index < 0)
                index = k - 1; // wrap around
        }

        return array;
    }

    /** Reverse the passed IntList destructively. */
    public static IntList reverse(IntList L) {
        IntList prev = null;
        IntList curr = L;
        while (curr != null) {
            IntList next = curr.rest;
            curr.rest = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    /**
     *  IntList A = IntList.list(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
     *  IntList B = IntList.list(9, 8, 7, 6, 5, 4, 3, 2, 1);
     *  The result of calling skippify on A and B are as below:
     *  A: (1,3,6,10)
     *  B: (9,7,4)
     */
    public void skippify() {
        int n = 1;

        IntList p = this;
        while (p != null) {
            IntList next = p.rest;

            for (int i = 0; i < n; i++) {
                if (next == null) break;
                next = next.rest;
            }

            p.rest = next;
            p = p.rest;
            n++;
        }
    }
}
