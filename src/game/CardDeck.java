package game;

import java.util.LinkedList;

/**
 * Represents a card deck in the game
 */
public class CardDeck implements CardDeckInterface {

    private int deckNumber;
    private LinkedList<Card> cards;

    public CardDeck(int deckNumber) {
        cards = new LinkedList<>();
        this.deckNumber = deckNumber;
    }

    public CardDeck(int deckNumber, LinkedList<Card> deck) {
        this.deckNumber = deckNumber;
        this.cards = deck;
    }


    public synchronized Card retrieveTopCard() {
        return cards.poll();
    }

    public synchronized void giveCard(Card card) {
        cards.addFirst(card);
        notify();
    }

    public boolean isNotEmpty() {
        if (cards.size() >= 1) {
            return true;
        }
        return false;
    }

    public String getCardsString() {
        StringBuilder cardsString = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            cardsString.append(cards.get(i).getNumber());
            if (i != cards.size() - 1) {
                cardsString.append(" ");
            }
        }
        return cardsString.toString();
    }


    public void setDeckNumber(int deckNumber) {
        this.deckNumber = deckNumber;
    }

    public LinkedList<Card> getCards() {
        return cards;
    }

    public int getDeckNumber() {
        return deckNumber;
    }

    public void setCards(LinkedList<Card> deck) {
        this.cards = deck;
    }
}