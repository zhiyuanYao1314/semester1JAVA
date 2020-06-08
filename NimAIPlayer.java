import java.util.Scanner;

/**
 * Author:zhiyuan yao
 * StudentId: 1012228
 * Time:2019/5/7 13:29
 * Describe: This player is an AI player who extends the NimPlayer abstract class
 *           and implements the Testable interface, implements the removeStone method
 *           and the advancedMove method to ensure the greatest possible victory.
 * version:1.3
 */
public class NimAIPlayer extends NimPlayer implements Testable{
    // constructor
    public NimAIPlayer(){}

    //constructor
    public NimAIPlayer(String username, String familyName, String givenName) {
        this.username = username;
        this.givenName = givenName;
        this.familyName = familyName;
    }

    /*
    The AI player moves the stones so that
    the number of stones remaining is k(upperBound+1)+1,
    if the AI player cannot make sure that,only move one stone
    so that the possibility of the other player making mistake
    larger.
     */
    @Override
    public int removeStone(Scanner keyboard, int currentStone, int upperBound) {
        for (int i =1;i<=upperBound;i++){
            if ((currentStone-i-1)%(upperBound+1)==0)
                return i;
        }
        return 1;
    }

    /**
     * the AI player try to move stones to meet the conditions of victory.
     * implement the method in abstract class NimPlayer
     * @param keyboard human players input
     * @param available available stones
     * @param lastMove the stones that last player move
     * @return the stones that current player move
     */
    @Override
    public String advancedMove(Scanner keyboard, boolean[] available, String lastMove) {
        return advancedMove(available,lastMove);
    }

    /**
     * the AI player try to move stones to meet the conditions of victory.
     * implement the method in interface Testable
     * @param available the stones which are available
     * @param lastMove the stones that last player move
     * @return the stones that current player move
     */
    @Override
    public String advancedMove(boolean[] available, String lastMove) {
        /*
         try to remove one stone to make sure that the
         remaining stone meet the conditions of victory
          */
        for (int i=0;i<available.length;i++){
            if (available[i]){
                available[i] = false;
                if (isGuaranteeWin(available))
                    return (i+1)+" "+1;
                else
                    available[i] = true;
            }
        }
        /*
        if removing one stone cannot meet the conditions,
        try to remove two stone to meet the condition of victory
         */
        for (int i=0;i<available.length-1;i++){
            if (available[i] && available[i+1]){
                available[i] = false;
                available[i+1] = false;
                if (isGuaranteeWin(available))
                    return (i+1)+" "+2;
                else {
                    available[i] = true;
                    available[i + 1] = true;
                }
            }
        }
        /*
        if cannot, just remove one stone so that
        the possibility of the other player making mistake larger.
         */
        for (int i=0;i<available.length;i++){
            if (available[i]){
                available[i] = false;
            }
            return (i+1)+" "+1;
        }
        return "";
    }

    /*
     The condition for victory guaranteed strategy.
      */
    private boolean isGuaranteeWin(boolean[] available) {
        // transfer the boolean[] to stringBuilder
        StringBuilder s = new StringBuilder();
        for (boolean ele: available )
            s.append(ele);
        // calculate the number of fragments including different number of stones.
        int[] fragments = new int[available.length];
        String[] ss = s.toString().split("false");
        for (String s1 : ss) {
            if (s1.contains("true"))
                fragments[s1.length() / 4]++;
        }
        /*
         if all this fragments including different number of stones
         are even, AI player can guarantee victory.
          */
        for (int i : fragments) {
            if (i % 2 != 0)
                return false;
        }
        return true;
    }
}