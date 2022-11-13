package game;

import java.util.LinkedList;

/**
 * Represents a card deck in the game
 */
public class CardDeck implements CardDeckInterface{

    private int deckNumber;
    private LinkedList<Card> cards;

    public CardDeck(int deckNumber){
        cards = new LinkedList<>();
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
        notify();
    }
        

    public int getDeckNumber() {
        return deckNumber;
    }

    public boolean isNotEmpty() {
        if(cards.size() > 1){
            return true;
        }else{
            return false;
        }
    }

    public void setDeckNumber(int deckNumber) {
        this.deckNumber = deckNumber;
    }

    public LinkedList<Card> getCards() {
        return cards;
    }

    public String getCardsString(){
        String cardsString = "";
        for (Card card : cards) {
            cardsString += card.getNumber() + " ";
        }
        return cardsString;
    }

    public void setCards(LinkedList<Card> deck) {
        this.cards = deck;
    }
}
