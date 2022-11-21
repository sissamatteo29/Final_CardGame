package game;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the readFile() method inside the Game class
 */
public class GameReadFileTest {

    static File cards1, cards2, cards3, cards4;
    static Game game;
    static PrintStream saveOutput;

    /**
     * Creates 4 different files, 3 of them containing errors and the last one correctly formatted
     * to test the Game class
     * @throws IOException
     */
    @BeforeClass
    public static void createFilesAndGame() throws IOException {
        /* Sets up an instance of the Game class with the few necessary members */
        game = new Game();
        game.setNumberOfPlayers(3);
        game.setPack(new Card[3 * 8]);

        /* Modifies the standard output to avoid error messages on the console every time the testing method runs */
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        saveOutput = System.out;       //Backup of the standard output
        System.setOut(printStream);

        /* First file */
        cards1 = new File("cards1.txt");
        try {
            cards1.createNewFile();
        } catch (IOException e) {
            System.out.println("Failed creating the file cards1.txt");
            e.printStackTrace();
        }

        Writer writeFile1 = new FileWriter(cards1);
        String sequence = "1\n5\ng\n9\n";   //g is a wrong format
        for (int i = 0; i < 6; i++){
            writeFile1.write(sequence);
        }
        writeFile1.close();

        /* Second file */
        cards2 = new File("cards2.txt");
        try {
            cards2.createNewFile();
        } catch (IOException e) {
            System.out.println("Failed creating the file cards2.txt");
            e.printStackTrace();
        }

        Writer writeFile2 = new FileWriter(cards2);
        sequence = "1\n5\n-22\n9\n";   //-22 is negative
        for (int i = 0; i < 6; i++){
            writeFile2.write(sequence);
        }
        writeFile2.close();

        /* Third file */
        cards3 = new File("cards3.txt");
        try {
            cards3.createNewFile();
        } catch (IOException e) {
            System.out.println("Failed creating the file cards3.txt");
            e.printStackTrace();
        }

        Writer writeFile3 = new FileWriter(cards3);
        sequence = "1\n5\n22\n";        //correct format but only 18 cards
        for (int i = 0; i < 6; i++){
            writeFile3.write(sequence);
        }
        writeFile3.close();

        /* Fourth file */
        cards4 = new File("cards4.txt");
        try {
            cards4.createNewFile();
        } catch (IOException e) {
            System.out.println("Failed creating the file cards4.txt");
            e.printStackTrace();
        }

        Writer writeFile4 = new FileWriter(cards4);
        sequence = "1\n5\n22\n9\n";         //Correct format and 24 cards (3 players)
        for (int i = 0; i < 6; i++){
            writeFile4.write(sequence);
        }
        writeFile4.close();

    }

    @Test
    public void testReadFileWrongFormat(){
        assertFalse(game.readFile(cards1.getName()));
    }

    @Test
    public void testReadFileNegativeNumber(){
        assertFalse(game.readFile(cards2.getName()));
    }

    @Test
    public void testReadFileNotEnoughCards(){
        assertFalse(game.readFile(cards3.getName()));
    }

    @Test
    public void testReadFileCorrect(){
        assertTrue(game.readFile(cards4.getName()));
    }

    @AfterClass
    public static void restoreStandardOutputAndDeleteFiles(){
        System.setOut(saveOutput);
        cards1.delete();
        cards2.delete();
        cards3.delete();
        cards4.delete();

    }
}
