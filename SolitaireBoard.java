

import java.util.ArrayList;
import java.util.Random;

/*
  class SolitaireBoard
  The board for Bulgarian Solitaire.  You can change what the total number of cards is for the game
  by changing NUM_FINAL_PILES, below.  Don't change CARD_TOTAL directly, because there are only some values
  for CARD_TOTAL that result in a game that terminates.
  (See comments below next to named constant declarations for more details on this.)
*/


public class SolitaireBoard {
   
   public static final int NUM_FINAL_PILES = 9;
   // number of piles in a final configuration
   // (note: if NUM_FINAL_PILES is 9, then CARD_TOTAL below will be 45)
   
   public static final int CARD_TOTAL = NUM_FINAL_PILES * (NUM_FINAL_PILES + 1) / 2;
   // bulgarian solitaire only terminates if CARD_TOTAL is a triangular number.
   // see: http://en.wikipedia.org/wiki/Bulgarian_solitaire for more details
   // the above formula is the closed form for 1 + 2 + 3 + . . . + NUM_FINAL_PILES

   // Note to students: you may not use an ArrayList -- see assgt description for details.
   
   
   /**
      Representation invariant:
      1. currentPilesNum should be [1,CARD_TOTAL].
      2. The total of all the numbers of piles in pilesContainer should equal to CARD_TOTAL.
      3. There should be no 0 among the pilesContainer[0] to pilesContainer[currentPilesNum].

   */
   
   // <add instance variables here>
   private int[] pilesContainer = new int[CARD_TOTAL];
   private int currentPilesNum; // The number of how many piles there are in the pilesContainer.
 
   /**
      Creates a solitaire board with the configuration specified in piles.
      piles has the number of cards in the first pile, then the number of cards in the second pile, etc.
      PRE: piles contains a sequence of positive numbers that sum to SolitaireBoard.CARD_TOTAL
   */
   public SolitaireBoard(ArrayList<Integer> piles) {
      
      for(int i = 0; i < piles.size(); i++){
         pilesContainer[i] = piles.get(i);
      }
      currentPilesNum = piles.size();
      
      assert isValidSolitaireBoard();   // sample assert statement (you will be adding more of these calls)
   }
 
   
   /**
      Creates a solitaire board with a random initial configuration.
   */
   public SolitaireBoard() {
      
      Random generator = new Random();
      int remainTotal = CARD_TOTAL;
      for(int i = 0; i < CARD_TOTAL; i++){
         if(remainTotal == 0){
            continue;
         }
         pilesContainer[i] = 1 + generator.nextInt(remainTotal);
         remainTotal = remainTotal - pilesContainer[i];
         currentPilesNum++;
      }
      assert isValidSolitaireBoard();
   }
  
   
   /**
      Plays one round of Bulgarian solitaire.  Updates the configuration according to the rules
      of Bulgarian solitaire: Takes one card from each pile, and puts them all together in a new pile.
      The old piles that are left will be in the same relative order as before, 
      and the new pile will be at the end.
   */
   public void playRound() {
      
      int pointer = 0; // pointer is used to point out the element that is equal to 1.
      int size = currentPilesNum; //the oiginal size of the plies when the play haven't done yet.
      
      for(int i = 0; i < size; i++){
         if(pilesContainer[i] == 1){
            pilesContainer[i]--;
            pointer++;
            currentPilesNum--;
            continue;
         }
         pilesContainer[i-pointer] = pilesContainer[i] - 1;
         if(pointer > 0){
            pilesContainer[i] = 0;
         }
      }
      pilesContainer[currentPilesNum] = size;
      currentPilesNum++;
      
      assert isValidSolitaireBoard();

   }
   
   /**
      Returns true iff the current board is at the end of the game.  That is, there are NUM_FINAL_PILES
      piles that are of sizes 1, 2, 3, . . . , NUM_FINAL_PILES, in any order.
   */
   
   public boolean isDone() {
      
      if(currentPilesNum != NUM_FINAL_PILES){
         return false;
      }
      int[] test = new int[CARD_TOTAL]; //create a Array to store the elements in pliesContainer.
      int total = 0;
      for(int i = 0; i < NUM_FINAL_PILES; i++){
    	  if(test[pilesContainer[i] - 1] == 0) {
    		  test[pilesContainer[i] - 1] = 1;
    	      total++;
    	  }
      }
      if(total == NUM_FINAL_PILES){
         return true;
      }
      
      assert isValidSolitaireBoard();
      
      return false; 
      
   }

   
   /**
      Returns current board configuration as a string with the format of
      a space-separated list of numbers with no leading or trailing spaces.
      The numbers represent the number of cards in each non-empty pile.
   */
   public String configString() {
      
      String result = "";
      for(int i = 0; i < currentPilesNum - 1; i++){
         result += Integer.toString(pilesContainer[i]) + " ";
      }
      result += Integer.toString(pilesContainer[currentPilesNum - 1]);
      
      assert isValidSolitaireBoard();
      
      return result;   
   }
   
   
   /**
      Returns true iff the solitaire board data is in a valid state
      (See representation invariant comment for more details.)
   */
   private boolean isValidSolitaireBoard() {
      
      int total = 0;
      for(int i = 0; i < currentPilesNum; i++){
         if(pilesContainer[i] == 0){
            return false;
         }
         total += pilesContainer[i];
      }
      if(1 <= currentPilesNum && currentPilesNum <= CARD_TOTAL && total == CARD_TOTAL){
         return true;
      }
      return false;  
   }
   

   // <add any additional private methods here>


}
