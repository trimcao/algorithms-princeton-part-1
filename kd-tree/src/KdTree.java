// optimizations: call too many RectHV
// generate RectHV only in case the RectHV does not already exist

import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
   private Node root;
   private Node min;
   private static class Node {
      private Point2D point;      // the point
      private RectHV rect;    // the axis-aligned rectangle corresponding to this node
      private Node lb;        // the left/bottom subtree
      private Node rt;        // the right/top subtree
      private boolean vertical; // the orientation of the node
      private int N; //number of Nodes in the subtree
      private Node() {

      }
      private Node(Point2D p, boolean isVert, int N, RectHV rect)
      {
         this.point = p;
         this.vertical = isVert;
         this.N = N;
         this.rect = rect;
      }
   }
   public KdTree()                               // construct an empty set of points
   {
      root = null; // initiate the root
   }
   public boolean isEmpty()                      // is the set empty?
   {
      return size() == 0;
   }
   public int size()                         // number of points in the set
   {
      return size(root);
   }
   private int size(Node x)
   {
      if (x == null) return 0;
      else return x.N;
   }
   private boolean flip(boolean b)
   {
      if (b) return false;
      else return true;
   }
   private int compareKd(Point2D first, Point2D second, boolean isVert)
   {
      if (first.equals(second))  return 0;
      if (isVert) {
         if (first.x() >= second.x())
            return 1;
         else
            return -1;
      }
      else {
         if (first.y() >= second.y())
            return 1;
         else
            return -1;
      }
   }
   private RectHV genRect(Point2D ref, RectHV parent, boolean isVert, boolean isLess)
   {
      if (isVert) {
         if (isLess) {
            return new RectHV(parent.xmin(), parent.ymin(), ref.x(), parent.ymax());
         }
         else {
            return new RectHV(ref.x(), parent.ymin(), parent.xmax(), parent.ymax());
         }
      }
      else {
         if (isLess)
            return new RectHV(parent.xmin(), parent.ymin(), parent.xmax(), ref.y());
         else
            return new RectHV(parent.xmin(), ref.y(), parent.xmax(), parent.ymax());
      }
   }
   public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
      root = insert(root, p, true, new RectHV(0.0, 0.0, 1.0, 1.0));
   }
   private Node insert(Node x, Point2D p, boolean isVert, RectHV rect)
   {
      if (x == null) return new Node(p, isVert, 1, rect);
      int cmp = compareKd(p, x.point, isVert);
      if (cmp < 0) {
         if (x.lb != null) {
            x.lb = insert(x.lb, p, flip(isVert), x.lb.rect);
         }
         else
            x.lb = insert(x.lb, p, flip(isVert), genRect(x.point, x.rect, isVert, true)); // isLess = true
      }
      else if (cmp > 0) {
         if (x.rt != null)
            x.rt = insert(x.rt, p, flip(isVert), x.rt.rect); // isLess = false
         else
            x.rt = insert(x.rt, p, flip(isVert), genRect(x.point, x.rect, isVert, false)); // isLess = false
      }
      else {}

      x.N = 1 + size(x.lb) + size(x.rt);
      return x;
   }
   public boolean contains(Point2D p)            // does the set contain point p?
   {
      Point2D found = contains(root, p, true);
      if (found == null) return false;
      else return true;
   }
   private Point2D contains(Node x, Point2D p, boolean isVert)
   {
      if (x == null) return null;
      int cmp = compareKd(p, x.point, isVert);
      if (cmp < 0)
         return contains(x.lb, p, flip(isVert));
      else if (cmp > 0)
         return contains(x.rt, p, flip(isVert));
      else
         return x.point;
   }

   public void draw()                         // draw all points to standard draw
   {
      draw(root);
   }
   private void draw(Node x)
   {
      if (x == null) return;
      draw(x.lb);
      draw(x.rt);
      StdDraw.setPenColor(StdDraw.BLACK);
      StdDraw.setPenRadius(.01);
      StdDraw.point(x.point.x(), x.point.y());
      if (x.vertical) {
         StdDraw.setPenRadius(.005);
         StdDraw.setPenColor(StdDraw.RED);
         StdDraw.line(x.point.x(), x.rect.ymin(), x.point.x(), x.rect.ymax());
      }
      else {
         StdDraw.setPenRadius(.005);
         StdDraw.setPenColor(StdDraw.BLUE);
         StdDraw.line(x.rect.xmin(), x.point.y(), x.rect.xmax(), x.point.y());
      }

   }

   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
   {
      Queue<Point2D> queue = new Queue<Point2D>();
      range(root, queue, rect);
      return queue;
   }
   private void range(Node x, Queue<Point2D> queue, RectHV rect)
   {
      if (x == null) return;
      if (!rect.intersects(x.rect)) return; // the query rectangle does not intersect
      if (rect.contains(x.point))
         queue.enqueue(x.point);
      range(x.lb, queue, rect);
      range(x.rt, queue, rect);
   }

   public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
   {
      if (isEmpty()) return null;
      min = root;
      nearest(root, p);
      return min.point;
   }
   private void nearest(Node x, Point2D p)
   {
      if (x == null) return;
      if (x.rect.distanceSquaredTo(p) > p.distanceSquaredTo(min.point))
         return;
      if (p.distanceSquaredTo(x.point) < p.distanceSquaredTo(min.point)) {
         min = x;
      }

      if ((x.lb != null) && (x.lb.rect.contains(p))) {
         nearest(x.lb, p);
         nearest(x.rt, p);
      }
      else if ((x.rt != null) && (x.rt.rect.contains(p))) {
         nearest(x.rt, p);
         nearest(x.lb, p);
      }
      else {
         nearest(x.lb, p);
         nearest(x.rt, p);
      }

   }
   public static void main(String[] args)                  // unit testing of the methods (optional)
   {

      KdTree test = new KdTree();
      test.insert(new Point2D(0.419922, 0.746094));
      test.insert(new Point2D(0.222656, 0.441406));
      //test.insert(new Point2D(0.024472, 0.345492));
      //test.insert(new Point2D(0.793893, 0.095492));

      //Point2D a = new Point2D(0.206107, 0.095492);
      Point2D d = new Point2D(0.222656, 0.441406);
      System.out.println(test.contains(d));
      //test.test();
   }
}
