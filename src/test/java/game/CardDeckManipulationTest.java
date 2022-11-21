package game;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests the methods used to manipulate a CardDeck object (add and remove cards)
 */

public class CardDeckManipulationTest {

    CardDeck cardDeck;

    /**
     * Creates a random CardDeck to work on with the test methods
     */
    @Before
    public void createDeck(){
        LinkedList<Card> cardList = new LinkedList<>(List.of(new Card(10), new Card(5), new Card(4), new Card(9)));
        cardDeck = new CardDeck(2, cardList);
    }

    /**
     * Starting from the deck set up in the @Before method, it retrieves 2 cards and
     * checks the number of cards remaining and the number of the retrieved card
     */
    @Test
    public void testRetrieveTopCard() {
        Card topCard = cardDeck.retrieveTopCard();
        assertEquals(10, topCard.getNumber());
        assertEquals(3, cardDeck.getCards().size());

        topCard = cardDeck.retrieveTopCard();
        assertEquals(5, topCard.getNumber());
        assertEquals(2, cardDeck.getCards().size());
    }

    /**
     * Starting from the deck set up in the @Before method, it adds 2 cards and
     * checks the total number of cards and the presence of the new card in the deck
     */
    @Test
    public void testGiveCard() {
        cardDeck.giveCard(new Card(3));
        assertEquals(3, cardDeck.getCards().get(0).getNumber());
        assertEquals(5, cardDeck.getCards().size());

        cardDeck.giveCard(new Card(20));
        assertEquals(20, cardDeck.getCards().get(0).getNumber());
        assertEquals(6, cardDeck.getCards().size());
    }
}
