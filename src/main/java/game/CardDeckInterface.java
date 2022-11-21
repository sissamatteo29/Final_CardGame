package game;

import java.util.LinkedList;

/**
 * Represents a card deck in the game
 */
public interface CardDeckInterface {

    /**
     * Returns the top card of the deck
     * @return The top card of the deck
     */
    public Card retrieveTopCard();

    /**
     * Places a given card at the bottom of the deck. Notifies the
     * player threads waiting for the deck to have cards.
     * @param card  The card to be placed at the bottom
     */
    public void giveCard(Card card);

    /**
     * Checks to see if the deck is empty by checking the size 
     * of the list of cards.
     * @return Returns true if the list has cards, false otherwise
     */
    public boolean isNotEmpty();

    /**
     * Cycles through the list of cards, and adds their values to a string,
     * to represent the cards in the deck.
     * @return A string with the value of the cards
     */
    public String getCardsString();

    /**
     * Sets the number of the deck.
     * @param deckNumber  The number of the deck being set
     */
    public void setDeckNumber(int deckNumber);

    /**
     * Returns the list of cards inside of the deck.
     * @return THe LinkedList of cards
     */
    public LinkedList<Card> getCards();

    /**
     * Returns the number of the deck.
     * @return The number of the deck
     */
    public int getDeckNumber();

    /**
     * Sets the cards inside of the deck.
     * @param deck  The LinkedList of card objects
     */
    public void setCards(LinkedList<Card> deck);
}

