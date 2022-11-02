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

    public Game(){

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
