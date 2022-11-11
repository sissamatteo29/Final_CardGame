package game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a card deck in the game
 */
public class CardDeck {

    private int deckNumber;
    private LinkedList<Card> cards;

    public CardDeck(int deckNumber){
        cards = new LinkedList<Card>();
        this.deckNumber = deckNumber;
    }

    public CardDeck(int deckNumber, LinkedList<Card> deck) {
        this.deckNumber = deckNumber;
        this.cards = deck;
    }

    /**
     * Returns the top card of the deck
     * @return the top card
     */
    public synchronized Card retrieveTopCard() {
        return cards.poll();
    }

    /**
     * Puts a card at the bottom of the deck
     * @param card the said card
     */
    public synchronized void giveCard(Card card) {
        cards.addFirst(card);
    }

    public int getDeckNumber() {
        return deckNumber;
    }

    public void setDeckNumber(int deckNumber) {
        this.deckNumber = deckNumber;
    }

    public LinkedList<Card> getCards() {
        return cards;
    }

    public void setCards(LinkedList<Card> deck) {
        this.cards = deck;
    }
}