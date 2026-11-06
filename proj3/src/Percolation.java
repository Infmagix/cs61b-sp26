import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    /** Directions: left, right, up, down. */
    private final int[] dx = {-1, 1, 0, 0};
    private final int[] dy = {0, 0, -1, 1};

    private int gridSize;
    private WeightedQuickUnionUF uf;
    private int vTopSite, vBotSite;

    private Cell[][] state;

    private enum Cell {
        BLOCKED,    // blocked site
        OPEN,       // open site
//        EMPTY,      // empty open site
//        FULL,       // full open site
    }

    private int nOpenSites;

    /** Create N-by-N grid, with all sites initially blocked. */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be greater than 0.");
        }

        gridSize = N;
        uf = new WeightedQuickUnionUF(N * N + 2);
        vTopSite = N * N;       // open sites connected to it are full
        vBotSite = N * N + 1;   // percolates when it is connected to vTopSite

        state = new Cell[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                state[i][j] = Cell.BLOCKED;

        nOpenSites = 0;
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) return;
        state[row][col] = Cell.OPEN;

        int cIdx = toIndex(row, col);
        if (row == 0) uf.union(cIdx, vTopSite);
        if (row == gridSize - 1) uf.union(cIdx, vBotSite);

        for (int i = 0; i < 4; i++) {
            int nr = row + dx[i];
            int nc = col + dy[i];
            if (!check(nr, nc)) continue;
            if (state[nr][nc] == Cell.BLOCKED) continue;

            int nIdx = toIndex(nr, nc);
            uf.union(cIdx, nIdx);

            if (nr == 0) uf.union(nIdx, vTopSite);
            if (nr == gridSize - 1) uf.union(nIdx, vBotSite);
        }

        nOpenSites++;
    }

    /** Check if the site (row, col) is open. */
    public boolean isOpen(int row, int col) {
        if (!check(row, col)) {
            throw new IndexOutOfBoundsException(
                "The row " + row + " or column " + col + " index is not between 0 and " + (gridSize - 1));
        }

        return state[row][col] == Cell.OPEN;
    }

    /** Check if the open site is connected to sites in the top row. */
    public boolean isFull(int row, int col) {
        if (!check(row, col)) {
            throw new IndexOutOfBoundsException(
                "The row " + row + " or column " + col + " index is not between 0 and " + (gridSize - 1));
        }

        return isOpen(row, col) && uf.connected(toIndex(row, col), vTopSite);
    }

    public int numberOfOpenSites() {
        return nOpenSites;
    }

    /** Check if any open site in the bottom row is connected to sites in the top row. */
    public boolean percolates() {
        return uf.connected(vTopSite, vBotSite);
    }

    private boolean check(int row, int col) {
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
    }

    private int toIndex(int row, int col) {
        return row * gridSize + col;
    }
}
