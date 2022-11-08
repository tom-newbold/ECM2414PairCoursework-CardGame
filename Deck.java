import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<Card>();
    public Deck(Card[] cards) {
        for (Card c : cards) {
            this.deck.add(c);
        }
    }

    /**
     * Draws the top card from the deck
     * @return The drawn card
     */
    public synchronized Card drawTopCard() throws InterruptedException {
        while(this.deck.size()<=0) {
            wait();
        }
        return this.deck.remove(0);
    }

    /**
     * Adds a card to the (bottom of?) the deck
     * @param c The card to be added to the deck
     */
    public synchronized void addCard(Card c) {
        this.deck.add(c);
        notify();
    }
}
