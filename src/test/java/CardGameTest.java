import game.CardGame;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;


public class CardGameTest {

    @Test
    public void testUserInputInitialPhase(){

        //Let's test the input with multiple examples, both correct and wrong
        String numberOfPlayerInput = "Rome \n 7 \n randomfile \n cards.txt";
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
        CardGame testGame = new CardGame();
        testGame.userInputInitialPhase();

        //Now we can check if the output is correct
        String resultingString = outputStream.toString();
        //We only need the last print of the entire method, so we can extract it
        int index = resultingString.indexOf("Preparing");
        resultingString = resultingString.substring(index);
        //We can now split the string into words
        String[] splitResultingString = resultingString.split(" ");
        assertEquals("7", splitResultingString[4],"The initial method to get the user's input is not working properly");

        //We set back the standard input and output
        System.setIn(stdin);
        System.setOut(stdout);
    }






}
