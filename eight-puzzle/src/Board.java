/*
Hamming and Manhattan also counts the preceding moves. I must pay attention to that.
*/

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.Queue;

public class Board {
   private int[][] board;
   private int N;
   // construct a board from an N-by-N array of blocks
   // (where blocks[i][j] = block in row i, column j)
   public Board(int[][] blocks) {
      N = blocks.length;
      this.board = new int[N][N];
      //this.board = blocks; // shallow copy, might be problematic? Yes
      for (int i = 0; i < N; i++) {
         for (int j = 0; j < N; j++) {
            board[i][j] = blocks[i][j];
            //goal[i][j] = TwoDtoOneD(i, j);
         }
      }
   }

   public int dimension()                 // board dimension N
   {
      return N;
   }
   public int hamming()                   // number of blocks out of place
   {
      int count = 0; // count the number of blocks in the wrong position
      for (int i = 0; i < N; i++) {
         for (int j = 0; j < N; j++) {
            if (board[i][j] == 0);
            else if (board[i][j] != TwoDtoOneD(i, j)) // 1D = row * N + column + 1;
               count += 1;
         }
      }
      return count;
   }

   public int manhattan()                 // sum of Manhattan distances between blocks and goal
   {
      int count = 0;
      int[] tempPos = new int[2];
      for (int i = 0; i < N; i++) {
         for (int j = 0; j < N; j++) {
            if (board[i][j] != 0) {
               tempPos = OneDtoTwoD(board[i][j]);
               count = count + Math.abs(i - tempPos[0]) + Math.abs(j - tempPos[1]);
            }
         }
      }
      return count;
   }
   public boolean isGoal()                // is this board the goal board?
   {
      //boolean result = true;
      if (board[N - 1][N - 1] != 0)
         return false;
      for (int i = 0; i < N; i++) {
         for (int j = 0; j < N; j++) {
            if (board[i][j] != 0) {
               if (board[i][j] != TwoDtoOneD(i, j)) {
                  return false;
               }
            }
         }
      }
      return true;
   }
   public Board twin()                    // a board that is obtained by exchanging two adjacent blocks in the same row
   {
      int[][] twinBoard = new int[N][N];
      for (int i = 0; i < N; i++) {
         for (int j = 0; j < N; j++) {
            twinBoard[i][j] = board[i][j];
         }
      }
      boolean swap = false;
      for (int i = 0; i < N; i++) {
         for (int j = 0; j < N - 1; j++) {
            if (twinBoard[i][j] != 0 && twinBoard[i][j+1] != 0) {
               int temp = twinBoard[i][j];
               twinBoard[i][j] = twinBoard[i][j + 1];
               twinBoard[i][j + 1] = temp;
               swap = true;
               break;
            }
         }
         if (swap) break;
      }
      return new Board(twinBoard);
   }
   public boolean equals(Object other)        // does this board equal y?
   {
      //boolean equal;
      if (other == this) return true;
      if (other == null) return false;
      if (other.getClass() != this.getClass()) return false;
      Board that = (Board) other;
      if (N != that.dimension()) return false;
      for (int i = 0; i < N; i++) {
         for (int j = 0; j < N; j++) {
            if (board[i][j] != that.board[i][j])
               return false;
         }
      }
      return true;
   }

