/* Possible problems
* - equidistant.txt (good thing to investigate),
* input40.txt, input48, input299
* horizontal5.txt (good thing to investigate)
*
*
*/

import java.util.Arrays;

public class Fast {
  public static void main(String[] args) {
    // rescale coordinates and turn on animation mode
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    StdDraw.show(0);
    StdDraw.setPenRadius(0.01);  // make the points a bit  larger

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
    for (int i = 1; i < N; i++) {
      System.out.println(list[i].toString());
      System.out.println(list[i].slopeTo(a));
      list[i].setSlopeRef(a);
    }
    System.out.println();
    System.out.println();
    //Arrays.sort(list, 1, N, Point.SLOPE_ORDER);
    */
    Arrays.sort(list);
    //Point [] oList = list; // ordered list
    /*
    for (int i = 0; i < N; i++) {
      System.out.println(list[i].toString());
    }
    System.out.println("Test 1");
    */
    Point ref;
    int count = 0;
    int index = 0;
    while (index < N) {
      //System.out.println(index);
      ref = list[index];
      System.out.println("Ref is: " + ref.toString());
      Arrays.sort(list, index + 1, N, ref.SLOPE_ORDER);

      for (int i = 1; i < N; i++) {
        System.out.println(list[i].toString() + " and slope = " + list[i].slopeTo(ref));
      }
      System.out.println("Test 2");


      // Counting for 3 or more points with the same slope
      for (int i = index + 1; i < (N - 1); i++) {
        boolean isPrint = false;
        boolean lastComp = false;
        int numPoint = 0;
        if (ref.slopeTo(list[i]) == ref.slopeTo(list[i + 1])) {
          count++;
          System.out.println(count);
          System.out.println("i = " + i);
          System.out.println("N = " + N);
          if (i == (N - 2) && count >= 2) {
            isPrint = true;
            numPoint = count + 2;
            //System.out.println(count);
            count = 0;
          }
        }
        else if (ref.slopeTo(list[i]) != ref.slopeTo(list[i + 1])) {
          // check for value of count here
          // if count >= 2 then output
          if (count >= 2) {
            numPoint = count + 2;
            //System.out.println(count);
            isPrint = true;
            lastComp = true;
          }
          count = 0;
        }

        if (isPrint) {
          // possibly need to sort before output
          // Let figure out better way to do this later
          // One way is to sort every time before incrementing the index

          Point[] preOut = new Point[numPoint];
          preOut[numPoint - 1] = ref;
          if (i == N - 2) {
            for (int j = 0; j < (numPoint - 2); j++) {
              preOut[j] = list[i - j];
            }
            //preOut[numPoint - 1] = ref;
            preOut[numPoint - 2] = list[i + 1];
          }
          else {
            //preOut[numPoint - 1] = ref;
            for (int j = 0; j < (numPoint - 1); j++) {
              preOut[j] = list[i - j];
            }
          }

          Arrays.sort(preOut);

          for (int j = 0; j < numPoint; j++) {
            StdOut.print(preOut[j]);
            if (j < (numPoint - 1)) {
              StdOut.print(" -> ");
            }
          }
          StdOut.println();
          preOut[0].drawTo(preOut[numPoint - 1]);
          StdDraw.show(10);
          /*
          System.out.println(preOut[0].toString() + " -> " + preOut[1].toString() + " -> "
                             + preOut[2].toString() + " -> " + preOut[3].toString());
          preOut[0].drawTo(preOut[3]);
          */
          isPrint = false;
        }
      }
      index = index + 1;
    }


    /*
    Arrays.sort(list, a.SLOPE_ORDER);
    for (int i = 1; i < N; i++) {
      System.out.println(list[i].toString());
      System.out.println(list[i].slopeTo(a));
    }
    */
    // display to screen all at once
    StdDraw.show(0);

    // reset the pen radius
    StdDraw.setPenRadius();
  }
}
