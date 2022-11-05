package game;

import java.util.*;
import java.io.*;

public class Game {
    private int numberOfPlayers;
    private Card[] pack;
    private Player[] players;
    private CardDeck[] decks;
    private File inputFile;

    Game(){
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Please enter the number of players: ");
            try {
                int number = scanner.nextInt();
                if (number > 1) {
                    numberOfPlayers = number;
                    break;
                } else {
                    System.out.println("Number of players must be more than 1.");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Number of players must be an integer.");
            }
        }

        pack = new Card[8*numberOfPlayers];
        while (true) {
            System.out.println("Please enter location of pack to load:");
            String fileName = scanner.next();
            File inputFile = new File(fileName);
            if(inputFile.exists()){
                if (readFile(fileName) == true){
                    break;
                }
                
            }else if(pack.length != 8 * numberOfPlayers){
                System.out.println(String.format("The file doesn't have the right number of cards."));
            }else {
                System.out.println(String.format("The file doesn't exist."));
            }
        }

        scanner.close();
        
    }
    private boolean readFile(String fileName){
        try {
            int index = 0;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while (index < (8 * numberOfPlayers)) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                } else {
                    int value = 0;
                    try {
                        value = Integer.parseInt(line);
                        if (value > 0) {
                            pack[index] = new Card(value);
                        } else {
                            System.out.println("On line " + (index + 1) + " there was a number less that 1");
                            reader.close();
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("On line " + (index +1 ) + " there was a wrong format");
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

    public void createPlayersAndDecks(){
        players = new Player[numberOfPlayers];
        decks = new CardDeck[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++){
            players[i] = new Player(i + 1, this);
            decks[i] = new CardDeck(i + 1);
        }
    }

    public void distributeCards() {
        //First distribute 4 cards to each player
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < numberOfPlayers; j++){
                players[j].getHand().add(pack[i * numberOfPlayers + j]);
            }
        }
        //Then we need to fill the Decks
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < numberOfPlayers; j++){
                decks[j].addCard(pack[4 * numberOfPlayers + i * numberOfPlayers + j]);  //just shifted of 4*numberOfPlayers
            }
        }
        //Assign each deck to the respective player
        for (int i = 0; i < numberOfPlayers; i++){
            players[i].setTakeDeck(decks[i]);   //the take deck has the same number as the player
            players[i].setGiveDeck(decks[(i + 1)  % numberOfPlayers]);
        }
    }

    public void startThreads() {
        for(int i = 0; i < numberOfPlayers; i++){
            new Thread(players[i]).start(); //don't need any reference?
        }
    }

    public synchronized void endGame(){
        for (int i = 0; i < players.length; i++){
            players[i].setKeepGoing(false);
        }
    }

    public File getInputFile() {
        return inputFile;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public Card[] getPack() {
        return pack;
    }

    public void setPack(Card[] pack) {
        this.pack = pack;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public CardDeck[] getDecks() {
        return decks;
    }

    public void setDecks(CardDeck[] decks) {
        this.decks = decks;
    }

    public String winner(){
        return "winner";
    }
}