   /*
   private Board genNeighbor (int[][] blocks, int rowZ, int colZ, int rowSwap, int colSwap)
   {

   }
   */
   public Iterable<Board> neighbors()     // all neighboring boards
   {
      Queue<Board> neighbor = new Queue<Board>();
      int[][] tempBoard = new int[N][N];
      int rowZ = -1;
      int colZ = -1;
      // make a copy of board[][], also find the location of the blank
      for (int i = 0; i < N; i++) {
         for (int j = 0; j < N; j++) {
            tempBoard[i][j] = board[i][j];
            if (board[i][j] == 0) {
               rowZ = i;
               colZ = j;
            }
         }
      }
      //int temp = 0;
      // find neighbors and add them to the Queue
      if (checkPos(rowZ - 1, colZ)) {
         tempBoard[rowZ][colZ] = tempBoard[rowZ - 1][colZ];
         tempBoard[rowZ - 1][colZ] = 0;
         neighbor.enqueue(new Board(tempBoard));
         tempBoard[rowZ - 1][colZ] = tempBoard[rowZ][colZ];
         tempBoard[rowZ][colZ] = 0;
      }
      if (checkPos(rowZ + 1, colZ)) {
         tempBoard[rowZ][colZ] = tempBoard[rowZ + 1][colZ];
         tempBoard[rowZ + 1][colZ] = 0;
         neighbor.enqueue(new Board(tempBoard));
         tempBoard[rowZ + 1][colZ] = tempBoard[rowZ][colZ];
         tempBoard[rowZ][colZ] = 0;
      }
      if (checkPos(rowZ, colZ - 1)) {
         tempBoard[rowZ][colZ] = tempBoard[rowZ][colZ - 1];
         tempBoard[rowZ][colZ - 1] = 0;
         neighbor.enqueue(new Board(tempBoard));
         tempBoard[rowZ][colZ - 1] = tempBoard[rowZ][colZ];
         tempBoard[rowZ][colZ] = 0;
      }
      if (checkPos(rowZ, colZ + 1)) {
         tempBoard[rowZ][colZ] = tempBoard[rowZ][colZ + 1];
         tempBoard[rowZ][colZ + 1] = 0;
         neighbor.enqueue(new Board(tempBoard));
         tempBoard[rowZ][colZ + 1] = tempBoard[rowZ][colZ + 1];
         tempBoard[rowZ][colZ] = 0;
      }
      return neighbor;
   }

   public String toString() {
      StringBuilder s = new StringBuilder();
      s.append(N + "\n");
      for (int i = 0; i < N; i++) {
         for (int j = 0; j < N; j++) {
            //if (board[i][j] == 0)
            //   s.append("   ");
            //else
            s.append(String.format("%2d ", board[i][j]));
         }
         s.append("\n");
      }
      return s.toString();
   }

   private int TwoDtoOneD(int i, int j) {
      return i * N + j + 1;
   }

   private int[] OneDtoTwoD(int k) {
      int[] pos = new int[2];
      double row = ((double)k) / N;
      pos[0] = (int)Math.ceil(row) - 1;
      if (k % N == 0)
         pos[1] = N - 1;
      else
         pos[1] = (k % N) - 1;
      return pos;
   }
   private boolean checkPos(int i, int j) {
      if (i < 0 || j < 0 || i > (N - 1) || j > (N - 1))
         return false;
      else
         return true;
   }

   public static void main(String[] args) // unit tests (not graded)
   {
      int[][] blocks = new int[3][3];
      /*
      blocks[0][0] = 8;
      blocks[0][1] = 1;
      blocks[0][2] = 3;
      blocks[1][0] = 4;
      blocks[1][1] = 0;
      blocks[1][2] = 2;
      blocks[2][0] = 7;
      blocks[2][1] = 6;
      blocks[2][2] = 5;
      */
      /*
      blocks[0][0] = 8;
      blocks[0][1] = 0;
      blocks[0][2] = 3;
      blocks[1][0] = 4;
      blocks[1][1] = 1;
      blocks[1][2] = 2;
      blocks[2][0] = 7;
      blocks[2][1] = 6;
      blocks[2][2] = 5;
      Board test = new Board(blocks);
      System.out.println(test.toString());
      //System.out.println(test.twin().toString());
      Queue<Board> neighbor = new Queue<Board>();
      neighbor = (Queue<Board>)test.neighbors();
      for (Board temp : neighbor) {
         System.out.println(temp.toString());
      }
      */
      /* // test basic methods: equals, hamming, manhattan,
      System.out.println(test.dimension());
      System.out.println(test.hamming());
      System.out.println(test.manhattan());
      System.out.println(test.isGoal());

      blocks[2][0] = 9;
      Board other = new Board(blocks);
      System.out.println(test.equals(blocks));

      blocks[2][0] = 7;
      Board that = new Board(blocks);
      System.out.println(test.equals(that));
      */
      int N = 3;
      int[][] goal = new int[3][3];
      for (int i = 0; i < N; i++) {
         for (int j = 0; j < N; j++) {
            goal[i][j] = i * N + j + 1;
         }
      }
      goal[N-1][N-1] = 0;
      goal[2][0] = 7;
      goal[2][1] = 6;
      goal[1][2] = 8;
      Board goalBoard = new Board(goal);
      System.out.println(goalBoard.isGoal());

   }
}
