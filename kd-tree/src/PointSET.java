// Corner cases: when data structures have zero elements.

import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {

   private SET<Point2D> set;
   public PointSET()                               // construct an empty set of points
   {
      set = new SET<Point2D>();
   }
   public boolean isEmpty()                      // is the set empty?
   {
      return set.isEmpty();
   }
   public int size()                         // number of points in the set
   {
      return set.size();
   }
   public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
      /*
      if (!contains(p)) {
         set.add(p);
      }
      */
      set.add(p);
   }
   public boolean contains(Point2D p)            // does the set contain point p?
   {
      return set.contains(p);
   }
   public void draw()                         // draw all points to standard draw
   {
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.setPenRadius(.01);
      for (Point2D temp : set) {
         StdDraw.point(temp.x(), temp.y());
      }
   }
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
   {
      Stack<Point2D> list = new Stack<Point2D>();
      for (Point2D temp : set) {
         if (rect.distanceSquaredTo(temp) == 0) {
            list.push(temp);
         }
      }
      return list;
   }
   public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
   {
      if (isEmpty()) return null;
      Point2D result = null;
      double minDist = 2.1; //max squared distance is sqrt(2)
      for (Point2D temp : set) {
         if (p.distanceSquaredTo(temp) < minDist) {
            result = temp;
            minDist = p.distanceSquaredTo(temp);
         }
      }
      return result;
   }

   public static void main(String[] args)                  // unit testing of the methods (optional)
   {

   }
}
