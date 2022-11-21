package game;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Class providing the methods to test some minor CardDeck's methods
 */
public class CardDeckUtilityMethodsTest {

    /**
     * Test on empty and non-empty decks
     */
    @Test
    public void testIsNotEmpty(){
        CardDeck cardDeck = new CardDeck(1, new LinkedList<>(List.of(new Card(3))));
        assertTrue(cardDeck.isNotEmpty());
        cardDeck.setCards(new LinkedList<>());
        assertFalse(cardDeck.isNotEmpty());
    }

    /**
     * Tests if the conversion into String works properly
     */
    @Test
    public void testGetCardsString(){
        CardDeck cardDeck = new CardDeck(1, new LinkedList<>(List.of(new Card(3), new Card(4), new Card(5))));
        assertEquals("3 4 5", cardDeck.getCardsString());
        cardDeck.setCards(new LinkedList<>());
        assertEquals("", cardDeck.getCardsString());
    }


}