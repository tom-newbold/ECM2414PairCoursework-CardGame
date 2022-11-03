import java.util.LinkedList;

interface DeckInterface {
    public Card drawTopCard(); // syncronised
    public void addCard();
}
public class Deck {
    private LinkedList<Card> deck;
    public Deck(Card[] cards) {}
}
