package game;

import java.util.LinkedList;

/**
 * Represents a card deck in the game.
 */
public class CardDeck implements CardDeckInterface {

    //Number of the deck.
    private int deckNumber;
    //Cards contained in the deck.
    private LinkedList<Card> cards;

    /**
     * Constructs the deck, sets the deck number and 
     * creates a linked list of cards.
     * @param deckNumber number of the deck.
     */
    public CardDeck(int deckNumber) {
        cards = new LinkedList<>();
        this.deckNumber = deckNumber;
    }

    /**
     * Constructs the deck, sets the deck number and 
     * sets the LinkedList of cards to the argument.
     * @param deckNumber number of the deck.
     * @param deck the linked list of cards.
     */
    public CardDeck(int deckNumber, LinkedList<Card> deck) {
        this.deckNumber = deckNumber;
        this.cards = deck;
    }

    /**
     * Returns the top card of the deck.
     * @return The top card of the deck.
     */
    public synchronized Card retrieveTopCard() {
        return cards.poll();
    }

    /**
     * Places a given card at the bottom of the deck. Notifies the
     * player threads waiting for the deck to have cards.
     * @param card  The card to be placed at the bottom.
     */
    public synchronized void giveCard(Card card) {
        cards.addFirst(card);
        //Notifies the player waiting for the deck to have cards.
        notify();
    }

    /**
     * Checks to see if the deck is empty by checking the size 
     * of the list of cards.
     * @return Returns true if the list has cards, false otherwise.
     */
    public boolean isNotEmpty() {
        if (cards.size() >= 1) {
            return true;
        }
        return false;
    }

    /**
     * Cycles through the list of cards, and adds their values to a string
     * to represent the cards in the deck.
     * @return A string with the value of the cards.
     */
    public String getCardsString() {
        StringBuilder cardsString = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            cardsString.append(cards.get(i).getNumber());
            //Adds a blank space between the cards
            if (i != cards.size() - 1) {
                cardsString.append(" ");
            }
        }
        return cardsString.toString();
    }

    /**
     * Sets the number of the deck.
     * @param deckNumber  The number of the deck being set.
     */
    public void setDeckNumber(int deckNumber) {
        this.deckNumber = deckNumber;
    }

    /**
     * Returns the list of cards inside of the deck.
     * @return The LinkedList of cards.
     */
    public LinkedList<Card> getCards() {
        return cards;
    }

    /**
     * Returns the number of the deck.
     * @return The number of the deck.
     */
    public int getDeckNumber() {
        return deckNumber;
    }

    /**
     * Sets the cards inside of the deck.
     * @param deck  The LinkedList of card objects.
     */
    public void setCards(LinkedList<Card> deck) {
        this.cards = deck;
    }
}
