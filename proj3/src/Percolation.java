import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    /** Directions: left, right, up, down. */
    private final int[] dx = {-1, 1, 0, 0};
    private final int[] dy = {0, 0, -1, 1};

    private int gridSize;
    private WeightedQuickUnionUF uf;

    private State[][] cell;

    private enum State {
        BLOCKED,    // blocked site
        EMPTY,      // empty open site
        FULL,       // full open site
    }

    private int nOpenSites;

    /** Create N-by-N grid, with all sites initially blocked. */
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N must be greater than 0.");
        }

        this.gridSize = N;
        uf = new WeightedQuickUnionUF(N * N);

        cell = new State[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                cell[i][j] = State.BLOCKED;

        nOpenSites = 0;
    }

    public void open(int row, int col) {
        if (isOpen(row, col)) return;

        cell[row][col] = State.EMPTY;
        if (row == 0) cell[row][col] = State.FULL;

        connect(row, col);
        nOpenSites++;
    }

    public boolean isOpen(int row, int col) {
        if (!check(row, col)) {
            throw new IndexOutOfBoundsException(
                "The row " + row + " or column " + col + " index is not between 0 and " + (gridSize - 1));
        }

        return cell[row][col] != State.BLOCKED;
    }

    public boolean isFull(int row, int col) {
        if (!check(row, col)) {
            throw new IndexOutOfBoundsException(
                "The row " + row + " or column " + col + " index is not between 0 and " + (gridSize - 1));
        }

        return cell[row][col] == State.FULL;
    }

    public int numberOfOpenSites() {
        return nOpenSites;
    }

    public boolean percolates() {
        for (int j = 0; j < gridSize; j++)
            if (cell[gridSize - 1][j] == State.FULL)
                return true;

        return false;
    }

    // TODO: Add any useful helper methods (we highly recommend this!).
    // TODO: Remove all TODO comments before submitting.

    private boolean check(int row, int col) {
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize;
    }

    private int toIndex(int row, int col) {
        return row * gridSize + col;
    }

    private void connect(int row, int col) {
        boolean isFullSite = (cell[row][col] == State.FULL);

        int cIdx = toIndex(row, col);
        for (int i = 0; i < 4; i++) {
            int nr = row + dx[i];
            int nc = col + dy[i];
            if (!check(nr, nc)) continue;
            if (cell[nr][nc] == State.BLOCKED) continue;

            int nIdx = toIndex(nr, nc);
            uf.union(cIdx, nIdx);

            if (!isFullSite)
                isFullSite = (cell[nr][nc] == State.FULL);
        }

        if (isFullSite) {
            int root = uf.find(cIdx);
            for (int i = 0; i < gridSize; i++)
                for (int j = 0; j < gridSize; j++)
                    if (uf.find(toIndex(i, j)) == root)
                        cell[i][j] = State.FULL;
        }
    }
}
