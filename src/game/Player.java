package game;

import java.util.ArrayList;
import java.util.Collections;

public class Player implements Runnable {
    private int playerNumber;
    private GameInterface game;
    private int preferredCards;

    private ArrayList<Card> hand = new ArrayList<>();
    private CardDeckInterface takeDeck, giveDeck;

    private Boolean gameOver = false;
    private ArrayList<String> log = new ArrayList<>();



    public void run() {
        reorderHand();
        log.add("Player "+ playerNumber + " initial hand: " + handToString());

        while (!gameOver){

            if (checkVictory()){

                game.compareAndSet(0, playerNumber);
                exit(true);

                break;

            } else if(game.returnWinner() != 0){
                exit(false);
                break;
            }
            //draws from the left deck
            if (takeDeck.isNotEmpty()) {

                Card newCard = takeDeck.retrieveTopCard();

                Card discardCard = hand.remove(hand.size()-1 - preferredCards);
                giveDeck.giveCard(discardCard);  //remove method returns the object removed}
                if (newCard.getNumber() == playerNumber){
                    //In this case we need to hold the card and place it at the end of the list
                    hand.add(newCard);
                    preferredCards++;
                } else {
                    //we just place the new card at the beginning of the list
                    hand.add(0, newCard);
                }


                appendToLog(newCard, discardCard);
            }else{
                //If Left deck empty will wait until cards have been added.
                synchronized (takeDeck){
                    try {
                        takeDeck.wait();
                    }catch (InterruptedException e){
                        break;
                    }
                }
            }
        }

    }


    private void exit(boolean win){
        gameOver = true;

        if(win){
            log.add(String.format("Player %d wins",playerNumber));
            game.stopDeckMonitor();
        }else{
            int winner = game.returnWinner();
            log.add(String.format("Player %d has informed player %d that player %d has won",
                                  winner, playerNumber, winner)); 
        }
        log.add(String.format("Player %d Exits\nPlayer %d final hand %s",
        playerNumber, playerNumber, handToString()));  
        game.generateLog("player" + playerNumber + "_output.txt", String.join("\n", log));

    }

    private void appendToLog(Card drawn, Card discarded) {
        log.add(String.format("Player %d draws a %d from deck %d", playerNumber, drawn.getNumber(), takeDeck.getDeckNumber()));
        log.add(String.format("Player %d discards a %d to deck %d", playerNumber, discarded.getNumber(), giveDeck.getDeckNumber()));
        log.add(String.format("Player %d current hand: %s", playerNumber, handToString()));
     }

    private String handToString() {
        String handString = "";
        for (Card card : hand) {
            handString += card.getNumber() + " ";
        }
        return handString;
    }


    public ArrayList<Card> getHand() {
        return hand;
    }

    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public boolean checkVictory() {
        boolean victory = true;
        //Compare the current card number with the successive one
        for (int i = 0; i < hand.size() - 1; i++) {
            if (hand.get(i).getNumber() != hand.get(i+1).getNumber()) {
                victory = false;
            }
        }
        return victory;
    }


    public void reorderHand() {
        preferredCards = 0;
        for(int i = 0; i < hand.size() - preferredCards; i++){
            if (hand.get(i).getNumber() == playerNumber){
                Collections.swap(hand, i, hand.size() - 1 - preferredCards);
                preferredCards++;
                i--;
            }
        }
    }

    public Player(int number, GameInterface game) {
        this.playerNumber = number;
        this.game = game;
    }

    public CardDeckInterface getTakeDeck() {
        return takeDeck;
    }

    public void setTakeDeck(CardDeck takeDeck) {
        this.takeDeck = takeDeck;
    }

    public CardDeckInterface getGiveDeck() {
        return giveDeck;
    }

    public void setGiveDeck(CardDeck giveDeck) {
        this.giveDeck = giveDeck;
    }

}
