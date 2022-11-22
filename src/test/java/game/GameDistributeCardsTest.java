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

        /*
        This nested for loop is just used to create a specific pack that will distribute the same hand to each player of the game. The hand is "1 2 3 4".
        The decks will receive the sequence of cards "4 3 2 1" because the giveDeck() method to add cards inside a CardDeck adds card at the beginning
        of the data structure.
         */
        Card[] pack1 = new Card[40];
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 4; j++){
                for(int k = 0; k < 5; k++){
                    pack1[(i * 20) + (5 * j + k)] = new Card(j + 1);
                }
            }
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

        /*
        These nested for loops are equivalent to the previous ones and are used to give each player the hand "1 2 3 4" and
        each deck the sequence "4 3 2 1"
         */
        Card[] pack2 = new Card[16];
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 4; j++){
                for (int k = 0; k < 2; k++){
                    pack2[(i * 8) + (2 * j + k)] = new Card(j + 1);
                }
            }
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

        /* Verifies that the sequence of cards in some random players' hands is "1 2 3 4" */
        assertEquals(1, game1.getPlayers()[0].getHand().get(0).getNumber());
        assertEquals(2, game1.getPlayers()[0].getHand().get(1).getNumber());
        assertEquals(3, game1.getPlayers()[0].getHand().get(2).getNumber());
        assertEquals(4, game1.getPlayers()[0].getHand().get(3).getNumber());
        assertEquals(1, game1.getPlayers()[3].getHand().get(0).getNumber());
        assertEquals(2, game1.getPlayers()[3].getHand().get(1).getNumber());
        assertEquals(3, game1.getPlayers()[3].getHand().get(2).getNumber());
        assertEquals(4, game1.getPlayers()[3].getHand().get(3).getNumber());
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

        /* Verifies from some random decks that the sequence of distributed cards is "4 3 2 1" */
        assertEquals(4, game1.getDecks()[1].getCards().get(0).getNumber());
        assertEquals(3, game1.getDecks()[1].getCards().get(1).getNumber());
        assertEquals(2, game1.getDecks()[1].getCards().get(2).getNumber());
        assertEquals(1, game1.getDecks()[1].getCards().get(3).getNumber());
        assertEquals(4, game1.getDecks()[4].getCards().get(0).getNumber());
        assertEquals(3, game1.getDecks()[4].getCards().get(1).getNumber());
        assertEquals(2, game1.getDecks()[4].getCards().get(2).getNumber());
        assertEquals(1, game1.getDecks()[4].getCards().get(3).getNumber());
    }

    @Test
    public void testDistributeCardsGame2PlayersHands(){
        /* Calls the method of the Game class to test */
        game2.distributeCards();

        /* Checks the size of players' hands */
        assertEquals(4, game2.getPlayers()[0].getHand().size());
        assertEquals(4, game2.getPlayers()[1].getHand().size());

        /* Verifies that the sequence of cards in the players' hands is "1 2 3 4" */
        assertEquals(1, game2.getPlayers()[0].getHand().get(0).getNumber());
        assertEquals(2, game2.getPlayers()[0].getHand().get(1).getNumber());
        assertEquals(3, game2.getPlayers()[0].getHand().get(2).getNumber());
        assertEquals(4, game2.getPlayers()[0].getHand().get(3).getNumber());
        assertEquals(1, game2.getPlayers()[1].getHand().get(0).getNumber());
        assertEquals(2, game2.getPlayers()[1].getHand().get(1).getNumber());
        assertEquals(3, game2.getPlayers()[1].getHand().get(2).getNumber());
        assertEquals(4, game2.getPlayers()[1].getHand().get(3).getNumber());
    }

    @Test
    public void testDistributeCardsGame2Decks(){
        /* Calls the method of the Game class to test */
        game2.distributeCards();

        /* Checks the size of the internal CardDeck's data structure */
        assertEquals(4, game2.getDecks()[0].getCards().size());
        assertEquals(4, game2.getDecks()[1].getCards().size());

        /* Verifies from the decks that the sequence of distributed cards is "4 3 2 1" */
        assertEquals(4, game2.getDecks()[1].getCards().get(0).getNumber());
        assertEquals(3, game2.getDecks()[1].getCards().get(1).getNumber());
        assertEquals(2, game2.getDecks()[1].getCards().get(2).getNumber());
        assertEquals(1, game2.getDecks()[1].getCards().get(3).getNumber());
        assertEquals(4, game2.getDecks()[0].getCards().get(0).getNumber());
        assertEquals(3, game2.getDecks()[0].getCards().get(1).getNumber());
        assertEquals(2, game2.getDecks()[0].getCards().get(2).getNumber());
        assertEquals(1, game2.getDecks()[0].getCards().get(3).getNumber());
    }
}
