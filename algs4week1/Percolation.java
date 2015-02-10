public class Percolation {

	private int count;
	private boolean[] openSites;
	private WeightedQuickUnionUF sites;
	private WeightedQuickUnionUF sites2;

	public Percolation(int N) {               // create N-by-N grid, with all sites blocked
		if (N <= 0) throw new java.lang.IllegalArgumentException("argument less than zero");
		count = N;
		openSites = new boolean[(N+1)*(N+1)];
		for (int i = 0; i < (N+1)*(N+1); i++) {
			openSites[i] = false;
		}
		sites = new WeightedQuickUnionUF((N+1)*(N+1));
		sites2 = new WeightedQuickUnionUF((N+1)*(N+1));
		for (int i = 1; i <= N; i++) {
			sites.union(0, N + i);
			sites2.union(0, N + i);
			sites.union(1, N*N + i);
		}
	}

	public void open(int i, int j) {          // open site (row i, column j) if it is not open already
		if (i <= 0 || j <= 0 || i > count || j > count) throw new java.lang.IndexOutOfBoundsException("argument less than zero");
		openSites[xyTo1D(i, j)] = true;
		if (i < count && isOpen(i + 1, j)) sites.union(xyTo1D(i, j), xyTo1D(i + 1, j));
		if (i > 1 && isOpen(i - 1, j)) sites.union(xyTo1D(i, j), xyTo1D(i - 1, j));
		if (j < count && isOpen(i, j + 1)) sites.union(xyTo1D(i, j), xyTo1D(i, j + 1));
		if (j > 1 && isOpen(i, j - 1)) sites.union(xyTo1D(i, j), xyTo1D(i, j - 1));

		if (i < count && isOpen(i + 1, j)) sites2.union(xyTo1D(i, j), xyTo1D(i + 1, j));
		if (i > 1 && isOpen(i - 1, j)) sites2.union(xyTo1D(i, j), xyTo1D(i - 1, j));
		if (j < count && isOpen(i, j + 1)) sites2.union(xyTo1D(i, j), xyTo1D(i, j + 1));
		if (j > 1 && isOpen(i, j - 1)) sites2.union(xyTo1D(i, j), xyTo1D(i, j - 1));
	}

	public boolean isOpen(int i, int j) {    // is site (row i, column j) open?
		if (i <= 0 || j <= 0 || i > count || j > count) throw new java.lang.IndexOutOfBoundsException("argument less than zero");
		return openSites[xyTo1D(i, j)];     

	}
	public boolean isFull(int i, int j) {     // is site (row i, column j) full?
		if (i <= 0 || j <= 0 || i > count || j > count) throw new java.lang.IndexOutOfBoundsException("argument less than zero");
		return isOpen(i, j) && sites2.connected(0, xyTo1D(i, j));
	}
	public boolean percolates() {         // does the system percolate?
		if (count == 1) return isFull(1, 1);
		else return sites.connected(0, 1);
	}

	private int xyTo1D(int i, int j) {
		return i*count + j;
	}

	public static void main(String[] args) {   // test client (optional)
		Percolation perc = new Percolation(5);
		perc.isOpen(5, 5);
	}

}
