package game;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
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
    public void testCheckVictory(){
        //In the first case the player has not won
        player1.setHand(new ArrayList<>(List.of(new Card(3), new Card(5), new Card(3), new Card(4))));
        assertFalse(player1.checkVictory());

        player1.setHand(new ArrayList<>(List.of(new Card(3), new Card(3), new Card(3), new Card(3))));  //set the hand of the player
        assertTrue(player1.checkVictory());
    }

}