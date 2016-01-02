// I need to use a private class of search Nodes to record moves,
// board, and previous node

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.*;
import java.util.Comparator;

public class Solver {
   //private int moves;
   private MinPQ<Node> nodes;
   private MinPQ<Node> twinNodes;
   private Comparator<Node> comparator;
   //private Queue<Board> sol;
   private Node finalNode;

   private class Node {
      private Board board;
      private Node prev;
      private int moves;

      private Node() {
         this.prev = null;
         this.moves = 0;
         this.board = null;
      }
      private Node(Board board) {
         this.board = board;
         this.prev = null;
         this.moves = 0;
      }
      private Node(Board board, Node prev, int moves) {
         this.board = board;
         this.prev = prev;
         this.moves = moves;
      }
      private void setPrev(Node prev) {
         this.prev = prev;
      }
      private void setMoves(int moves) {
         this.moves = moves;
      }
      private void setBoard(Board board) {
         this.board = board;
      }
      private Node getPrev() {
         return prev;
      }
      private int getMoves() {
         return moves;
      }
      private Board getBoard() {
         return board;
      }
      public String toString() {
         StringBuilder s = new StringBuilder();
         s.append("priority = " + (board.manhattan() + moves) + "\n");
         s.append("moves = " + moves + "\n");
         s.append("manhattan = " + board.manhattan() + "\n");
         s.append(board);
         return s.toString();
      }
   }

   public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
   {
      if (initial == null) throw new java.lang.NullPointerException();
      comparator = new ManhattanComparator();
      nodes = new MinPQ<Node>(comparator);
      twinNodes = new MinPQ<Node>(comparator);
      nodes.insert(new Node(initial));
      //System.out.println(initial.twin());
      twinNodes.insert(new Node(initial.twin()));
      Node currentNode = nodes.delMin();
      //System.out.println("current Node: " + currentNode.getBoard());
      //Node prevNode = currentNode;
      Node currTwin = twinNodes.delMin();
      //Node prevTwin = currTwin;
      //int count = 1;
      while (!currentNode.getBoard().isGoal() && !currTwin.getBoard().isGoal()) {
         //neighbor = currentNode.getBoard().neighbors();
         //neighborTwin = currTwin.getBoard().neighbors();

         for (Board temp : currentNode.getBoard().neighbors()) {
            if (currentNode.getPrev() == null)
               nodes.insert(new Node(temp, currentNode, currentNode.getMoves() + 1));
            else if (!temp.equals(currentNode.getPrev().getBoard()))
               nodes.insert(new Node(temp, currentNode, currentNode.getMoves() + 1));
         }
         for (Board temp : currTwin.getBoard().neighbors()) {
            if (currentNode.getPrev() == null)
               twinNodes.insert(new Node(temp, currTwin, currTwin.getMoves() + 1));
            else if (!temp.equals(currTwin.getPrev().getBoard()))
               twinNodes.insert(new Node(temp, currTwin, currTwin.getMoves() + 1));
         }
         /* // Output all nodes in the minpq to screen for debugging
         System.out.println("Step " + count++ +": " + "\n");
         for (Node temp: nodes) {
            System.out.println(temp);
         }
         */
         //prevNode = currentNode;
         currentNode = nodes.delMin();
         //prevTwin = currTwin;
         currTwin = twinNodes.delMin();
      }

      // Assume currentNode now contains the goal board
      finalNode = currentNode;
      if (!finalNode.getBoard().isGoal()) {
         finalNode.setMoves(-1);
         finalNode = null;
      }

   }
   public boolean isSolvable()            // is the initial board solvable?
   {
      if (moves() == -1)
         return false;
      else
         return true;
   }
   public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
   {
      if (finalNode == null)
         return -1;
      return finalNode.getMoves();
   }
   public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
   {
      if (finalNode == null)
         return null;
      // Use final node to track the solution
      Stack<Board> sol = new Stack<Board>();
      Node ref = finalNode;
      while (ref != null) {
         sol.push(ref.getBoard());
         ref = ref.getPrev();
      }
      //System.out.println("Solution size: " + sol.size());
      return sol;
   }

   private class ManhattanComparator implements Comparator<Node>
   {
      @Override
      public int compare(Node x, Node y)
      {
         // Assume neither string is null. Real code should
         // probably be more robust
         int xVal = x.getBoard().manhattan() + x.getMoves();
         int yVal = y.getBoard().manhattan() + y.getMoves();
         if (xVal < yVal)
         {
            return -1;
         }
         if (xVal > yVal)
         {
            return 1;
         }
         if (x.getBoard().manhattan() < y.getBoard().manhattan()) {
            return -1;
         }
         if (x.getBoard().manhattan() > y.getBoard().manhattan()) {
            return 1;
         }
         return 0;
      }
   }

   public static void main(String[] args) // solve a slider puzzle (given below)
   {
      // create initial board from file
      In in = new In(args[0]);
      int N = in.readInt();
      int[][] blocks = new int[N][N];
      for (int i = 0; i < N; i++)
         for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
      Board initial = new Board(blocks);
      System.out.println("Original board");
      System.out.println(initial);
      StdOut.println();
      // solve the puzzle
      Solver solver = new Solver(initial);

      // print solution to standard output
      if (!solver.isSolvable())
         StdOut.println("No solution possible");
      else {
         StdOut.println("Minimum number of moves = " + solver.moves());
         for (Board board : solver.solution())
            StdOut.println(board);
      }
   }
}
