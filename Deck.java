import java.util.LinkedList;

public class Deck {
    private LinkedList<Card> deck;
    public Deck(Card[] cards) {}

    /**
     * Draws the top card from the deck
     * @return The drawn card
     */
    public synchronized Card drawTopCard() {return new Card(0); };

    /**
     * Adds a card to the (bottom of?) the deck
     * @param c The card to be added to the deck
     */
    public synchronized void addCard(Card c) {};
}
