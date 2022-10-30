package game;

import java.util.Objects;

/**
 * Represents a card in the game
 */
public class Card {
    //Value of card
    private int num;

    /**
     * Constructs card object
     * @param num card's number
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
    
    /**
     * Tests if the cards are the same
     * @param card Object to compare against
     * @return true if the cards are equal
     */
    @Override
    public boolean equals(Object card) {

        //checks if the cards match
        return Objects.equals(this.num, ((Card) card).getNumber());
    }
}