public class StarTriangle5 {
   /**
     * Prints a right-aligned triangle of stars ('*') with 5 lines.
     * The first row contains 1 star, the second 2 stars, and so on. 
     */
   public static void starTriangle5() {
      for (int i = 1; i <= 5; i++) {
         for (int j = 1; j <= 5; j++) {
            System.out.print((j <= 5 - i) ? " " : "*");
         }
         System.out.println();
      }
   }
   
   public static void main(String[] args) {
      starTriangle5();
   }
}