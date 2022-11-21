package game;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Provides test for the distributeCards() method inside the Game class
 */
public class GameDistributeCardsTest {

    Game game1;
    Game game2;

    /**
     *Creates 2 new games with different number of players to apply the distributeCards() method.
     * Sets up the fundamental members of the Game class
     */
    @Before
    public void setGames(){
        game1 = new Game(); //5 players
        game2 = new Game(); //2 players

        Card[] pack1 = new Card[40];
        for (int i = 0; i < 40; i++){
            pack1[i] = new Card(3);
        }

        Player[] players1 = new Player[5];
        CardDeck[] decks1 = new CardDeck[5];
        for (int i = 0; i < 5; i++){
            players1[i] = new Player(i + 1, game1);
            decks1[i] = new CardDeck(i + 1);
        }

        game1.setNumberOfPlayers(5);
        game1.setDecks(decks1);
        game1.setPack(pack1);
        game1.setPlayers(players1);

        Card[] pack2 = new Card[16];
        for (int i = 0; i < 16; i++){
            pack2[i] = new Card(8);
        }

        Player[] players2 = new Player[2];
        CardDeck[] decks2 = new CardDeck[2];
        for (int i = 0; i < 2; i++){
            players2[i] = new Player(i + 1, game2);
            decks2[i] = new CardDeck(i + 1);
        }

        game2.setPlayers(players2);
        game2.setPack(pack2);
        game2.setDecks(decks2);
        game2.setNumberOfPlayers(2);
    }

    @Test
    public void testDistributeCardsGame1PlayersHands(){
        /* Calls the method of the Game class to test */
        game1.distributeCards();

        /* Checks the size of players' hands */
        assertEquals(4, game1.getPlayers()[0].getHand().size());
        assertEquals(4, game1.getPlayers()[1].getHand().size());
        assertEquals(4, game1.getPlayers()[2].getHand().size());
        assertEquals(4, game1.getPlayers()[3].getHand().size());
        assertEquals(4, game1.getPlayers()[4].getHand().size());

        /* Verifies that some cards in players' hands are equal to 3 (game1 was set up with a deck of cards with number 3 to simplify) */
        assertEquals(3, game1.getPlayers()[3].getHand().get(2).getNumber());
        assertEquals(3, game1.getPlayers()[0].getHand().get(3).getNumber());
    }

    @Test
    public void testDistributeCardsGame1Decks(){
        /* Calls the method of the Game class to test */
        game1.distributeCards();

        /* Checks the size of the internal CardDeck's data structure */
        assertEquals(4, game1.getDecks()[0].getCards().size());
        assertEquals(4, game1.getDecks()[1].getCards().size());
        assertEquals(4, game1.getDecks()[2].getCards().size());
        assertEquals(4, game1.getDecks()[3].getCards().size());
        assertEquals(4, game1.getDecks()[4].getCards().size());

        /* Cheks that some random cards have number 3 */
        assertEquals(3, game1.getDecks()[0].getCards().get(2).getNumber());
        assertEquals(3, game1.getDecks()[0].getCards().get(3).getNumber());
    }

    @Test
    public void testDistributeCardsGame2PlayersHands(){
        /* Calls the method of the Game class to test */
        game2.distributeCards();

        /* Checks the size of players' hands */
        assertEquals(4, game2.getPlayers()[0].getHand().size());
        assertEquals(4, game2.getPlayers()[1].getHand().size());

        /* Verifies that some cards in players' hands are equal to 8 (game2 was set up with a deck of cards with number 3 to simplify) */
        assertEquals(8, game2.getPlayers()[1].getHand().get(2).getNumber());
        assertEquals(8, game2.getPlayers()[0].getHand().get(3).getNumber());
    }

    @Test
    public void testDistributeCardsGame2Decks(){
        /* Calls the method of the Game class to test */
        game2.distributeCards();

        /* Checks the size of the internal CardDeck's data structure */
        assertEquals(4, game2.getDecks()[0].getCards().size());
        assertEquals(4, game2.getDecks()[1].getCards().size());

        /* Checks that some random cards have number 8 */
        assertEquals(8, game2.getDecks()[0].getCards().get(2).getNumber());
        assertEquals(8, game2.getDecks()[0].getCards().get(3).getNumber());
    }
}
