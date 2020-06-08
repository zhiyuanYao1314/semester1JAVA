import java.util.Scanner;

/**
 * Author:zhiyuan yao
 * StudentId: 1012228
 * Time:2019/5/7 13:29
 * Describe: this class implements NimGame, and
 *           player can play one game in one instance of OriginalNimGame class.
 * version:1.3
 */
public class OriginalNimGame implements NimGame{

    // current number of stones
    private int currentStone;
    // Upper bound of stone removal
    private int upperBound;
    // The first player
    private NimPlayer player1;
    // The second player
    private NimPlayer player2;

    public OriginalNimGame() {
    }

    public OriginalNimGame(int currentStone,int upperBound,NimPlayer player1,NimPlayer player2){
        this.currentStone = currentStone;
        this.upperBound = upperBound;
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * one game
     * @param keyboard used for inputting information by user
     * @return return the winner when one game finished.
     */
    @Override
    public NimPlayer oneGame(Scanner keyboard){
        initialization();
        return playGame(keyboard);
    }

    /**
     * initialize the initial stone, maximum stone removal
     * and two players' names.
     */
    private void initialization(){
        System.out.println("Initial stone count: "+this.currentStone);
        System.out.println("Maximum stone removal: "+this.upperBound);
        System.out.println("Player 1: "+player1.getGivenName()+" "+player1.getFamilyName());
        System.out.println("Player 2: "+player2.getGivenName()+" "+player2.getFamilyName());
        System.out.println();
    }

    /**
     *  Two players take turns to move the stone,
     *  and the player who moves the last stone is the loser.
     */
    private NimPlayer playGame(Scanner keyboard){
        NimPlayer currentPlayer = null;
        // Two players take turns to move the stone
        for (int i=0;currentStone>0;i++){
            System.out.printf("%d stones left:", currentStone);
            for (int j = 0; j < currentStone; j++) {
                System.out.print(" *");
            }
            System.out.println();
            // make sure who is current player
            currentPlayer = i % 2 == 0 ? player1 : player2;
            System.out.printf("%s\'s turn - remove how many?\n\n", currentPlayer.getGivenName());
            int stoneremoved =removeAvailably(currentPlayer,keyboard);
            currentStone -= stoneremoved;
        }
        //game over
        System.out.println("Game Over");
        System.out.printf("%s wins!", currentPlayer==player1?(player2.getGivenName()+" "+
                player2.getFamilyName()):(player1.getGivenName()+" "+player1.getFamilyName()));
        System.out.println();
        return currentPlayer==player1?player2:player1;
    }

    /**
     * make sure the number of stones players remove is reasonable
     * @param player current player
     * @param keyboard player input the number of stones that they remove
     * @return the reasonable number of stones players remove
     */
    private int removeAvailably(NimPlayer player,Scanner keyboard){
        int stoneRemoved=1;
        boolean flag = false;
        String message = "Invalid move. You must remove between 1 and " +
                +(upperBound > currentStone ? currentStone : upperBound)
                +" stones.";
        while (!flag){
            try {
                stoneRemoved = player.removeStone(keyboard,currentStone,upperBound);
                if (stoneRemoved>upperBound || stoneRemoved>currentStone || stoneRemoved< 1 ) {
                    throw new InvalidMoveException(message);
                }
                flag = true;
            }catch (InvalidMoveException e){
                System.out.println(e.getMessage()+"\n");
                printCurrentStone();
                System.out.printf("%s\'s turn - remove how many?\n\n", player.getGivenName());
            }catch (NumberFormatException e){
                System.out.println(message+"\n");
                printCurrentStone();
                System.out.printf("%s\'s turn - remove how many?\n\n", player.getGivenName());
            }
        }
        return stoneRemoved;
    }

    /**
     * print current stones
     */
    private void printCurrentStone(){
        System.out.printf("%d stones left:", currentStone);
        for (int j = 0; j < currentStone; j++) {
            System.out.print(" *");
        }
        System.out.println();
    }
}