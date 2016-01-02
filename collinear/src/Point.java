/*************************************************************************
 * Name: Tri Minh Cao
 * Email: trimcao@gmail.com
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate
    //private double slopeRef;

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        int xThat = that.getX();
        int yThat = that.getY();
        if (xThat == this.x && yThat == this.y) {
          return Double.NEGATIVE_INFINITY;
        }
        else if (xThat != this.x && yThat == this.y)
          return 0;
        else if (xThat == this.x && yThat != this.y)
          return Double.POSITIVE_INFINITY;
        else {
          double yDiff = yThat - this.y;
          return yDiff / (xThat - this.x);
        }
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        int xThat = that.getX();
        int yThat = that.getY();
        if (this.y > yThat)
          return 1;
        else if (this.y < yThat)
          return -1;
        else {
          if (this.x > xThat)
            return 1;
          else if (this.x < xThat)
            return -1;
          else
            return 0;
          }

    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    private int getX() {
      return this.x;
    }

    private int getY() {
      return this.y;
    }
    /*
    private void setSlopeRef (Point that) {
      slopeRef = this.slopeTo(that);
    }
    */
    private class SlopeOrder implements Comparator<Point>
    {
      public int compare(Point q1, Point q2) {
        double diff = slopeTo(q1) - slopeTo(q2);
        //double diff = q1.slopeRef - q2.slopeRef;
        if (Double.isNaN(diff) || (diff == 0))
          return 0;
        else if (diff > 0)
          return 1;
        else
          return -1;
        //return q1.slopeRef - q2.slopeRef; // should not use this
      }
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        double a = Double.NEGATIVE_INFINITY;
        double b = Double.NEGATIVE_INFINITY;
        double c = Double.POSITIVE_INFINITY;
        double d = Double.POSITIVE_INFINITY;
        double e = a-b;
        double f = 2.3;
        double g = 2.3;
        double h = -1.2;
        /*
        System.out.println(a == Double.NEGATIVE_INFINITY);
        System.out.println(c-a);
        System.out.println(b-d);
        System.out.println(Double.isNaN(e));
        */
        /*
        double diff = g - h;
        if (Double.isNaN(diff) || (diff == 0))
          System.out.println(0);
        else if (diff > 0)
          System.out.println(1);
        else
          System.out.println(-1);
        */
    }
}
