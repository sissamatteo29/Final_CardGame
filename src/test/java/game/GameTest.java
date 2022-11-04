package game;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    Game myTestGame1;
    Game myTestGame2;

    @Before
    public void createTwoNewGames(){

        myTestGame1 = new Game();
        myTestGame2 = new Game();

        Card[] pack1 = new Card[40];
        for (int i = 0; i < 40; i++){
            pack1[i] = new Card(3);
        }

        Player[] players1 = new Player[5];
        CardDeck[] decks1 = new CardDeck[5];
        for (int i = 0; i < 5; i++){
            players1[i] = new Player(i, myTestGame1);
            decks1[i] = new CardDeck(i);
        }

        myTestGame1.setNumberOfPlayers(5);
        myTestGame1.setDecks(decks1);
        myTestGame1.setPack(pack1);
        myTestGame1.setPlayers(players1);

        Card[] pack2 = new Card[16];
        for (int i = 0; i < 16; i++){
            pack2[i] = new Card(8);
        }

        Player[] players2 = new Player[2];
        CardDeck[] decks2 = new CardDeck[2];
        for (int i = 0; i < 2; i++){
            players2[i] = new Player(i, myTestGame2);
            decks2[i] = new CardDeck(i);
        }

        myTestGame2.setPlayers(players2);
        myTestGame2.setPack(pack2);
        myTestGame2.setDecks(decks2);
        myTestGame2.setNumberOfPlayers(2);
    }

    @Test
    public void testCreatePlayersAndDecks() {
        Game trialGame = new Game(4);
        trialGame.createPlayersAndDecks();
        assertNotEquals(trialGame.getPlayers()[3], null);
        assertNotEquals(trialGame.getPlayers()[0], null);
        assertNotEquals(trialGame.getDecks()[2], null);
        assertNotEquals(trialGame.getDecks()[1], null);
    }

    @Test
    public void testDistributeCardsForGame1() {
        myTestGame1.distributeCards();
        //Verifying the length of some random players' hands
        assertEquals(4, myTestGame1.getPlayers()[1].getHand().size());
        assertEquals(4, myTestGame1.getPlayers()[0].getHand().size());

        //verifying that some cards in players' hands are equal to 3
        assertEquals(3, myTestGame1.getPlayers()[3].getHand().get(2).getNumber());
        assertEquals(3, myTestGame1.getPlayers()[0].getHand().get(3).getNumber());

        //verifying the same things for the decks
        assertEquals(4, myTestGame1.getDecks()[0].getCards().size());
        assertEquals(4, myTestGame1.getDecks()[3].getCards().size());

        assertEquals(3, myTestGame1.getDecks()[0].getCards().get(2).getNumber());
        assertEquals(3, myTestGame1.getDecks()[0].getCards().get(3).getNumber());
    }



    @Test
    public void startThreads() {
    }

    @Test
    public void endGame() {
    }

    @Test
    public void winner() {
    }
}