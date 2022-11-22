package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Player implements Runnable {

    private int playerNumber;
    private Game game;
    private int preferredCards;
    private ArrayList<Card> hand = new ArrayList<>();
    private CardDeck takeDeck, giveDeck;
    private Boolean gameOver = false;
    private ArrayList<String> log = new ArrayList<>();

    /**
     * Constructs the player, sets the player number and game interface to the parameters.
     * @param number The number of the player
     * @param game The interface of the game
     */
    public Player(int number, Game game) {
        this.playerNumber = number;
        this.game = game;
    }

    /**
     * Constructs the player without any parameters, used for testing. 
     */
    public Player() {

    }

    /**
     * The main function for the player.
     * 
     * Checks if the player has won the game if they have the relevant code is activated.
     * Checks if another player has won the game, if they have the relevant code is activated.
     * If they haven't a card is drawn and discarded, if the draw deck is empty the player waits.
     * 
     * Adds details of the player to the log.
     * 
     */
    public void run() {
        //Sorts the players hand putting the priority cards at the back of the hand.
        reorderHand();
        //Logs the players initial hand
        log.add("Player " + playerNumber + " initial hand: " + handToString());

        //While the game is still being played.
        while (!gameOver) {
            //If the player has 4 cards of the same value.
            if (checkVictory()) {
                //Checks to see if the Atomic Integer in the game class is 0 meaning no one else
                //has won yet, then Atomic Integer is set to the player number.
                game.compareAndSet(0, playerNumber);
                //Sets gameOver to true, notifies the deck's monitors, creates the relevant log.
                exit(true);
                break;
            } else if (game.returnWinner() != 0) {
                //Creates the relevant log for losing.
                exit(false);
                break;
            }

            //Checks to see if the left deck (draw deck) has cards in it.
            if (takeDeck.isNotEmpty()) {
                //Takes a card from the left deck and discards one to the right deck.
                try {
                    takeAndDiscard();
                } catch(Exception e) { }
            } else {
                //If Left deck empty will wait until cards have been added.
                synchronized (takeDeck) {
                    try {
                        takeDeck.wait();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        }

    }

    /**
     * By comparing the values of the cards in th players hand, this function determines if the
     * player has won.
     * @return True if the player has won, false otherwise.
     */
    public boolean checkVictory() {
        boolean victory = true;
        //Compare the current card number with the successive one
        for (int i = 0; i < hand.size() - 1; i++) {
            if (hand.get(i).getNumber() != hand.get(i + 1).getNumber()) {
                victory = false;
                break;
            }
        }
        //If all of the cards match the player has won.
        return victory;
    }

    /**
     * Re-orders the players initial hand putting the preferred cards at the back.
     */
    public void reorderHand() {
        preferredCards = 0;
        for (int i = 0; i < hand.size() - preferredCards; i++) {
            //If the number of the card matches the player number it is a preferred card.
            if (hand.get(i).getNumber() == playerNumber) {
                //Swaps the preferred card with the last in the hand non preferred card.
                Collections.swap(hand, i, hand.size() - 1 - preferredCards);
                preferredCards++;
                i--;
            }
        }
    }

    /**
     * Takes a new card from the left deck, checks if the card matches the player number, if so
     * the card is added at the back of the players hand and the integer tracking the number of
     * preferred cards increases by 1. If the card isn't a preferred card it gets added to the 
     * front of his hand.
     * 
     * Discards a non priority card and adds the card to the bottom of the right deck. 
     * 
     * Writes the information to the log.
     */
    public void takeAndDiscard() {
        //The new card the player drew.
        Card newCard = takeDeck.retrieveTopCard();

        //If the new card matches the player number it is a preferred card.
        if (newCard.getNumber() == playerNumber) {
            //Since it is a preferred card it gets added to the back of the player's hand.
            hand.add(newCard);
            preferredCards++;
        } else {
            //Since the card isn't a preferred card it gets placed at the front of the deck.
            hand.add(0, newCard);
        }

        Card discardCard = null;
        int nonPreferred = 5 - preferredCards;      //At this stage the cards inside the player's hand are 5
        if (nonPreferred == 1){
            discardCard = hand.remove(0);
        } else {
            Random random = new Random();
            discardCard = hand.remove(random.nextInt(nonPreferred));
        }

        giveDeck.giveCard(discardCard);

        //Adds the draw and discard card information to the log.
        appendToLog(newCard, discardCard);
    }

    /**
     * This function is used when the player loses or wins the game. The relevant
     * information is added onto the log, and the log is generated. If the player
     * has won it notifies all of the other threads waiting on decks to receive cards.
     * @param win True if the player won, false if the player lost.
     */
    private void exit(boolean win) {
        //Since this function only gets called when the player wins or loses, the game ends.
        gameOver = true;
        if (win) {
            log.add(String.format("Player %d wins", playerNumber));
            //Stops all of the monitors of the deck.
            game.stopDeckMonitor();
        } else {
            //Retrieves the winner of the game and logs the relevant information.
            int winner = game.returnWinner();
            log.add(String.format("Player %d has informed player %d that player %d has won",
                    winner, playerNumber, winner));
        }
        //Logs the players final hand.
        log.add(String.format("Player %d Exits\nPlayer %d final hand %s",
                playerNumber, playerNumber, handToString()));
        //Creates a new text file with the players log.
        game.generateLog("player" + playerNumber + "_output.txt", String.join("\n", log));
    }

    /**
     * Writes to the log which card the player drew, discarded and the current hand of the player.
     * @param drawn The card the player drew.
     * @param discarded The card the player discarded.
     */
    private void appendToLog(Card drawn, Card discarded) {
        //Logs the value of the card drawn and the number of the deck the card is taken from.
        log.add(String.format("Player %d draws a %d from deck %d", playerNumber, drawn.getNumber(),
        takeDeck.getDeckNumber()));
        //Logs the value of the card discarded and the number of the deck the card is discarded to.
        log.add(String.format("Player %d discards a %d to deck %d", playerNumber,
        discarded.getNumber(), giveDeck.getDeckNumber()));
        //Logs the player's hand after the taking and discarding a card.
        log.add(String.format("Player %d current hand: %s", playerNumber, handToString()));
    }

    /**
     * Cycles through the hand of the player, adding the values of the cards onto a string
     * and returning that sting.
     * @return The values of the cards in the players hand in string format.
     */
    public String handToString() {
        StringBuilder handString = new StringBuilder();
        for (int i = 0; i < hand.size(); i++) {
            handString.append(hand.get(i).getNumber());
            //Adds a blank space between the cards
            if (i != hand.size() - 1) {
                handString.append(" ");
            }
        }
        return handString.toString();
    }

    /**
     * Returns the number of the player.
     * @return The integer number of the player.
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * Sets the number of the player, used for test purposes.
     * @param playerNumber The number of the player.
     */
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    /**
     * Returns an array list of cards representing the players current hand,
     * used for test purposes.
     * @return An ArrayList of card objects.
     */
    public ArrayList<Card> getHand() {
        return hand;
    }

    /**
     * Sets the hand of the player, used for test purposes.
     * @param hand An ArrayList of card objects.
     */
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    /**
     * Adds a card to the player, used when distributing cards.
     * @param card The card object being added.
     */
    public void addCard(Card card) {
        hand.add(card);
    }

    /**
     * Returns the deck the player takes cards from, used for testing.
     * @return The CardDeck object of the player's left deck.
     */
    public CardDeck getTakeDeck() {
        return takeDeck;
    }

    /**
     * Sets the deck the player is going to take cards from.
     * @param takeDeck The deck object of the player's left deck.
     */
    public void setTakeDeck(CardDeck takeDeck) {
        this.takeDeck = takeDeck;
    }

    /**
     * Returns the deck the player discards on to, used for test purposes.
     * @return The deck object of the player's right deck.
     */
    public CardDeck getGiveDeck() {
        return giveDeck;
    }

    /**
     * Sets the deck the player will discard cards on to.
     * @param giveDeck The deck object of the player's right deck.
     */
    public void setGiveDeck(CardDeck giveDeck) {
        this.giveDeck = giveDeck;
    }

    /**
     * Returns the number of cards the player has that match his/her player number.
     * Used for test purposes.
     * @return The number of preferred cards the player has. 
     */
    public int getPreferredCards() {
        return preferredCards;
    }

    /**
     * Sets the number of preferred cards the player has, used for test purposes.
     * @param preferredCards The number of preferred cards.
     */
    public void setPreferredCards(int preferredCards) {
        this.preferredCards = preferredCards;
    }
}
