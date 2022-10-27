import java.util.LinkedList;

interface DeckInterface {
    public Card drawTopCard(); // syncronised
    public void addCard();
}
public class Deck {
    private volatile LinkedList<Card> deck; // is volatile needed here?
}
