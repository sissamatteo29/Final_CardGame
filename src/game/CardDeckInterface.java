package game;

import java.util.LinkedList;

    public interface CardDeckInterface {


        Card retrieveTopCard();

        void giveCard(Card card);

        int getDeckNumber();

        boolean isNotEmpty();

        void setDeckNumber(int deckNumber);

        LinkedList<Card> getCards();

        String getCardsString();

        void setCards(LinkedList<Card> deck);
    }
