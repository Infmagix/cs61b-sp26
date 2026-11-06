import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

public class PercolationTest {

    /**
     * Enum to represent the state of a cell in the grid. Use this enum to help you write tests.
     * <p>
     * (0) CLOSED: isOpen() returns true, isFull() return false
     * <p>
     * (1) OPEN: isOpen() returns true, isFull() returns false
     * <p>
     * (2) INVALID: isOpen() returns false, isFull() returns true
     *              (This should not happen! Only open cells should be full.)
     * <p>
     * (3) FULL: isOpen() returns true, isFull() returns true
     * <p>
     */
    private enum Cell {
        CLOSED, OPEN, INVALID, FULL
    }

    /**
     * Creates a Cell[][] based off of what Percolation p returns.
     * Use this method in your tests to see if isOpen and isFull are returning the
     * correct things.
     */
    private static Cell[][] getState(int N, Percolation p) {
        Cell[][] state = new Cell[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int open = p.isOpen(r, c) ? 1 : 0;
                int full = p.isFull(r, c) ? 2 : 0;
                state[r][c] = Cell.values()[open + full];
            }
        }
        return state;
    }

    @Test
    public void basicTest() {
        int N = 5;
        Percolation p = new Percolation(N);
        // open sites at (r, c) = (0, 1), (2, 0), (3, 1), etc. (0, 0) is top-left
        int[][] openSites = {
                {0, 1},
                {2, 0},
                {3, 1},
                {4, 1},
                {1, 0},
                {1, 1}
        };
        Cell[][] expectedState = {
                {Cell.CLOSED, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
                {Cell.CLOSED, Cell.OPEN, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED}
        };
        for (int[] site : openSites) {
            p.open(site[0], site[1]);
        }
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
    }

    @Test
    public void oneByOneTest() {
        int N = 1;
        Percolation p = new Percolation(N);
        p.open(0, 0);
        Cell[][] expectedState = {
                {Cell.FULL}
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isTrue();
    }

    /** A freshly constructed grid is all blocked, has 0 open sites, and does not percolate. */
    @Test
    public void initialGridTest() {
        int N = 4;
        Percolation p = new Percolation(N);
        Cell[][] expectedState = new Cell[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                expectedState[r][c] = Cell.CLOSED;
            }
        }
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.numberOfOpenSites()).isEqualTo(0);
        assertThat(p.percolates()).isFalse();
    }

    /** numberOfOpenSites counts correctly; re-opening a site does not increment it again. */
    @Test
    public void numberOfOpenSitesTest() {
        Percolation p = new Percolation(3);
        p.open(0, 0);
        p.open(0, 1);
        p.open(2, 2);
        assertThat(p.numberOfOpenSites()).isEqualTo(3);
        // Re-opening an already-open site must not increase the count.
        p.open(0, 0);
        p.open(2, 2);
        assertThat(p.numberOfOpenSites()).isEqualTo(3);
    }

    /** Opening the same site multiple times does not change its state. */
    @Test
    public void duplicateOpenTest() {
        int N = 3;
        Percolation p = new Percolation(N);
        p.open(1, 1);
        p.open(1, 1);
        p.open(1, 1);
        Cell[][] expectedState = {
            {Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
            {Cell.CLOSED, Cell.OPEN, Cell.CLOSED},
            {Cell.CLOSED, Cell.CLOSED, Cell.CLOSED}
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.numberOfOpenSites()).isEqualTo(1);
        assertThat(p.percolates()).isFalse();
    }

    /** Only open sites in the top row are full. */
    @Test
    public void topRowFullTest() {
        int N = 3;
        Percolation p = new Percolation(N);
        p.open(0, 0);
        p.open(0, 2);
        Cell[][] expectedState = {
            {Cell.FULL, Cell.CLOSED, Cell.FULL},
            {Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
            {Cell.CLOSED, Cell.CLOSED, Cell.CLOSED}
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
    }

    /** Does not percolate: top row and bottom row have no connecting path. */
    @Test
    public void doesNotPercolateTest() {
        int N = 4;
        Percolation p = new Percolation(N);
        p.open(0, 0);
        p.open(1, 0);
        p.open(2, 0);
        // Bottom-row site is open but not connected to anything above it.
        p.open(3, 3);
        Cell[][] expectedState = {
            {Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
            {Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
            {Cell.FULL, Cell.CLOSED, Cell.CLOSED, Cell.CLOSED},
            {Cell.CLOSED, Cell.CLOSED, Cell.CLOSED, Cell.OPEN}
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
    }

    /** A single vertical path percolates, and the whole path is full. */
    @Test
    public void verticalPercolationTest() {
        int N = 3;
        Percolation p = new Percolation(N);
        p.open(0, 0);
        p.open(1, 0);
        p.open(2, 0);
        Cell[][] expectedState = {
            {Cell.FULL, Cell.CLOSED, Cell.CLOSED},
            {Cell.FULL, Cell.CLOSED, Cell.CLOSED},
            {Cell.FULL, Cell.CLOSED, Cell.CLOSED}
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isTrue();
    }

    /** A zigzag path percolates. */
    @Test
    public void zigzagPercolationTest() {
        int N = 4;
        Percolation p = new Percolation(N);
        p.open(0, 0);
        p.open(1, 0);
        p.open(1, 1);
        p.open(2, 1);
        p.open(2, 2);
        p.open(3, 2);
        assertThat(p.percolates()).isTrue();
        assertThat(p.isFull(3, 2)).isTrue();
        // An unopened cell stays blocked.
        assertThat(getState(N, p)[0][3]).isEqualTo(Cell.CLOSED);
    }

    /** Whole row opened: middle row is fully open and, when connected to the top, fully full. */
    @Test
    public void fullRowTest() {
        int N = 3;
        Percolation p = new Percolation(N);
        for (int c = 0; c < N; c++) {
            p.open(0, c);
        }
        for (int c = 0; c < N; c++) {
            p.open(1, c);
        }
        Cell[][] expectedState = {
            {Cell.FULL, Cell.FULL, Cell.FULL},
            {Cell.FULL, Cell.FULL, Cell.FULL},
            {Cell.CLOSED, Cell.CLOSED, Cell.CLOSED}
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
    }

    /**
     * Key test: after opening a site connected to the full region, the entire
     * connected component becomes full. Here (1,1) is opened first (not full);
     * then opening (0,1) connects it to the top row, so (1,1) must become full.
     */
    @Test
    public void fullPropagationTest() {
        int N = 3;
        Percolation p = new Percolation(N);
        p.open(1, 1);
        assertThat(p.isFull(1, 1)).isFalse();
        p.open(0, 1);
        Cell[][] expectedState = {
            {Cell.CLOSED, Cell.FULL, Cell.CLOSED},
            {Cell.CLOSED, Cell.FULL, Cell.CLOSED},
            {Cell.CLOSED, Cell.CLOSED, Cell.CLOSED}
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
    }

    /** Fully open N-by-N grid always percolates, and every cell is full. */
    @Test
    public void allOpenTest() {
        int N = 5;
        Percolation p = new Percolation(N);
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                p.open(r, c);
            }
        }
        Cell[][] expectedState = new Cell[N][N];
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                expectedState[r][c] = Cell.FULL;
            }
        }
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isTrue();
        assertThat(p.numberOfOpenSites()).isEqualTo(N * N);
    }

    /** Diagonal adjacency does not count as connected. */
    @Test
    public void diagonalDoesNotConnectTest() {
        int N = 3;
        Percolation p = new Percolation(N);
        p.open(0, 0);
        p.open(1, 1);
        p.open(2, 2);
        Cell[][] expectedState = {
            {Cell.FULL, Cell.CLOSED, Cell.CLOSED},
            {Cell.CLOSED, Cell.OPEN, Cell.CLOSED},
            {Cell.CLOSED, Cell.CLOSED, Cell.OPEN}
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);
        assertThat(p.percolates()).isFalse();
    }

    /** Opening from the bottom up: percolates once the top row becomes connected. */
    @Test
    public void buildFromBottomTest() {
        int N = 3;
        Percolation p = new Percolation(N);
        p.open(2, 2);
        p.open(1, 2);
        assertThat(p.percolates()).isFalse();
        assertThat(p.isFull(1, 2)).isFalse();
        p.open(0, 2);
        assertThat(p.percolates()).isTrue();
        assertThat(p.isFull(0, 2)).isTrue();
        assertThat(p.isFull(1, 2)).isTrue();
        assertThat(p.isFull(2, 2)).isTrue();
    }

    /** Smallest non-trivial percolation case: 2x2. */
    @Test
    public void twoByTwoTest() {
        int N = 2;
        Percolation p = new Percolation(N);
        p.open(0, 0);
        p.open(1, 0);
        assertThat(p.percolates()).isTrue();
        Cell[][] expectedState = {
            {Cell.FULL, Cell.CLOSED},
            {Cell.FULL, Cell.CLOSED}
        };
        assertThat(getState(N, p)).isEqualTo(expectedState);
    }
}
