public class draft {

   public static void main(String[] args) // unit tests (not graded)
   {
      int[][] a = new int[3][3];
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            a[i][j] = 1;
         }
      }

      int[][] n;
      n = a;
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            n[i][j] = 2;
         }
      }
      /*
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            System.out.println(a[i][j]);
         }
      }
      */
      System.out.println(a.length);
      int b = 8;
      double c = b/3;
      System.out.println(c);

   }
}
