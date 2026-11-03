public class WeightedQuickUnionDSWithPathCompression implements DisjointSets {
    private int[] parent;

    public WeightedQuickUnionDSWithPathCompression(int N) {
        parent = new int[N];
        for (int i = 0; i < N; i++) {
            parent[i] = -1;
        }
    }

    private int find(int p) {
        while (parent[p] >= 0) {
            if (parent[parent[p]] >= 0) {
                parent[p] = parent[parent[p]];
            }
            p = parent[p];
        }
        return p;
    }

    /* * Connects two items p and q. */
    @Override
    public void connect(int p, int q) {
        int i = find(p);
        int j = find(q);
        if (i == j) return;

        if (parent[i] < parent[j]) {
            parent[i] += parent[j];
            parent[j] = i;
        } else {
            parent[j] += parent[i];
            parent[i] = j;
        }
    }

    /**
     * Checks to see if two items are connected.
     */
    @Override
    public boolean isConnected(int p, int q) {
        int i = find(p);
        int j = find(q);
        return i == j;
    }
}
