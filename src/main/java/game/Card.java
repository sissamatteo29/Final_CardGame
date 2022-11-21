package game;

/**
 * Represents a card in the game
 */
public class Card {
    //Value of card
    private final int num;

    /**
     * Sets the number of the card
     */
    public Card(int n) {
        this.num = n;
    }

    /**
     * Returns the card's value
     * @return the card's number
     */
    public int getNumber() {
        return this.num;
    }

}
