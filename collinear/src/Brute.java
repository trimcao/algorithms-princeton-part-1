import java.util.Arrays;

public class Brute {
  public static void main(String[] args) {

    // rescale coordinates and turn on animation mode
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    StdDraw.show(0);
    StdDraw.setPenRadius(0.01);  // make the points a bit larger

    String filename = args[0];
    In in = new In(filename);
    int N = in.readInt();
    Point[] list = new Point[N];
    for (int i = 0; i < N; i++) {
      int x = in.readInt();
      int y = in.readInt();
      Point p = new Point(x, y);
      list[i] = p;
      p.draw();
    }
    /*
    for (int i = 0; i < N; i++) {
      System.out.println(list[i].toString());
    }
    */
    /*
    // Test the sorting
    Arrays.sort(list);
    System.out.println("Test sorting");
    for (int i = 0; i < N; i++) {
      System.out.println(list[i].toString());
    }
    */

    /* Process the data
     */
    Arrays.sort(list);
    StdOut.println();
    int i = 0; int j = 1;
    int k = 2; int h = 3;
    int iMax = N - 4; int jMax = N - 3;
    int kMax = N - 2; int hMax = N - 1;
    Point a; Point b; Point c; Point d;
    int count = 0;
    while (i <= iMax) {

      a = list[i]; b = list[j];
      c = list[k]; d = list[h];
      if (j < jMax && k < kMax && h < hMax)
        h = h + 1;
      else if (j < jMax && k < kMax && h == hMax) {
        k = k + 1;
        h = k + 1; // this uses new k
      }
      else if (j < jMax && k == kMax && h == hMax) {
        j = j + 1; k = j + 1; h = k + 1;
      }
      else if (j == jMax && k == kMax && h == hMax) {
        i = i + 1; j = i + 1; k = j + 1; h = k + 1;
      }
      // Check if four points are in the same line
      // Don't need another method because it is only written once here
      if (b.slopeTo(a) == c.slopeTo(a) && b.slopeTo(a) == d.slopeTo(a)) {
        //count++;
        //System.out.println(count);
        StdOut.println(a.toString() + " -> " + b.toString() + " -> "
                           + c.toString() + " -> " + d.toString());
        a.drawTo(d);
        StdDraw.show(50);
      }

    }

    // display to screen all at once
    StdDraw.show(0);

    // reset the pen radius
    StdDraw.setPenRadius();
  }

}
