package game;

import java.io.File;

public class Game {
    private int numberOfPlayers;
    private Card[] pack;
    private Player[] players;
    private CardDeck[] decks;
    private File inputFile;

    public Game(int numberOfPlayers, Card[] pack, Player[] players, CardDeck[] decks, File inputFile) {
        this.numberOfPlayers = numberOfPlayers;
        this.pack = pack;
        this.players = players;
        this.decks = decks;
        this.inputFile = inputFile;
    }

    public Game(int numberOfPlayers){
        this.numberOfPlayers = numberOfPlayers;
    }

    public Game(){

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
