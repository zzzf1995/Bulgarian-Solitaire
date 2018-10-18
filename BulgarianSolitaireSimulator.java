
import java.util.Scanner;
import java.util.ArrayList;
/**
   This class BulgarianSolitaireSimulator is used to carry out the Solitaire game.
   It enables users to input the initial numbers or just use a random number to start.
   This class can printout the directions about this game and check whether the user inputs legal 
   numbers or not.
   This class can also printout every step of this Solitaire game and the final result.
   Based on different Command Line Arguments, this class can run in three different modes.
*/

public class BulgarianSolitaireSimulator {

   public static void main(String[] args) {
     
      boolean singleStep = false;
      boolean userConfig = false;
      Scanner in = new Scanner(System.in);
      ArrayList<Integer> piles = new ArrayList<>();
      SolitaireBoard solitaire;
      int round = 0;

      for (int i = 0; i < args.length; i++) {
         if (args[i].equals("-u")) {
            userConfig = true;
         }
         else if (args[i].equals("-s")) {
            singleStep = true;
         }
      }

      if(userConfig){
         piles = userinput(in);
         solitaire = new SolitaireBoard(piles);
      }else{
         solitaire = new SolitaireBoard();
      }

      System.out.println("Initial configuration: " + solitaire.configString());
      while(!solitaire.isDone()) {
    	  solitaire.playRound();
    	  round++;
    	  System.out.println("[" + round + "] Current configuration: " + solitaire.configString());
    	  if(singleStep) {
    		  System.out.print("<Type return to continue>");
    		  in.nextLine();
    	  }
      }
      
      System.out.println("Done!");
   }
   
   /**
      Provide a method that enable the user to input a stream of initial numbers that
      can be used to start out Solitaire game instead of using the random numbers.
   */   
   private static ArrayList<Integer> userinput(Scanner in){
	  ArrayList<Integer> piles = new ArrayList<>();
      System.out.println("Number of total cards is " + SolitaireBoard.CARD_TOTAL);
      System.out.println("You will be entering the initial configuration of the cards (i.e., how many in each pile).");
      System.out.println("Please enter a space-separated list of positive integers followed by newline:");
      String line = in.nextLine();
      Scanner lineScanner = new Scanner(line);
      while(lineScanner.hasNextInt()){
         piles.add(lineScanner.nextInt());
      }
      
      
      while(!checkinput(piles) || lineScanner.hasNext()){
         System.out.println("ERROR: Each pile must have at least one card and the total number of cards must be " + SolitaireBoard.CARD_TOTAL);
         System.out.println("Please enter a space-separated list of positive integers followed by newline:");
         line = in.nextLine();
         lineScanner = new Scanner(line);
         piles = new ArrayList<>();
         while(lineScanner.hasNextInt()){
            piles.add(lineScanner.nextInt());
         }
      }
      lineScanner.close();
      
      return piles;
   }
   
   /**
      Provide a method that can check whether the user has inputed legel initial numbers 
      in the Solitaire game or not.
   */
   private static boolean checkinput(ArrayList<Integer> piles){
      int total = 0;
      for(int i = 0; i < piles.size(); i++){
         if(piles.get(i) <= 0){
            return false;
         }
         total += piles.get(i);
      }
      if(total == SolitaireBoard.CARD_TOTAL){
         return true;
      }
      return false;
   }
}
