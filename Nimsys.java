import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Author:zhiyuan yao
 * StudentId: 1012228
 * Time:2019/5/7 13:29
 * Describe: there are up to 100 players who can be stored in this Nimsys,
 *           user can add player, remove player, edit player, reset player statistics,
 *           display player, ranking players, and
 *           start one game or advanced game.
 * version:1.3
 */
public class Nimsys {

    // keyboard is used for player input information.
    private Scanner keyboard;
    // all players
    private NimPlayer[] players;
    // the command which user input
    private String command;
    // how many players this nimSys has currently
    private int playersSize;
    // the max number of players in this Nimsys
    private final int MAX_NUMBER_PLAYERS = 100;

    public Nimsys() {
        this.players = new NimPlayer[MAX_NUMBER_PLAYERS];
        keyboard=new Scanner(System.in);
        playersSize=0; // initialize 0.
    }

    public static void main(String[] args) {
        Nimsys nimsys = new Nimsys();
        nimsys.load();
        System.out.println("Welcome to Nim\n");
        while(true){
            System.out.print("$");
            nimsys.command = nimsys.keyboard.nextLine();
            // catch all exceptions. Print and return it to the user.
            try {
                nimsys.executeCommand();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            System.out.println();
        }
    }

    /**
     * when starting this system, load information from disk.
     */
    private void load(){
        File playersData = new File("players.dat");
        FileInputStream fis=null;
        BufferedReader reader = null;
        try {
            fis = new FileInputStream(playersData);
            reader = new BufferedReader(new InputStreamReader(fis));
            String line;
            int i=0;
            while ((line = reader.readLine()) !=null){
                String[] playersDetails =line.split(" ");
                if (playersDetails[0].equals("y")){
                    players[i] = new NimAIPlayer(playersDetails[1],
                            playersDetails[2],playersDetails[3]);
                    players[i].setNumberOfGamesWon(Integer.parseInt(playersDetails[4]));
                    players[i].setNumberOfGamesPlayed(Integer.parseInt(playersDetails[5]));
                }else{
                    players[i] = new NimHumanPlayer(playersDetails[1],
                            playersDetails[2],playersDetails[3]);
                    players[i].setNumberOfGamesWon(Integer.parseInt(playersDetails[4]));
                    players[i].setNumberOfGamesPlayed(Integer.parseInt(playersDetails[5]));
                }
                i++;
            }
            this.playersSize =i;
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Select the appropriate method based on the user's command
     * @throws InvalidNumberOfArgumentsException invalid number of arguments exception
     * @throws InvalidCommandException invalid comment exception
     */
    private void executeCommand()
            throws InvalidNumberOfArgumentsException, InvalidCommandException {
        String[] commands =command.split(" ");
        if (commands.length>1)
            command = commands[1];
        else
            command = ""; // If there are no parameters

        if (commands[0].equals("addplayer") || commands[0].equals("addaiplayer")){
            addPlayer(commands[0]);
        }else if(commands[0].equals("removeplayer")){
            removePlayer();
        }else if(commands[0].equals("editplayer")){
            editPlayer();
        }else if(commands[0].equals("resetstats")){
            resetStats();
        }else if(commands[0].equals("displayplayer")){
            displayplayer();
        }else if(commands[0].equals("rankings")){
            rankings();
        }else if(commands[0].equals("startgame")){
            startgGame();
        }else if(commands[0].equals("startadvancedgame")){
            startAdvancedGame();
        }else if(commands[0].equals("exit")){
            System.out.println();
            update();
            System.exit(0);
        }else {
            throw new InvalidCommandException("'"+commands[0] +"' is not a valid command.");
        }
    }

    /**
     * When this program exits,
     * this ﬁle will be updated with new/modiﬁed players.
     */
    private void update(){
        File playersData = new File("players.dat");
        PrintWriter out = null;
        try {
            out = new PrintWriter(playersData);
            for (int i=0;i<playersSize;i++){
                if (players[i] instanceof NimAIPlayer){
                    out.print("y"+" ");
                }else{
                    out.print("n"+" ");
                }
                out.print(players[i].getUsername()+" ");
                out.print(players[i].getFamilyName()+" ");
                out.print(players[i].getGivenName()+" ");
                out.print(players[i].getNumberOfGamesWon()+" ");
                out.print(players[i].getNumberOfGamesPlayed());
                out.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (out != null){
                out.close();
            }
        }
    }


    /**
     * add one player
     * @param comm comment which player input.
     * @throws InvalidNumberOfArgumentsException invalid number of arguments exception
     */
    private void addPlayer(String comm) throws InvalidNumberOfArgumentsException{
        String[] arguments = command.split(",");
        if (arguments.length< 3 )
            throw new InvalidNumberOfArgumentsException
                    ("Incorrect number of arguments supplied to command.");
        int playerIndex =findPlayer(arguments[0]);
        if (playerIndex!=-1){
            System.out.println("The player already exists.");
            return;
        }
        if (comm.equals("addaiplayer")){
            players[playersSize]= new NimAIPlayer(arguments[0],arguments[1],arguments[2]);
        }else{
            players[playersSize]= new NimHumanPlayer(arguments[0],arguments[1],arguments[2]);
        }
        playersSize++;
    }

    /**
     * remove one or all players.
     */
    private void removePlayer(){

        // remove all players
        if (command.equals("")){
            System.out.println("Are you sure you want to remove all players? (y/n)");
            char answer = keyboard.nextLine().charAt(0);
            if (answer=='y'){
                players = new NimPlayer[MAX_NUMBER_PLAYERS];
                playersSize = 0;
                return;
            }
            return;
        }
        // remove one player
        String userName = command;
        int playerIndex = findPlayer(userName);
        if (playerIndex==-1){
            System.out.println("The player does not exist.");
            return;
        }
        for (int i=playerIndex;i<playersSize;i++) players[i] = players[i + 1];
        playersSize--;
    }

    /**
     *  edit player's family name and given name.
     * @throws InvalidNumberOfArgumentsException invalid number of arguments exception
     */
    private void editPlayer()throws InvalidNumberOfArgumentsException{
        String[] arguments = command.split(",");

        if (arguments.length< 3 )
            throw new InvalidNumberOfArgumentsException
                    ("Incorrect number of arguments supplied to command.");

        int playerIndex =findPlayer(arguments[0]);
        if (playerIndex==-1){
            System.out.println("The player does not exist.");
            return;
        }
        players[playerIndex].setFamilyName(arguments[1]);
        players[playerIndex].setGivenName(arguments[2]);
    }

    /**
     * reset one or all players' statistics
     */
    private void resetStats(){
        // reset all players' statistics
        if (command.equals("")){
            System.out.println("Are you sure you want to reset all player statistics? (y/n)");
            char answer = keyboard.nextLine().charAt(0);
            if (answer=='y'){
                for (int i=0;i<playersSize;i++){
                    players[i].setNumberOfGamesPlayed(0);
                    players[i].setNumberOfGamesWon(0);
                }
            }
            return;
        }
        //reset one player' statistics
        String userName = command;
        int playerIndex = findPlayer(userName);
        if (playerIndex==-1){
            System.out.println("The player does not exist.");
            return;
        }
        players[playerIndex].setNumberOfGamesPlayed(0);
        players[playerIndex].setNumberOfGamesWon(0);
    }

    /**
     * display one or all players information
     */
    private void displayplayer(){
        //display all players
        if (command.equals("")){
            NimPlayer[] allPlayers = rankingAlphabetically();
            for(int i=0;i<playersSize;i++){
                printPlayer(allPlayers[i]);
            }
            return;
        }
        // display one player
        String userName = command;
        int playerIndex = findPlayer(userName);
        if (playerIndex==-1){
            System.out.println("The player does not exist.");
            return;
        }
        printPlayer(players[playerIndex]);
    }

    /**
     * print one player information
     * @param player this player who is printed
     */
    private void printPlayer(NimPlayer player){
        System.out.println(player.getUsername()+","
                +player.getGivenName()+","
                +player.getFamilyName()+","
                +player.getNumberOfGamesPlayed()+" games,"
                +player.getNumberOfGamesWon()+" wins");
    }

    /**
     * Ranking all players
     */
    private void rankings(){
        NimPlayer[] allPlayer = rankingAlphabetically();
        Arrays.sort(allPlayer, new Comparator<NimPlayer>() {
            @Override
            public int compare(NimPlayer o1, NimPlayer o2) {
                double o1wonTimes = o1.getNumberOfGamesWon();
                double o2wonTimes = o2.getNumberOfGamesWon();
                double o1playTimes = o1.getNumberOfGamesPlayed();
                double o2playTimes = o2.getNumberOfGamesPlayed();
                double x = o1wonTimes*100/(o1playTimes>0?o1playTimes:1)-
                        o2wonTimes*100/ (o2playTimes>0?o2playTimes:1);
                if (command.equals("asc")){
                    return x==0?0:x>0?1:-1;
                }
                return x==0?0:x>0?-1:1;
            }
        });
        // Print less than 10 players
        int maxLength = allPlayer.length>10?10:allPlayer.length;
        for (int i=0;i<maxLength;i++){
            NimPlayer p = allPlayer[i];
            double winTimes = p.getNumberOfGamesWon();
            int playTimes = p.getNumberOfGamesPlayed();
            int winningRatio = (int) ((winTimes*100)/(playTimes>0?playTimes:1)+0.5);
            String firstColumn = winningRatio + "%";
            System.out.printf("%-5s", firstColumn);
            System.out.println("| "+String.format("%02d",playTimes) + " games | "
                    + p.getGivenName() + " " + p.getFamilyName());
            }
    }

    /**
     *  ranking all players alphabetically
     * @return return all players which are ranked.
     */
    private NimPlayer[] rankingAlphabetically() {
        NimPlayer[] allPlayer = new NimPlayer[playersSize];
        System.arraycopy(players, 0, allPlayer, 0, playersSize);
        Arrays.sort(allPlayer, new Comparator<NimPlayer>() {
            @Override
            public int compare(NimPlayer o1, NimPlayer o2) {
                return (o1.getUsername().compareTo(o2.getUsername()));
            }
        });
        return allPlayer;
    }


    /**
     * start one game
     * @throws InvalidNumberOfArgumentsException invalid number of arguments exception
     */
    private void startgGame() throws InvalidNumberOfArgumentsException{
        String[] arguments = command.split(",");

        if (arguments.length< 4 )
            throw new InvalidNumberOfArgumentsException
                    ("Incorrect number of arguments supplied to command.");

        int initialNumber = Integer.parseInt(arguments[0]);
        int upperBound = Integer.parseInt(arguments[1]);
        String player1Name = arguments[2];
        String player2Name = arguments[3];
        int player1index = findPlayer(player1Name);
        int player2index = findPlayer(player2Name);
        if( player1index==-1 || player2index==-1){
            System.out.println("One of the players does not exist.");
            return;
        }
        System.out.println();
        NimPlayer player1 = players[player1index];
        NimPlayer player2 = players[player2index];
        NimGame game = new OriginalNimGame(initialNumber,upperBound,
                players[player1index],players[player2index]);
        // update players' number of games played and number of games won
        NimPlayer winner = game.oneGame(keyboard);
        player1.setNumberOfGamesPlayed(player1.getNumberOfGamesPlayed()+1);
        player2.setNumberOfGamesPlayed(player2.getNumberOfGamesPlayed()+1);
        if (player1.equals(winner)){
            player1.setNumberOfGamesWon(player1.getNumberOfGamesWon()+1);
        }else{
            player2.setNumberOfGamesWon(player2.getNumberOfGamesWon()+1);
        }
    }

    /**
     *  start one advanced game
     * @throws InvalidNumberOfArgumentsException invalid number of arguments exception
     */
    private void startAdvancedGame() throws InvalidNumberOfArgumentsException{
        String[] arguments = command.split(",");
        if (arguments.length< 3 )
            throw new InvalidNumberOfArgumentsException
                    ("Incorrect number of arguments supplied to command.");

        int initialNumber = Integer.parseInt(arguments[0]);
        String player1Name = arguments[1];
        String player2Name = arguments[2];
        int player1index = findPlayer(player1Name);
        int player2index = findPlayer(player2Name);
        if( player1index==-1 || player2index==-1){
            System.out.println("One of the players does not exist.");
            return;
        }
        System.out.println();
        NimPlayer player1 = players[player1index];
        NimPlayer player2 = players[player2index];
        NimGame game = new AdavancedNimGame(initialNumber,
                       players[player1index],players[player2index]);

        // update players' number of games played and number of games won
        NimPlayer winner = game.oneGame(keyboard);
        player1.setNumberOfGamesPlayed(player1.getNumberOfGamesPlayed()+1);
        player2.setNumberOfGamesPlayed(player2.getNumberOfGamesPlayed()+1);
        if (player1.equals(winner)){
            player1.setNumberOfGamesWon(player1.getNumberOfGamesWon()+1);
        }else{
            player2.setNumberOfGamesWon(player2.getNumberOfGamesWon()+1);
        }

    }

    /**
     *  find player by username
     * @param userName input by palyer
     * @return return the index of player found, -1 means that do not find player.
     */
    private int findPlayer(String userName){
        for (int i=0;i<players.length;i++){
            if (players[i] == null){
                return -1;
            }else if(userName.equals(players[i].getUsername())){
                return i;
            }
        }
        return -1;
    }
}