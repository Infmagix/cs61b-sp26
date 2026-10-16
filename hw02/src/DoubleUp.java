public class DoubleUp {
   /**
     * Returns a new string where each character of the given string is repeated twice.
     * Example: doubleUp("hello") -> "hheelllloo"
     */
   public static String doubleUp(String s) {
      StringBuilder sb = new StringBuilder("");

      for (int i = 0; i < s.length(); i++) {
         sb.append(s.charAt(i));
         sb.append(s.charAt(i));
      }

      return sb.toString();
   }
   
   public static void main(String[] args) {
      String s = doubleUp("hello");
      System.out.println(s);
      
      System.out.println(doubleUp("cat"));
   }
}