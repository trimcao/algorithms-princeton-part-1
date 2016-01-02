/*
*
*
*/

import java.util.Arrays;
import java.util.ArrayList;

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
    StdOut.println();
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
    ArrayList<Double> slopeList = new ArrayList<Double>();
    ArrayList<Point> pointList = new ArrayList<Point>();
    while (index < N) {
      //System.out.println(index);
      ref = list[index];
      ///System.out.println("Ref is: " + ref.toString()); // for debugging
      Arrays.sort(list, index + 1, N, ref.SLOPE_ORDER);
      /* // for debugging
      for (int i = 1; i < N; i++) {
        System.out.println(list[i].toString() + " and slope = " + list[i].slopeTo(ref));
      }
      System.out.println("Test 2");
      */

      // Counting for 3 or more points with the same slope
      for (int i = index + 1; i < (N - 1); i++) {
        boolean isPrint = false;
        boolean lastComp = false;
        int numPoint = 0;
        if (ref.slopeTo(list[i]) == ref.slopeTo(list[i + 1])) {
          count++;
          //System.out.println(count); // for debugging
          //System.out.println(list[i].toString() + " slope = " + ref.slopeTo(list[i]));
          //System.out.println(list[i + 1].toString() + " slope = " + ref.slopeTo(list[i + 1]));
          //System.out.println("i = " + i); // for debugging
          //System.out.println("N = " + N); // for debugging
          if (i == (N - 2) && count >= 2) {
            isPrint = true;
            numPoint = count + 2;
            lastComp = true;
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
          }
          count = 0;
        }

        // Print out and draw the results
        if (isPrint) {
          // possibly need to sort before output
          // Let figure out better way to do this later
          // One way is to sort every time before incrementing the index

          Point[] preOut = new Point[numPoint];
          preOut[numPoint - 1] = ref;
          if (lastComp) {
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
          // Check if the line is already in the list
          double slope = preOut[0].slopeTo(preOut[numPoint - 1]);
          //System.out.println("Current slope: " + slope);
          Point temp = preOut[0];
          Point temp2 = preOut[numPoint - 1];
          boolean different = true;
          for (int j = 0; j < slopeList.size(); j++) {
            //System.out.println(slopeList.get(j));
            if (slopeList.get(j) == slope) {
              double checkSlope;
              if (pointList.get(j).compareTo(temp) == 0)
                checkSlope = pointList.get(j).slopeTo(temp2);
              else
                checkSlope = pointList.get(j).slopeTo(temp);
              //System.out.println("Check slope: " + checkSlope);
              if (checkSlope == slope || checkSlope == -slope)
                different = false;
            }
          }
          //System.out.println(different);
          if (different) {
            slopeList.add(slope);
            pointList.add(temp);
            for (int j = 0; j < numPoint; j++) {
              StdOut.print(preOut[j]);
              if (j < (numPoint - 1)) {
                StdOut.print(" -> ");
              }
            }
            StdOut.println();
            preOut[0].drawTo(preOut[numPoint - 1]);
            StdDraw.show(10);
          }
          isPrint = false;
        }
      }
      count = 0;
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
