package game;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerTest {

    Player player1;

    @Before
    public void createPlayer(){
        player1 = new Player();
        player1.setPlayerNumber(3);
    }

    @Test
    public void testReorderHand(){
        player1.setHand(new ArrayList<>(List.of(new Card(3), new Card(5), new Card(3), new Card(4))));  //set the hand of the player
        //call the method
        player1.reorderHand();
        //we check if the last two cards of the hand are 3s
        assertEquals(3, player1.getHand().get(player1.getHand().size() - 1).getNumber());
        assertEquals(3, player1.getHand().get(player1.getHand().size() - 2).getNumber());

        //We use the same player with different hands
        player1.setHand(new ArrayList<>(List.of(new Card(4), new Card(5), new Card(2), new Card(8))));
        //there are no 3s so the reorderHand() method should do nothing
        player1.reorderHand();
        assertEquals(4, player1.getHand().get(0).getNumber());
        assertEquals(8, player1.getHand().get(player1.getHand().size() - 1).getNumber());

        //Let's try with a hand full of the preferred cards (it shouldn't throw exceptions)
        player1.setHand(new ArrayList<>(List.of(new Card(3), new Card(3), new Card(3), new Card(3))));
        //there are no 3s so the reorderHand() method should do nothing
        player1.reorderHand();
        assertEquals(3, player1.getHand().get(0).getNumber());
        assertEquals(3, player1.getHand().get(player1.getHand().size() - 1).getNumber());
    }

    @Test
    public void testCheckVictory() {
        //In the first case the player has not won
        player1.setHand(new ArrayList<>(List.of(new Card(3), new Card(5), new Card(3), new Card(4))));
        assertFalse(player1.checkVictory());

        player1.setHand(new ArrayList<>(List.of(new Card(3), new Card(3), new Card(3), new Card(3))));  //set the hand of the player
        assertTrue(player1.checkVictory());
    }

    @Test
    public void testTakeAndDiscard(){
        //we first set up a player with an ordered hand and its decks
        Player player = new Player();
        player.setPlayerNumber(3);
        player.setHand(new ArrayList<>(List.of(new Card(4), new Card(5), new Card(3), new Card(3))));
        player.setPreferredCards(2);
        player.setTakeDeck(new CardDeck(3, new LinkedList<>(List.of(new Card(5), new Card(3), new Card(6)))));
        player.setGiveDeck(new CardDeck(4, new LinkedList<>(List.of(new Card(4), new Card(5)))));

        //we can now run the method takeAndDiscard for the first time and we know that the player will draw a card which
        //is not a preferred one
        player.takeAndDiscard();
        assertEquals(2, player.getTakeDeck().getCards().size());
        assertEquals(3, player.getGiveDeck().getCards().size());
        assertEquals(4, player.getHand().size());
        assertEquals(5, player.getHand().get(0).getNumber());

        //again, with a preferred card
        player.takeAndDiscard();
        assertEquals(1, player.getTakeDeck().getCards().size());
        assertEquals(4, player.getGiveDeck().getCards().size());
        assertEquals(4, player.getHand().size());
        //the card was placed in the last position, so you have 3 cards with number 3 and they should be at the end of the hand
        assertEquals(3, player.getHand().get(1).getNumber());
        assertEquals(3, player.getHand().get(2).getNumber());
        assertEquals(3, player.getHand().get(3).getNumber());
    }

    @Test
    public void testHandToString(){
        Player player = new Player();
        player.setHand(new ArrayList<>(List.of(new Card(3), new Card(4), new Card(5))));
        assertEquals("3 4 5", player.handToString());
        player.setHand(new ArrayList<>());
        assertEquals("", player.handToString());
    }



}
