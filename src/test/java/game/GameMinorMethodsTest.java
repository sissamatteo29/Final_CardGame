package game;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertNotEquals;

/**
 * Tests some minor methods in the Game class
 */
public class GameMinorMethodsTest {

    /**
     * Tests the createPlayersAndDecks method by checking if random elements inside the Players[] and Decks[] arrays are null
     */
    @Test
    public void testCreatePlayersAndDecks() {
        Game trialGame = new Game();
        trialGame.setNumberOfPlayers(4);
        trialGame.createPlayersAndDecks();
        assertNotEquals(trialGame.getPlayers()[3], null);
        assertNotEquals(trialGame.getPlayers()[0], null);
        assertNotEquals(trialGame.getDecks()[2], null);
        assertNotEquals(trialGame.getDecks()[1], null);
    }

    /**
     * Test method to verify if the testGenerateLog method can write on the file
     * @throws Exception
     */
    @Test
    public void testGenerateLog() throws Exception {
        Game game = new Game();
        File testGenerateLog = new File("testGenerateLog.txt");
        try {
            testGenerateLog.createNewFile();
            game.generateLog("testGenerateLog.txt", "Hello World!");
        } catch (Exception e){
            System.out.println("Test testGenerateLog has failed to" +
                    " create the file or something went wrong with the generateLog method in the Game class");
            throw new Exception();
        }
    }
}
