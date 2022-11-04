package game;
import java.util.*;
import java.io.*;

public class Game {
    private int numberOfPlayers;
    private Card[] pack;
    // private Player[] players;
    // private CardDeck[] decks;
    
    public String winner(){
        return "winner";
    }

    Game(){
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("Please enter the number of players: ");
            try {
                int number = scanner.nextInt();
                if (number > 1) {
                    numberOfPlayers = number;
                    break;
                } else {
                    System.out.println("Number of players must be more than 1.");
                }
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("Number of players must be an integer.");
            }
        }

        while (true) {
            System.out.println("Please enter location of pack to load:");
            String fileName = scanner.next();
            File inputFile = new File(fileName);
            if(inputFile.exists()){
                if (readFile(fileName) == true){
                    break;
                }
                
            }else if(pack.length != 8 * numberOfPlayers){
                System.out.println(String.format("The file doesn't have the right number of cards."));
            }else {
                System.out.println(String.format("The file doesn't exist."));
            }
        }

        scanner.close();
        
    }
    private boolean readFile(String fileName){
        try {
            pack = new Card[8*numberOfPlayers];
            int index = 0;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while (index < (8 * numberOfPlayers)) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                } else {
                    int value = 0;
                    try {
                        value = Integer.parseInt(line);
                        if (value > 0) {
                            pack[index] = new Card(value);
                        } else {
                            System.out.println("On line " + (index + 1) + " there was a number less that 1");
                            reader.close();
                            return false;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("On line " + (index +1 ) + " there was a wrong format");
                        reader.close();
                        return false;
                    }
                    
                }
                index++;
                
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            return false;
        }
        return true;
    }
}
