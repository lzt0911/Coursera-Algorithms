import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // 将原方案中表示open的boolean数组改为byte数组，设定规则如下
    // 初始化的默认值0代表blocked site
    // 每open一个，如果位于尾行则赋2，否则赋1
    // 分别对每个邻接site检测，如任何一方的root site对应byte值为2，将双方union后的root site设为2
    private byte[] grid;
    private int gridSize;
    private int openedSites;
    private WeightedQuickUnionUF uf;
    private final int virtualTop;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be greater than 0");
        }
        grid = new byte[n * n + 1];
        for (int i = 0; i <= n * n; i++) {
            grid[i] = 0;
        }
        gridSize = n;
        openedSites = 0;
        uf = new WeightedQuickUnionUF(n * n + 1);
        virtualTop = n * n;
    }

    private void validateRowAndCol(int row, int col) {
        if (row < 1 || row > gridSize || col < 1 || col > gridSize) {
            throw new IllegalArgumentException("row or col should be between 1 and grid_size");
        }
    }

    private int convertRowAndCol(int row, int col) {
        return (row - 1) * gridSize + (col - 1);
    }

    private void update(int p, int q) {
        int pRoot = uf.find(p);
        int qRoot = uf.find(q);
        uf.union(p, q);
        if (grid[qRoot] == 2 || grid[pRoot] == 2) {
            grid[pRoot] = 2;
            grid[qRoot] = 2;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        validateRowAndCol(row, col);
        if (!isOpen(row, col)) {
            openedSites++;
            int p = convertRowAndCol(row, col);
            grid[p] = 1;
            if (row == 1) {
                update(p, virtualTop);
            }
            if (row == gridSize) {
                grid[uf.find(p)] = 2;
            }
            // 上
            if (row > 1 && grid[p - gridSize] > 0) {
                update(p, p - gridSize);
            }
            // 下
            if (row < gridSize && grid[p + gridSize] > 0) {
                update(p, p + gridSize);
            }
            // 左
            if (col > 1 && grid[p - 1] > 0) {
                update(p, p - 1);
            }
            // 右
            if (col < gridSize && grid[p + 1] > 0) {
                update(p, p + 1);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateRowAndCol(row, col);
        int p = convertRowAndCol(row, col);
        return grid[p] > 0;
    }

    // is the site (row, col) full? 该位置是否被水充满
    public boolean isFull(int row, int col) {
        validateRowAndCol(row, col);
        int p = convertRowAndCol(row, col);
        return uf.find(p) == uf.find(virtualTop);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openedSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return grid[uf.find(virtualTop)] == 2;
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
