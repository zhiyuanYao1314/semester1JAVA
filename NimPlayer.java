import java.util.Scanner;

/**
 * Author: Zhiyuan Yao
 * StudentId: 1012228
 * Time:2019/5/7 13:29
 * Describe: the abstract class is implemented
 *           by NimAIPlayer and NimHumanPlayer
 *           each player has the given name,
 *           family name, and unique username.
 *           this class record players' game statistics
 *           including the number of games played and
 *           the number of games won.
 * version:1.3
 */

public abstract class NimPlayer {
    // unique username of the player
    protected String username;
    // given name of player
    protected String givenName;
    // family name of player
    protected String familyName;
    // the number of games player played
    protected int numberOfGamesPlayed;
    // the number of games  player won
    protected int numberOfGamesWon;

    /**
     * Determine whether two players have the same user name
     * @param anotherPlayer another player
     * @return true means they have same username, false means they have different username
     */
    public boolean equals(NimPlayer anotherPlayer){
        if (anotherPlayer == null || !this.getUsername().equals(anotherPlayer.getUsername()))
            return false;
        return true;
    }

    /**
     *  players remove stones
     * @param keyboard players input the number of stones they remove
     * @return the number of stones player remove
     */
    public abstract int removeStone(Scanner keyboard,int currentStone, int upperBound);

    /**
     * players move stones for advanced game
     * @param keyboard human players input
     * @param available available stones
     * @param lastMove the stones that last player move
     * @return the stones that current player move
     * @throws InvalidMoveException invalid move exception
     */
    public abstract String advancedMove
            (Scanner keyboard,boolean[] available, String lastMove)
            throws InvalidMoveException;

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public void setNumberOfGamesPlayed(int numberOfGamesPlayed) {
        this.numberOfGamesPlayed = numberOfGamesPlayed;
    }

    public void setNumberOfGamesWon(int numberOfGamesWon) {
        this.numberOfGamesWon = numberOfGamesWon;
    }

    public String getUsername() {
        return username;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public int getNumberOfGamesPlayed() {
        return numberOfGamesPlayed;
    }

    public int getNumberOfGamesWon() {
        return numberOfGamesWon;
    }
}