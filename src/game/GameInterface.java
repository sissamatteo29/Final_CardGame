package game;

import java.io.File;

public interface GameInterface {

    int returnWinner();

    void compareAndSet(int expected, int value);

    void run();

    void stopDeckMonitor();

    void startThreads();

    void askNumberOfPlayers();

    void askFileName();

    boolean readFile(String fileName);

    void createPlayersAndDecks();

    void distributeCards();

    void generateLog(String fileName,String log);

    File getInputFile();

    void setInputFile(File inputFile) ;
    Card[] getPack();

    void setPack(Card[] pack);

    CardDeck[] getDecks();

    void setDecks(CardDeck[] decks);
}
