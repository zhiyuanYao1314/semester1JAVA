import java.util.Scanner;

/**
 * Author:zhiyuan yao
 * StudentId: 1012228
 * Time:2019/5/7 13:29
 * Describe: the NimHumanPlayer class extends NimPlayer,
 *           and implements removeStone method,
 *           this class adds own method called advancedMove.
 * version:1.3
 */
public class NimHumanPlayer extends NimPlayer{

    //constructor
    public NimHumanPlayer(String username, String familyName, String givenName) {
        this.username = username;
        this.givenName = givenName;
        this.familyName = familyName;
    }

    // remove stone for original NimGame
    @Override
    public int removeStone(Scanner keyboard, int currentStone, int upperBound) {
        int num = Integer.parseInt(keyboard.nextLine());
        return num;
    }

    /**
     * advanced move for advanced game.
     * @param keyboard used for player's input
     * @param available available stones which can be moved
     * @param lastMove the stones last player move
     * @return return the stones which current player remove
     * @throws InvalidMoveException invalid move exception will be thrown
     */
    @Override
    public String advancedMove(Scanner keyboard,boolean[] available, String lastMove)
            throws InvalidMoveException {
        String stones = keyboard.nextLine();
        int position = Integer.parseInt(stones.split(" ")[0]);
        int count = Integer.parseInt(stones.split(" ")[1]);
        // valid remove
        if(position<1 || position>available.length || count<1 || count>2 ||
                !available[position - 1])
            throw new InvalidMoveException("Invalid move.");
        if( count ==2 && position<available.length && !available[position])
            throw new InvalidMoveException("Invalid move.");
        if(count ==2 && position==available.length)
            throw new InvalidMoveException("Invalid move.");
        // remove
        available[position-1] = false;
        if (count ==2){
            available[position] = false;
        }
        return stones;
    }

}