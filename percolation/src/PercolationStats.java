import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

  private double[] result;
  private int times;
  // perform T independent experiments on an N-by-N grid
  public PercolationStats(int N, int T)
  {
    times = T;
    result = new double[T];
    Percolation perc;
    boolean test = false;
    int count = 0;
    int randRow;
    int randCol;
    for (int i = 0; i < T; i++)
    {
      perc = new Percolation(N);
      //System.out.println("shit");
      //System.out.println(perc.percolates());
      while (!perc.percolates())
      {
        do {
          randRow = StdRandom.uniform(1, N+1);
          randCol = StdRandom.uniform(1, N+1);
          //System.out.println(randRow + " " + randCol);
          if (!perc.isOpen(randRow, randCol))
          {
            perc.open(randRow, randCol);
            test = false;
          }
          else test = true;
        } while (test);
        count += 1;
      }
      //System.out.println(count);
      result[i] = (double) count / (N*N);
      count = 0;
    }
  }
  public double mean()                  // sample mean of percolation threshold
  {
    double mean = 0;
    for (int i = 0; i < times; i++)
    {
      mean += this.result[i];
    }
    return (mean / times);
  }

  public double stddev()   // sample standard deviation of percolation threshold
  {
    double dev = 0;
    double mean = this.mean();
    for (int i = 0; i < times; i++)
    {
      dev += Math.pow(this.result[i]-mean, 2);
    }
    return Math.sqrt((dev / (times-1)));
  }
  public double confidenceLo()       // low  endpoint of 95% confidence interval
  {
    double lo;
    lo = this.mean() - (1.96 * this.stddev() / Math.sqrt(times));
    return lo;
  }
  public double confidenceHi()       // high endpoint of 95% confidence interval
  {
    double hi;
    hi = this.mean() + (1.96 * this.stddev() / Math.sqrt(times));
    return hi;
  }

  public static void main(String[] args)    // test client (described below)
  {

    int width = Integer.parseInt(args[0]);
    int times = Integer.parseInt(args[1]);

    if (times <= 0) {
      throw new IllegalArgumentException("the number of simulation(s) cannot be"
        + "less than 1");
    }

    PercolationStats test = new PercolationStats(width, times);
    System.out.println("mean = " + test.mean());
    System.out.println("stddev = " + test.stddev());
    System.out.println("95% confidence interval = " + test.confidenceLo() +", "
      + test.confidenceHi());


    //for (int i = 0; i<100; i++)
    //  System.out.println(StdRandom.uniform(1,3));
  }
}
