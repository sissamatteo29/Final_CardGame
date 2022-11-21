package game;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Tests the minor methods inside the Player class
 */
public class PlayerMinorMethodsTest {

    Player player;

    @Before
    public void initPlayer(){
        player = new Player();
        player.setPlayerNumber(3);
    }

    /**
     * Tests if the checkVictory() method works with different initial hands of the player
     */
    @Test
    public void testCheckVictory() {
        player.setHand(new ArrayList<>(List.of(new Card(3), new Card(5), new Card(3), new Card(4))));
        assertFalse(player.checkVictory());

        player.setHand(new ArrayList<>(List.of(new Card(3), new Card(3), new Card(3), new Card(3))));  //set the hand of the player
        assertTrue(player.checkVictory());
    }

    /**
     * Tests the conversion of the player's hand into a string format
     */
    @Test
    public void testHandToString(){
        player.setHand(new ArrayList<>(List.of(new Card(3), new Card(4), new Card(5))));
        assertEquals("3 4 5", player.handToString());
        player.setHand(new ArrayList<>());
        assertEquals("", player.handToString());
    }
}
