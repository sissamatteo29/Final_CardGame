package game;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests the takeAndDiscard() method inside the Player class
 */
public class PlayerTakeAndDiscardTest {

    static Player player1;
    static Player player2;

    @BeforeClass
    public static void initPlayer() {
        /* player1 gets a takeDeck in which the first card is NOT a preferred one */
        player1 = new Player();
        player1.setPlayerNumber(3);
        player1.setHand(new ArrayList<>(List.of(new Card(4), new Card(5), new Card(3), new Card(3))));       //Already reordered hand
        player1.setPreferredCards(2);
        player1.setTakeDeck(new CardDeck(3, new LinkedList<>(List.of(new Card(5), new Card(3), new Card(6)))));
        player1.setGiveDeck(new CardDeck(4, new LinkedList<>(List.of(new Card(4), new Card(5)))));

        /* player2 gets a takeDeck in which the first card is a preferred one */
        player2 = new Player();
        player2.setPlayerNumber(3);
        player2.setHand(new ArrayList<>(List.of(new Card(4), new Card(5), new Card(3), new Card(3))));       //Already reordered hand
        player2.setPreferredCards(2);
        player2.setTakeDeck(new CardDeck(3, new LinkedList<>(List.of(new Card(3), new Card(3), new Card(6)))));
        player2.setGiveDeck(new CardDeck(4, new LinkedList<>(List.of(new Card(4), new Card(5)))));
    }

    @Test
    public void testTakeAndDiscardWithoutPreferredCard() {
        player1.takeAndDiscard();
        assertEquals(2, player1.getTakeDeck().getCards().size());    //Checks decks new sizes
        assertEquals(3, player1.getGiveDeck().getCards().size());
        assertEquals(4, player1.getHand().size());                   //Checks player's hand size
        assertEquals(3, player1.getHand().get(2).getNumber());
        assertEquals(3, player1.getHand().get(3).getNumber());
    }

    @Test
    public void testTakeAndDiscardWithPreferredCard() {
        player2.takeAndDiscard();
        assertEquals(2, player2.getTakeDeck().getCards().size());    //Checks decks new sizes
        assertEquals(3, player2.getGiveDeck().getCards().size());
        assertEquals(4, player2.getHand().size());                   //Checks player's hand size
        assertEquals(3, player2.getHand().get(1).getNumber());       //There should be 3 preferred cards at the end of the hand
        assertEquals(3, player2.getHand().get(2).getNumber());
        assertEquals(3, player2.getHand().get(3).getNumber());
    }

}
