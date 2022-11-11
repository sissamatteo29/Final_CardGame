package game;

/**
 * Represents a card in the game
 */
public class Card {
    //Value of card
    private final int num;

    /**
     * Constructs card object
     * param num card's number
     */
    public Card(int n) {
        this.num = n;
    }

    /**
     * Gets the card's value
     * @return the card's number
     */
    public int getNumber() {
        return this.num;
    }

}