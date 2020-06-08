import java.util.Scanner;

/**
 * Author:zhiyuan yao
 * StudentId: 1012228
 * Time:2019/5/7 13:29
 * Describe: the AdvancedNimGame implements NimGame,
 *           Players can play advanced game in oneGame method.
 * version:1.3
 */
public class AdavancedNimGame implements NimGame {

    // current number of stones
    private int currentStone;
    // The first player
    private NimPlayer player1;
    // The second player
    private NimPlayer player2;
    // the remaining stone
    private boolean[] available;
    // the stones last move
    private String lastMove;

    // constructor
    public AdavancedNimGame(int currentStone,NimPlayer player1,NimPlayer player2){
        this.currentStone = currentStone;
        this.player1 = player1;
        this.player2 = player2;
        available = new boolean[currentStone];
        for (int i=0;i<currentStone;i++){
            available[i]= true;
        }
    }

    /*
       one game,
       return winner when the game finished.
       keyboard is used for inputting information by user
     */
    @Override
    public NimPlayer oneGame(Scanner keyboard) {
        initialization();
        NimPlayer currentPlayer = null;
        for (int i=0;currentStone>0;i++){
            boolean flag = false;
            while (!flag){
                try {
                    System.out.print(currentStone + " stones left: ");
                    printStone();
                    // make sure who is current player
                    currentPlayer = i % 2 == 0 ? player1 : player2;
                    System.out.printf("%s\'s turn - which to remove?\n\n",
                            currentPlayer.getGivenName());
                    lastMove=currentPlayer.advancedMove(keyboard,available,lastMove);
                    flag = true;
                }catch (InvalidMoveException e){ // invalid move Exception
                    System.out.println(e.getMessage());
                    System.out.println();
                }
            }
            // calculate current stone
            currentStone -= Integer.parseInt((lastMove.split(" "))[1]);
        }
        //game over
        System.out.println("Game Over");
        System.out.printf("%s wins!", currentPlayer==player1?(player1.getGivenName()+" "+
                player1.getFamilyName()):(player2.getGivenName()+" "+player2.getFamilyName()));
        System.out.println();
        return currentPlayer==player1?player1:player2;
    }

    // initilization
    private void initialization(){
        currentStone =available.length;
        System.out.println("Initial stone count: "+this.currentStone);
        System.out.print("Stones display: ");
        printStone();
        System.out.println("Player 1: "+player1.getGivenName()+" "+player1.getFamilyName());
        System.out.println("Player 2: "+player2.getGivenName()+" "+player2.getFamilyName());
        System.out.println();
    }

    // print all the stones
    private void printStone(){
        for (int i=0;i<available.length;i++){
            System.out.print("<"+(i+1)+","+(available[i]?"*":"x")+">");
            if(i != available.length-1){
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}
