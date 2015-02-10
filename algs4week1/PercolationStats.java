public class PercolationStats {
	private int count;
	private int time;
	private double[] results;
	public PercolationStats(int N, int T) {    // perform T independent experiments on an N-by-N grid
		if (N <= 0 || T <= 0) throw new java.lang.IllegalArgumentException("argument less than zero");
		count = N;
		time = T;
		results = new double[time];
		Percolation perc;
		for (int t = 0; t < T; t++) {
			results[t] = 0;
			perc = new Percolation(N);
			while (!perc.percolates()) {
				while (true) {
					int i = (int) (Math.random() * N + 1);
					int j = (int) (Math.random() * N + 1);
					if (perc.isOpen(i, j)) continue;
					perc.open(i, j);
					results[t]++;
					break;
				}
			}
			results[t] /= count*count;
		}
	}
	public double mean() {                     // sample mean of percolation threshold
		return StdStats.mean(results);

	}

	public double stddev() {                   // sample standard deviation of percolation threshold
		return StdStats.stddev(results);
	}

	public double confidenceLo()  {            // low  endpoint of 95% confidence interval
		return mean() - (1.96*stddev())/(Math.sqrt(time));
	}

	public double confidenceHi()  {            // high endpoint of 95% confidence interval
		return mean() + (1.96*stddev())/(Math.sqrt(time));
	}

	public static void main(String[] args) {   // test client (described below)
		PercolationStats percStats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		double mean = percStats.mean();
		double stddev = percStats.stddev();
		double confLo = percStats.confidenceLo();
		double confHi = percStats.confidenceHi();
		System.out.printf("mean \t\t\t= %10.9f \n", mean);
		System.out.printf("stddev \t\t\t= %10.9f \n", stddev);
		System.out.printf("95%% confidence interval\t= %10.9f, %10.9f \n", confLo, confHi);
	}
}
