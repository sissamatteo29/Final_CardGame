package game;

import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class CardDeckTest {

    CardDeckInterface cardDeck;

    @Before
    public void createDeck(){
        LinkedList<Card> cardList = new LinkedList<>(List.of(new Card(10), new Card(5), new Card(4), new Card(9)));
        cardDeck = new CardDeck(2, cardList);
    }

    @Test
    public void testRetrieveTopCard() {
        Card topCard = cardDeck.retrieveTopCard();
        assertEquals(10, topCard.getNumber());
        assertEquals(3, cardDeck.getCards().size());

        topCard = cardDeck.retrieveTopCard();
        assertEquals(5, topCard.getNumber());
        assertEquals(2, cardDeck.getCards().size());

    }

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