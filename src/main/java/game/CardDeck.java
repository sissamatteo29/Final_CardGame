package game;

import java.util.ArrayDeque;

/**
 * Represents a card deck in the game
 */
public class CardDeck {

    private int deckNumber;
    private ArrayDeque<Card> cards = new ArrayDeque<>();

    /**
     * Constructs a deck with the given number.
     * @param deckNumber number of the deck
     */
    public CardDeck (int deckNumber){
        this.deckNumber = deckNumber;
    }

    /**
     * Returns the top card of the deck
     * @return the top card
     */
    public Card returnTopCard() {
        return null;
    }

    /**
     * Puts a card at the bottom of the deck
     * @param card the said card
     */
    public void addCard(Card card) {
    
    }

    /**
     * Returns the number of the deck
     * @return the number of the deck
     */
    public int returnDeckNumber(){
        return this.deckNumber;
    }
}