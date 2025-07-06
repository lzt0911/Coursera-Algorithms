import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int gridSize;
    private int openedSites;
    private WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be greater than 0");
        }
        grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false;
            }
        }
        gridSize = n;
        openedSites = 0;
        uf = new WeightedQuickUnionUF(n * n + 2); // 多出两个分别代表与首行所有元素和与尾行所有元素相连
    }

    private void validateRowAndCol(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize) {
            throw new IllegalArgumentException("row or col should be between 1 and grid_size");
        }
    }

    private int convertRowAndCol(int i, int j) {
        return 1 + i * gridSize + j;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateRowAndCol(row, col);
        int i = row - 1;
        int j = col - 1;
        if (!grid[i][j]) {
            grid[i][j] = true;
            openedSites++;
            int p = convertRowAndCol(i, j);
            if (i == 0) {
                uf.union(p, 0);
            }
            if (i == gridSize - 1) {
                uf.union(p, gridSize * gridSize + 1);
            }
            // 左
            if (i - 1 >= 0 && grid[i - 1][j]) {
                int q = convertRowAndCol(i - 1, j);
                uf.union(p, q);
            }
            // 右
            if (i + 1 <= gridSize - 1 && grid[i + 1][j]) {
                int q = convertRowAndCol(i + 1, j);
                uf.union(p, q);
            }
            // 上
            if (j - 1 >= 0 && grid[i][j - 1]) {
                int q = convertRowAndCol(i, j - 1);
                uf.union(p, q);
            }
            // 下
            if (j + 1 <= gridSize - 1 && grid[i][j + 1]) {
                int q = convertRowAndCol(i, j + 1);
                uf.union(p, q);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateRowAndCol(row, col);
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full? 该位置是否被水充满
    public boolean isFull(int row, int col) {
        validateRowAndCol(row, col);
        return uf.find(0) == uf.find(convertRowAndCol(row - 1, col - 1));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openedSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find(gridSize * gridSize + 1);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
