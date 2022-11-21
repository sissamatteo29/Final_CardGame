package game;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {

    private File inputFile;
    private int numberOfPlayers;
    private Card[] pack;
    private Player[] players;
    private Thread[] playerThreads;
    private CardDeck[] decks;
    //Represent the winner of the game by storing their number.
    private AtomicInteger winner =  new AtomicInteger(0);
    private Scanner scanner;

    /**
     * The main function of the Game.
     * 
     * Asks the user  for the number of players as well as the location of the text file.
     * 
     * Creates the players and decks, distributes the cards.
     * 
     * Starts the threads.
     * 
     * Once a player wins, it creates log files as well as prints the winner to the console.
     */
    public void run() {

        scanner = new Scanner(System.in);

        //Sets the number of players from the users input.
        askNumberOfPlayers();

        //Creates the array that will store the cards.
        pack = new Card[8*numberOfPlayers];

        //Sets the input file to the user input.
        askFileName();

        //Asks the user for a file until the file matches the criteria.
        //Creates the card objects from the file information.
        while (!readFile(inputFile.getName())){
            askFileName();
        }
        
        scanner.close();

        //Creates the player and deck classes.
        createPlayersAndDecks();

        //Distributes the cards in the pack.
        distributeCards();

        //Starts the player threads.
        startThreads();

        //Waits for the player threads to die.
        for (Thread thread: playerThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) { }
        }

        //Creates the logs for the decks.
        for (CardDeckInterface deck: decks){
            generateLog("deck" + deck.getDeckNumber() + "_output.txt", deck.getCardsString());
        }

        //Prints out the winner
        System.out.println("player " + winner.get() + " wins!");
    }

    /**
     * Gets the number of players the user wants to play.
     * 
     * The number of players must be an integer greater than 2, therefore the input question
     * is repeated until a suitable number is inputted.
     */
    public void askNumberOfPlayers(){
        while(true) {
            System.out.println("Please enter the number of players:");
            try {
                String line = scanner.nextLine();
                int number = Integer.parseInt(line);

                //Checks that the player number is higher than 1.
                if (number > 1) {
                    numberOfPlayers = number;
                    System.out.println("The game will have " + numberOfPlayers + " players!");
                    break;
                } else {
                    System.out.println("Number of players must be more than 1.");
                }
            } catch (NumberFormatException e) {
                //Catches the exception thrown by parseInt when the character isn't an integer.
                System.out.println("Number of players must be an integer.");
            } catch (NoSuchElementException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Gets the name of the file that the user wants to be used to create the cards.
     * 
     * The file must exist, therefore the input question is repeated until a suitable number
     * is inputted.
     */
    public void askFileName(){
        try{
            while (true) {
                System.out.println("Please enter location of pack to load:");
                String fileName = scanner.nextLine();

                //Creates a file using the user's input.
                File inputFile = new File(fileName);

                //Tests to see if the file inputted exists.
                if(inputFile.exists()){
                    this.inputFile = inputFile;
                    System.out.println("Selected file: " + fileName);
                    break;
                }else {
                    System.out.printf("The file %s doesn't exist.%n", fileName);
                }
            }
        } catch(SecurityException e){
            //Catches the exception thrown by exists() when the file has security blocking reading.
            System.out.println("This file has a security manager preventing it's reading");
        } catch (NoSuchElementException e) {
            System.out.println(e);
        }
    }

    /**
     * Reads through the file given, checking if there are enough cards in the file and checking
     * to make sure that every character is a positive integer. If both of those conditions are 
     * satisfied the cards are created. Else the function returns false.
     * 
     * @param fileName  The name of the file that will be read.
     * @return True if the file satisfied the conditions, false otherwise.
     */
    public boolean readFile(String fileName){
        try {
            int index = 0;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while (index < (8 * numberOfPlayers)) {
                String line = reader.readLine();

                //If the game requires more cards but the file doesn't have any left.
                if (line == null) {
                    System.out.println("The file " + fileName + " didn't have enough cards!");
                    reader.close();
                    return false;
                } else {
                    int value;
                    try {
                        value = Integer.parseInt(line);

                        //Checks if every integer is positive.
                        if (value > 0) {
                            //Creates a new card object with the number in the file.
                            pack[index] = new Card(value);
                        } else {
                            //Tells the user on which line the negative integer is.
                            System.out.println("On line " + (index + 1) + 
                            " there was a number less that 1");
                            reader.close();
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        //Catches the exception thrown by parseInt when a character isn't an integer.
                        System.out.println("On line " + (index + 1) + " there was a wrong format");
                        reader.close();
                        return false;
                    }

                }
                index++;

            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            return false;
        }
        return true;
    }

    /**
     * Creates the correct number of players and decks, creates Arrays to store the players and 
     * decks as well as assigns the correct decks to the correct players.
     */
    public void createPlayersAndDecks(){
        //Creates the relevant arrays with the correct size.
        players = new Player[numberOfPlayers];
        playerThreads = new Thread[numberOfPlayers];
        decks = new CardDeck[numberOfPlayers];

        //Creates the decks and adds them to the array.
        for (int i = 0; i < numberOfPlayers; i++){
            decks[i] = new CardDeck(i + 1);

        }

        //Creates the players and adds them to the array.
        for (int i = 0; i < numberOfPlayers; i++){
            players[i] = new Player(i + 1, this);

        }

        //Assign each deck to the respective player
        for (int i = 0; i < numberOfPlayers; i++){
            players[i].setTakeDeck(decks[i]);
        }

        //Assign each deck to the respective player
        for (int i = 0; i < numberOfPlayers; i++){
            players[i].setGiveDeck(decks[(i + 1)  % numberOfPlayers]);
        }
    }

    /**
     * Using round robin dealing the cards get distributed to the correct players and decks. 
     */
    public void distributeCards() {
        //Distributes the cards to the players and decks.
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < numberOfPlayers; j++){
                //Jumps over the cards meant for the decks.
                players[j].addCard(pack[i * numberOfPlayers + j]);
                //Jumps over the cards meant for the players.
                decks[j].giveCard(pack[4 * numberOfPlayers + i * numberOfPlayers + j]);
            }
        }
    }

    /**
     * Starts all of the player threads and creates an Array for the player threads.
     */
    public void startThreads() {
        //Creates and adds player threads to the thread array.
        for(int i = 0; i < numberOfPlayers; i++){
            playerThreads[i] = new Thread(players[i]);
        }
        //Starts the threads in the thread array. 
        for(Thread player : playerThreads){
            player.start();
        }
    }

    /**
     * Returns the value of the Atomic integer "winner".
     * @return The number of the winner or 0 if no one has won yet.
     */
    public int returnWinner() {
        return winner.get();
    }

    /**
     * If the expected value is correct sets the Atomic Integer "winner" to the value entered.
     * @param expected The value the Atomic Integer is being compared to.
     * @param value The value the Atomic Integer will be set to.
     */
    public void compareAndSet(int expected, int value) {
        winner.compareAndSet(expected, value);
    }

    /**
     * Stops all of the player threads waiting on decks.
     */
    public void stopDeckMonitor() {
        for (CardDeck deck : decks) {
            synchronized (deck) {
                //Wakes up the player thread that is waiting on the deck's monitor.
                deck.notify();
            }
        }
    }

    /**
     * Creates a text file, and writes to the text file.
     * @param fileName Name of the file being created.
     * @param log The contents of the file.
     */
    public void generateLog(String fileName, String log){
        try {
            FileWriter writer = new FileWriter(fileName);
            //Writes to the text file the player or deck's log.
            writer.write(log);
            writer.close();
        }catch (IOException e){
            //Catches the exception thrown by FileWriter.
            e.printStackTrace();
        }
    }

    /**
     * Returns the file that the user has used to create the cards. Used
     * for testing purposes.
     * @return The file the user has inputted to create the cards.
     */
    public File getInputFile() {
        return inputFile;
    }

    /**
     * Sets the file the cards will be read from. Used for testing purposes.
     * @param inputFile The file that will be used to create the cards for the test.
     */
    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    /**
     * Returns the number of players in the game which is set by the user or by a test.
     * @return The number of players in current the game.
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Sets the number of players for the game, used for test purposes.
     * @param numberOfPlayers Number of players for the test.
     */
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Returns the pack of card objects created. Used for test purposes.
     * @return An array of card objects.
     */
    public Card[] getPack() {
        return pack;
    }

    /**
     * Sets the card pack of the game, used for test purposes.
     * @param pack An array of card objects. 
     */
    public void setPack(Card[] pack) {
        this.pack = pack;
    }

    /**
     * Returns the array of player objects in the game, used for test purposes.
     * @return An array of player objects.
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Sets the array of players in the game, used for test purposes.
     * @param players  An array of player objects.
     */
    public void setPlayers(Player[] players) {
        this.players = players;
    }

    /**
     * Returns the array of player threads in the game, used for test purposes.
     * @return An array of player threads.
     */
    public Thread[] getPlayerThreads() {
        return playerThreads;
    }

    /**
     * Sets the array of player threads in the game, used for test purposes.
     * @param playerThreads An array of player threads.
     */
    public void setPlayerThreads(Thread[] playerThreads) {
        this.playerThreads = playerThreads;
    }

    /**
     * Returns the array of card decks in the game, used for test purposes.
     * @return An array of card deck objects.
     */
    public CardDeck[] getDecks() {
        return decks;
    }

    /**
     * Sets the array of card decks in the game, used for test purposes.
     * @param decks An array of card deck objects.
     */
    public void setDecks(CardDeck[] decks) {
        this.decks = decks;
    }

    /**
     * Returns the AtomicInteger "winner", used for test purposes.
     * @return The AtomicInteger "winner", 0 if no one has won the game,
     * or the player number of the winner.
     */
    public AtomicInteger getWinner() {
        return winner;
    }

    /**
     * Sets the AtomicInteger "winner", used for test purposes.
     * @param winner An AtomicInteger
     */
    public void setWinner(AtomicInteger winner) {
        this.winner = winner;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}
