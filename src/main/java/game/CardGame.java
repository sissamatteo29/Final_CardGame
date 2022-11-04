package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Runs the game
 */
public class CardGame {

    Game game;

    //This method provides all the initial computation to ask the user the initial data
    public void userInputInitialPhase() {

        //To start a new game, we first need to ask the user the number of players and the text file.
        //We use the Scanner class and store the infos in a Game object
        Scanner scanner = new Scanner(System.in);
        game = new Game();

        //First we ask for the number of players
        //The app is going to ask for the correct input until the user provides one
        while (true) {
            try {
                System.out.println("Please enter the number of players:");
                game.setNumberOfPlayers(scanner.nextInt());
                break;
            } catch (InputMismatchException e) {
                System.out.println("The number of players needs to be an integer greater than 1");
                scanner.next();
            }
        }

        //Then we ask the text file name and verify if that exists. If it doesn't the system asks again for a correct input
        while (true) {
            System.out.println("Type in the name of the text file:");
            File inputFile = new File(scanner.next());
            if(inputFile.exists()){
                game.setInputFile(inputFile);
                break;
            } else {
                System.out.println(String.format("The file %s doesn't exist!", inputFile.getName()));
            }
        }

        System.out.println(String.format("Preparing the game for %d players from the deck %s...", game.getNumberOfPlayers(), game.getInputFile().getName()));
    }

    public static void main(String[] args) {

        CardGame newGame = new CardGame();
        newGame.userInputInitialPhase();

    }
}
