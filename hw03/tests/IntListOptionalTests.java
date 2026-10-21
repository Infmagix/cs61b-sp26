import org.jsoup.select.Evaluator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.Arrays;
import java.util.Formatter;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntListOptionalTests {
    // TBD

    /**
     * Returns an IntList of the given integers.
     */
    public static IntList of(int... nums) {
        IntList L = new IntList(nums[0], null);
        IntList current = L;
        for (int i = 1; i < nums.length; i++) {
            current.rest = new IntList(nums[i], null);
            current = current.rest;
        }
        return L;
    }

    /**
     * Returns true if two IntLists are equal.
     */
    public static boolean checkEquals(IntList L1, IntList L2) {
        if (L1 == null && L2 == null) {
            return true;
        }
        if (L1 == null || L2 == null) {
            return false;
        }
        if (L1.first != L2.first) {
            return false;
        }
        return checkEquals(L1.rest, L2.rest);
    }

    /**
     * If a cycle exists in the IntList, this method
     * returns an integer equal to the item number of the location where the
     * cycle is detected.
     */
    private int detectCycles(IntList A) {
        IntList tortoise = A;
        IntList hare = A;

        if (A == null)
            return 0;

        int cnt = 0;


        while (true) {
            cnt++;
            if (hare.rest != null)
                hare = hare.rest.rest;
            else
                return 0;

            tortoise = tortoise.rest;

            if (tortoise == null || hare == null)
                return 0;

            if (hare == tortoise)
                return cnt;
        }
    }

    /** Outputs the IntList as a String. You are not expected to read
     * or understand this method. */
    private String intListToString(IntList L) {
        if (L == null) {
            return "()";
        }

        Formatter out = new Formatter();
        String sep;
        sep = "(";
        int cycleLocation = detectCycles(L);
        int cnt = 0;

        for (IntList p = L; p != null; p = p.rest) {
            out.format("%s%d", sep, p.first);
            sep = ", ";

            cnt++;
            if ((cnt > cycleLocation) && (cycleLocation > 0)) {
                out.format("... (cycle exists) ...");
                break;
            }
        }
        out.format(")");
        return out.toString();
    }

    @Test
    @Order(1)
    @DisplayName("Test list correctness")
    public void testList() {
        // Check Java visualizer
        IntList L1 = IntList.list(1, 2, 3);
        IntList L2 = new IntList(4, L1.rest);

        L2.rest.first = 13;
        L1.rest.rest.rest = L2;

        IntList L3 = IntList.list(50);
        L2.rest.rest = L3;
    }

    @Test
    @Order(2)
    @DisplayName("Test replaceIterativeDestructive correctness")
    public void testReplaceIterativeDestructive() {
        int a = 2;
        int b = 3;

        IntList L = of(1, 2, 23, 2, 3, 5);
        IntList expected = of(1, 3, 23, 3, 3, 5);
        IntList.replaceIterativeDestructive(L, a, b); // result

        if (!checkEquals(L, expected)) {
            String errorMessage = String.format("For input %s, expected L after call to testReplaceIterativeDestructive to be %s but got %s", intListToString(of(1, 2, 23, 2, 3, 5)), intListToString(expected), intListToString(L));
            fail(errorMessage);
        }
    }

    @Test
    @Order(3)
    @DisplayName("Test replaceRecursiveNonDestructive correctness")
    public void testReplaceRecursiveNonDestructive() {
        int a = 2;
        int b = 3;

        IntList L = of(1, 2, 23, 2, 3, 5);
        IntList expected = of(1, 3, 23, 3, 3, 5);
        IntList result = IntList.replaceRecursiveNonDestructive(L, a, b);

        if (!checkEquals(result, expected)) {
            String errorMessage = String.format("For input %s, expected replaceRecursiveNonDestructive to return %s but got %s", intListToString(L), intListToString(expected), intListToString(result));
            fail(errorMessage);
        }

        if (!checkEquals(L, of(1, 2, 23, 2, 3, 5))) {
            String errorMessage = String.format("For input %s, expected L after call to replaceRecursiveNonDestructive to be %s but got %s", intListToString(L), intListToString(of(1, 2, 23, 2, 3, 5)), intListToString(L));
            fail(errorMessage);
        }
    }

    @Test
    @Order(4)
    @DisplayName("Test replaceRecursiveDestructive correctness")
    public void testReplaceRecursiveDestructive() {
        int a = 2;
        int b = 3;

        IntList L = of(1, 2, 23, 2, 3, 5);
        IntList expected = of(1, 3, 23, 3, 3, 5);
        IntList.replaceRecursiveDestructive(L, a, b); // result

        if (!checkEquals(L, expected)) {
            String errorMessage = String.format("For input %s, expected L after call to replaceRecursiveDestructive to be %s but got %s", intListToString(of(1, 2, 23, 2, 3, 5)), intListToString(expected), intListToString(L));
            fail(errorMessage);
        }
    }

    @Test
    @Order(5)
    @DisplayName("Test removeDuplicates correctness")
    public void testRemoveDuplicates() {
        IntList L = of(1, 2, 2, 2, 3, 5);
        IntList expected = of(1, 2, 3, 5);
        IntList.removeDuplicates(L);

        if (!checkEquals(L, expected)) {
            String errorMessage = String.format("For input %s, expected L after call to replaceRecursiveDestructive to be %s but got %s", intListToString(of(1, 2, 23, 2, 3, 5)), intListToString(expected), intListToString(L));
            fail(errorMessage);
        }
    }

    @Test
    @Order(6)
    @DisplayName("Test interweave correctness")
    public void testInterweave() {
        int k = 2;
        IntList L = of(1, 2, 2, 2, 3, 5);
        IntList[] expected = new IntList[] { of(1, 2, 3), of(2, 2, 5) };
        IntList[] result = IntList.interweave(L, k);

        if (result.length != k) {
            String errorMessage = String.format("For input %s and %d, expected length of the return array after call to interweave to be %s but got %s",
                    intListToString(L), k, k, result.length);
            fail(errorMessage);
        }

        for (int i = 0; i < k; i++) {
            if (!checkEquals(result[i], expected[i])) {
                String errorMessage = String.format("For input %s and %d, expected index %d of the return array after call to interweave to be %s but got %s",
                        intListToString(L), k, i, intListToString(expected[i]), intListToString(result[i]));
                fail(errorMessage);
            }
        }
    }

    @Test
    @Order(7)
    @DisplayName("Test reverse correctness")
    public void testReverse() {
        IntList L = of(1, 3, 2, 5);
        IntList expected =  of(5, 2, 3, 1);
        IntList result = IntList.reverse(L);

        if (!checkEquals(result, expected)) {
            String errorMessage = String.format("For input %s, expected L after call to reverse to be %s but got %s",
                    intListToString(L), intListToString(expected), intListToString(result));
            fail(errorMessage);
        }
    }

    @Test
    @Order(8)
    @DisplayName("Test skippify correctness")
    public void testSkippify() {
        IntList A = of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        IntList B = of(9, 8, 7, 6, 5, 4, 3, 2, 1);
        IntList expectedFromA =  of(1, 3, 6, 10);
        IntList expectedFromB =  of(9, 7, 4);
        A.skippify();
        B.skippify();

        if (!checkEquals(A, expectedFromA)) {
            String errorMessage = String.format("For input %s, expected A after call to skippify to be %s but got %s",
                    intListToString(of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)), intListToString(expectedFromA), intListToString(A));
            fail(errorMessage);
        }

        if (!checkEquals(A, expectedFromA)) {
            String errorMessage = String.format("For input %s, expected A after call to skippify to be %s but got %s",
                    intListToString(of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)), intListToString(expectedFromA), intListToString(A));
            fail(errorMessage);
        }
    }

    @Test
    @Order(9)
    @DisplayName("Test gridify correctness")
    public void testGridify() {
        IntList A = of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        IntList B = of(9, 8, 7, 6, 5, 4, 3, 2, 1);
        IntList expectedFromA =  of(1, 3, 6, 10);
        IntList expectedFromB =  of(9, 7, 4);
        A.skippify();
        B.skippify();

        if (!checkEquals(A, expectedFromA)) {
            String errorMessage = String.format("For input %s, expected A after call to skippify to be %s but got %s",
                    intListToString(of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)), intListToString(expectedFromA), intListToString(A));
            fail(errorMessage);
        }

        if (!checkEquals(A, expectedFromA)) {
            String errorMessage = String.format("For input %s, expected A after call to skippify to be %s but got %s",
                    intListToString(of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)), intListToString(expectedFromA), intListToString(A));
            fail(errorMessage);
        }
    }
}
