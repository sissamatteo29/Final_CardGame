package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Represents a player in the game
 */
public class Player implements Runnable {
    private int playerNumber;
    private ArrayList<Card> hand;
    private int preferredCards;
    private boolean keepGoing = true;
    private Game game;
    private CardDeck takeDeck, giveDeck;

    //Essential constructor
    public Player(int playerNumber, Game game) {
        this.playerNumber = playerNumber;
        this.game = game;
        hand = new ArrayList<>();
    }

    public Player(){

    }

    public Player(int playerNumber, ArrayList<Card> hand, int preferredCards, boolean keepGoing, Game game, CardDeck takeDeck, CardDeck giveDeck) {
        this.playerNumber = playerNumber;
        this.hand = hand;
        this.preferredCards = preferredCards;
        this.keepGoing = keepGoing;
        this.game = game;
        this.takeDeck = takeDeck;
        this.giveDeck = giveDeck;
    }

    @Override
    public void run() {

        //We immediately check if the current player has already won at the beginning
        if (checkVictory()){
            game.endGame();
            System.out.println("Player " + playerNumber + " wins!");
        }

        //Reorder the cards in the current hand by putting the "preferred" cards at the end of the data structure and counting how many preferred cards we have at the very beginning
        reorderHand();

        Card newCard;
        //Every player iterates over until its boolean value keepGoing is set to true
        while (keepGoing){

            if (checkVictory()){
                game.endGame();
                System.out.println("Player " + playerNumber + " wins!");
            }

            //In every cycle, we first take a card from the takeDeck
            newCard = takeDeck.retrieveTopCard();

            if (newCard.getNumber() == playerNumber){
                //In this case we need to hold the card and place it at the end of the list
                hand.add(newCard);
                preferredCards++;
            } else {
                //we just place the new card at the beginning of the list
                hand.add(0, newCard);
            }

            //Now we generate a random number to decide which card to discard, without considering the preferred cards
            Random myRandom = new Random();
            giveDeck.addCard(hand.remove(myRandom.nextInt(0, hand.size() - 1 - preferredCards)));   //remove method returns the object removed
        }
    }

    /*
        A function that takes all the preferred cards of one hand and puts them at the end of the list
    */
    private void reorderHand() {
        preferredCards = 0;
        for(int i = 0; i < hand.size() - preferredCards; i++){
            if (hand.get(i).getNumber() == playerNumber){
                Collections.swap(hand, i, hand.size() - 1 - preferredCards);
                preferredCards++;
                i--;
            }
        }
    }

    private boolean checkVictory() {
        boolean victory = true;
        //Compare the current card number with the successive one
        for (int i = 0; i < hand.size() - 1; i++) {
            if (hand.get(i).getNumber() != hand.get(i+1).getNumber()) {
                victory = false;
            }
        }
        return victory;
    }


    public CardDeck getTakeDeck() {
        return takeDeck;
    }

    public void setTakeDeck(CardDeck takeDeck) {
        this.takeDeck = takeDeck;
    }

    public CardDeck getGiveDeck() {
        return giveDeck;
    }

    public void setGiveDeck(CardDeck giveDeck) {
        this.giveDeck = giveDeck;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public boolean isKeepGoing() {
        return keepGoing;
    }

    public void setKeepGoing(boolean keepGoing) {
        this.keepGoing = keepGoing;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}