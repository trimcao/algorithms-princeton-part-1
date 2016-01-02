public class Percolation {

    private int[][] grid;
    private int width;
    private WeightedQuickUnionUF perc;
    private WeightedQuickUnionUF percBottom;
    private int virtualTop;
    private int virtualBottom;
    private int fullSite=0;
    private boolean percolate = false;

    public Percolation(int N)     // create N-by-N grid, with all sites blocked
    {
      if (N<=0) {
        throw new IllegalArgumentException("the grid width cannot be less than 1");
      }
      width = N;
      grid = new int[N+1][N+1];
      perc = new WeightedQuickUnionUF(N*N+1);
      percBottom = new WeightedQuickUnionUF(N*N+1);

      //N^2 is the virtual top site, N^2+1 is the virtual bottom site
      this.virtualTop = N*N;
      this.virtualBottom = N*N;
      for (int i=0; i<N+1; i++) {
        for (int j=0; j<N+1; j++) {
          grid[i][j]=0; //0 means blocked, 1 means open
        }
      }
    }

    public void open(int i, int j)
    // open site (row i, column j) if it is not open already
    {
        this.validate(i,j);
        grid[i][j]=1;
        int position = xyTo1D(i,j);

        //check if site is adjacent to Top or Bottom
        if (i == 1) {
          perc.union(position, virtualTop);
          this.fullSite = position;
        }
        else if (i == width) percBottom.union(position, virtualBottom);

        //check and union 4 adjacent sites
        if (j<width) {
          if (isOpen(i,j+1)) {
              perc.union(position, xyTo1D(i,j+1));
              percBottom.union(position, xyTo1D(i,j+1));
          }
        }
        if (j>1) {
          if (isOpen(i,j-1)) {
              perc.union(position, xyTo1D(i,j-1));
              percBottom.union(position, xyTo1D(i,j-1));
          }
        }
        if (i<width) {
          if (isOpen(i+1,j)) {
            perc.union(position, xyTo1D(i+1,j));
            percBottom.union(position, xyTo1D(i+1,j));
          }
        }
        if (i>1) {
          if (isOpen(i-1,j)) {
            perc.union(position, xyTo1D(i-1,j));
            percBottom.union(position, xyTo1D(i-1,j));
          }
        }

        //check if this site is Full. If Yes, update fullSite position
        if (i>1) {
          if (isFull(i,j)) {
            this.fullSite = position;
          }
        }

    }
    public boolean isOpen(int i, int j)     // is site (row i, column j) open?
    {
      this.validate(i,j);
      if (grid[i][j] >= 1) return true;
      else return false;
    }
    public boolean isFull(int i, int j)     // is site (row i, column j) full?
    {
      this.validate(i,j);
      int position = xyTo1D(i,j);
      if (perc.connected(position,virtualTop)) {
        return true;
      }
      else return false;
    }

    public boolean percolates()             // does the system percolate?
    {
      if (this.percolate)
        return true;
      else if (percBottom.connected(fullSite,virtualBottom)) {
        this.percolate = true;
        return this.percolate;
      }
      else
        return false;

      //return this.percolate;
    }

    private int xyTo1D(int i, int j)
    {
      int result = (i - 1) * width - 1 + j;
      return result;
    }

    private void validate(int i, int j) {
      if (i < 1 || i > this.width) {
        throw new IndexOutOfBoundsException("index " + i + " is not between 1 and " + this.width);
      }
      if (j < 1 || j > this.width) {
        throw new IndexOutOfBoundsException("index " + j + " is not between 1 and " + this.width);
      }
}

    public static void main(String[] args)   // test client (optional)
    {
      Percolation test = new Percolation(3);
      test.open(1,2);
      test.open(2,2);
      test.open(3,2);

      //System.out.println(test.percolates());
      //System.out.println(Math.pow(3,2));
    }
}
