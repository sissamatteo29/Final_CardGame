package game;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Tests the reorderHand() method inside the Player class
 */
public class PlayerReorderHandTest {

    static Player player;

    @BeforeClass
    public static void initPlayer(){
        player = new Player();
        player.setPlayerNumber(3);
    }

    @Test
    public void testReorderHandWithTwoPreferredCards(){
        player.setHand(new ArrayList<>(List.of(new Card(3), new Card(5), new Card(3), new Card(4))));  //sets the hand of the player
        player.reorderHand();
        /* Checks if the last 2 cards of the player's hand are 3s */
        assertEquals(3, player.getHand().get(player.getHand().size() - 1).getNumber());
        assertEquals(3, player.getHand().get(player.getHand().size() - 2).getNumber());
    }

    @Test
    public void testReorderHandWithNoPreferredCards(){
        player.setHand(new ArrayList<>(List.of(new Card(4), new Card(5), new Card(2), new Card(8))));
        player.reorderHand();

        /* Since there are no preferred cards in the hand, the reorderHand() method shouldn't do anything */
        assertEquals(4, player.getHand().get(0).getNumber());
        assertEquals(8, player.getHand().get(player.getHand().size() - 1).getNumber());
    }

    @Test
    public void testReorderHandWithFullHandPreferredCards(){
        player.setHand(new ArrayList<>(List.of(new Card(3), new Card(3), new Card(3), new Card(3))));
        /* The reorderHand() method shouldn't do anything and should not throw any exception */
        player.reorderHand();
        assertEquals(3, player.getHand().get(0).getNumber());
        assertEquals(3, player.getHand().get(player.getHand().size() - 1).getNumber());
    }

}
