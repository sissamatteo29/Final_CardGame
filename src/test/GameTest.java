package game;


import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class GameTest {

    GameInterface myTestGame1;
    GameInterface myTestGame2;
    static File cards1, cards2, cards3, cards4;

    @BeforeClass
    public static void createFIle() throws IOException {

        cards1 = new File("cards1.txt");
        try {
            cards1.createNewFile();
        } catch (IOException e) {
            System.out.println("Failed creating the file cards1.txt");
            e.printStackTrace();
        }

        //Let's fill this File with numbers, and we will also place inside the file a wrong format to check if the method recognizes it
        Writer writeFile1 = new FileWriter(cards1);
        String sequence = "1\n5\ng\n9\n";   //note that g is a wrong format
        for (int i = 0; i < 6; i++){
            writeFile1.write(sequence);
        }
        writeFile1.close();

        //we need another file to check if the function recognizes negative values
        cards2 = new File("cards2.txt");
        try {
            cards2.createNewFile();
        } catch (IOException e) {
            System.out.println("Failed creating the file cards2.txt");
            e.printStackTrace();
        }

        Writer writeFile2 = new FileWriter(cards2);
        sequence = "1\n5\n-22\n9\n";   //note that -22 is negative
        for (int i = 0; i < 6; i++){
            writeFile2.write(sequence);
        }
        writeFile2.close();

        //We need another file with all the correct formats for the cards, but with less cards than the required number
        cards3 = new File("cards3.txt");
        try {
            cards3.createNewFile();
        } catch (IOException e) {
            System.out.println("Failed creating the file cards3.txt");
            e.printStackTrace();
        }

        Writer writeFile3 = new FileWriter(cards3);
        sequence = "1\n5\n22\n";   //right format, 18 cards total
        for (int i = 0; i < 6; i++){
            writeFile3.write(sequence);
        }
        writeFile3.close();

        //Finally, we need a correct file as input
        cards4 = new File("cards4.txt");
        try {
            cards4.createNewFile();
        } catch (IOException e) {
            System.out.println("Failed creating the file cards4.txt");
            e.printStackTrace();
        }

        Writer writeFile4 = new FileWriter(cards4);
        sequence = "1\n5\n22\n9\n";   //24 cards total so 3 players
        for (int i = 0; i < 6; i++){
            writeFile4.write(sequence);
        }
        writeFile4.close();

    }

    @Before
    public void createTwoNewGames(){

        myTestGame1 = new Game();
        myTestGame2 = new Game();

        Card[] pack1 = new Card[40];
        for (int i = 0; i < 40; i++){
            pack1[i] = new Card(3);
        }

        Player[] players1 = new Player[5];
        CardDeck[] decks1 = new CardDeck[5];
        for (int i = 0; i < 5; i++){
            players1[i] = new Player(i + 1, myTestGame1);
            decks1[i] = new CardDeck(i + 1);
        }

        myTestGame1.setNumberOfPlayers(5);
        myTestGame1.setDecks(decks1);
        myTestGame1.setPack(pack1);
        myTestGame1.setPlayers(players1);

        Card[] pack2 = new Card[16];
        for (int i = 0; i < 16; i++){
            pack2[i] = new Card(8);
        }

        Player[] players2 = new Player[2];
        CardDeck[] decks2 = new CardDeck[2];
        for (int i = 0; i < 2; i++){
            players2[i] = new Player(i + 1, myTestGame2);
            decks2[i] = new CardDeck(i + 1);
        }

        myTestGame2.setPlayers(players2);
        myTestGame2.setPack(pack2);
        myTestGame2.setDecks(decks2);
        myTestGame2.setNumberOfPlayers(2);
    }

    @Test
    public void testAskNumberOfPlayers(){

        //Let's test the input with multiple examples, both correct and wrong
        String numberOfPlayerInput = "tt\n7\nC5\n";
        //We create an inputStream to simulate the standard input of the user
        ByteArrayInputStream inputStream = new ByteArrayInputStream(numberOfPlayerInput.getBytes());
        //Save the reference to the standard input
        InputStream stdin = System.in;
        System.setIn(inputStream);

        //We also need to modify the standard output of the program
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream stdout = System.out;
        System.setOut(printStream);

        //Now we can call the method to be tested, it will run as usual, but the standard input and output of the program have been modified
        Game testGame = new Game();
        testGame.askNumberOfPlayers();

        System.setOut(stdout);

        //Now we can check if the output is correct
        String resultingString = outputStream.toString();
        System.out.println(resultingString);
        //We only need the last print of the entire method, so we can extract it
        int index = resultingString.indexOf("The game");
        resultingString = resultingString.substring(index);
        //We can now split the string into words
        String[] splitResultingString = resultingString.split(" ");
        assertEquals("The initial method to get the user's input for the number of players " +
                "is not working properly", "7", splitResultingString[4]);

        //We set back the standard input and output
        System.setIn(stdin);
    }

    @Test
    public void testAskFileName(){

        //Let's test the input with multiple examples, both correct and wrong
        String numberOfPlayerInput = "Rome\ntt\ncards1.txt\nC5\n";
        //We create an inputStream to simulate the standard input of the user
        ByteArrayInputStream inputStream = new ByteArrayInputStream(numberOfPlayerInput.getBytes());
        //Save the reference to the standard input
        InputStream stdin = System.in;
        System.setIn(inputStream);

        //We also need to modify the standard output of the program
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        PrintStream stdout = System.out;
        System.setOut(printStream);

        //Now we can call the method to be tested, it will run as usual, but the standard input and output of the program have been modified
        Game testGame = new Game();
        testGame.askFileName();

        //Now we can check if the output is correct
        String resultingString = outputStream.toString();
        //We only need the last print of the entire method, so we can extract it
        int index = resultingString.indexOf("Selected");
        resultingString = resultingString.substring(index);
        //We can now split the string into words
        String[] splitResultingString = resultingString.split(" ");
        assertEquals("The initial method to get the user's input for the textfile" +
                " is not working properly", "cards1.txt", splitResultingString[2].strip());

        //We set back the standard input and output
        System.setIn(stdin);
        System.setOut(stdout);
    }

    @Test
    public void testReadFile(){
        //the method readFile needs to have a reference to a game with a certain amount of player and a "pack" vector where it stores the cards
        Game game = new Game(3);
        game.setPack(new Card[3 * 8]);

        //now we can call the method with the various files created and be sure it returns true or false
        assertFalse(game.readFile(cards1.getName()));
        assertFalse(game.readFile(cards2.getName()));
        assertFalse(game.readFile(cards3.getName()));
        assertTrue(game.readFile(cards4.getName()));
    }

    @Test
    public void testCreatePlayersAndDecks() {
        Game trialGame = new Game(4);
        trialGame.createPlayersAndDecks();
        assertNotEquals(trialGame.getPlayers()[3], null);
        assertNotEquals(trialGame.getPlayers()[0], null);
        assertNotEquals(trialGame.getDecks()[2], null);
        assertNotEquals(trialGame.getDecks()[1], null);
    }

    @Test
    public void testDistributeCardsForGame1() {
        myTestGame1.distributeCards();
        //Verifying the length of some random players' hands
        assertEquals(4, myTestGame1.getPlayers()[1].getHand().size());
        assertEquals(4, myTestGame1.getPlayers()[0].getHand().size());

        //verifying that some cards in players' hands are equal to 3
        assertEquals(3, myTestGame1.getPlayers()[3].getHand().get(2).getNumber());
        assertEquals(3, myTestGame1.getPlayers()[0].getHand().get(3).getNumber());

        //verifying the same things for the decks
        assertEquals(4, myTestGame1.getDecks()[0].getCards().size());
        assertEquals(4, myTestGame1.getDecks()[3].getCards().size());

        assertEquals(3, myTestGame1.getDecks()[0].getCards().get(2).getNumber());
        assertEquals(3, myTestGame1.getDecks()[0].getCards().get(3).getNumber());
    }

    @Test
    public void testDistributeCardsForGame2(){  //2 players
        myTestGame2.distributeCards();
        //Verifying the length of some random players' hands
        assertEquals(4, myTestGame2.getPlayers()[1].getHand().size());
        assertEquals(4, myTestGame2.getPlayers()[0].getHand().size());

        //verifying that some cards in players' hands are equal to 8
        assertEquals(8, myTestGame2.getPlayers()[1].getHand().get(2).getNumber());
        assertEquals(8, myTestGame2.getPlayers()[0].getHand().get(3).getNumber());

        //verifying the same things for the decks
        assertEquals(4, myTestGame2.getDecks()[0].getCards().size());
        assertEquals(4, myTestGame2.getDecks()[1].getCards().size());

        assertEquals(8, myTestGame2.getDecks()[0].getCards().get(2).getNumber());
        assertEquals(8, myTestGame2.getDecks()[0].getCards().get(3).getNumber());
    }



    @Test
    public void startThreads() {
    }

    @Test
    public void endGame() {
    }

    @Test
    public void winner() {
    }

    @AfterClass
    public static void afterClass(){
        //deleting the file generated
        cards1.delete();
        cards2.delete();
        cards3.delete();
        cards4.delete();
    }
}