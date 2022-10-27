import java.util.LinkedList;

interface PlayerInterface {
    public void drawCard();
    public Card discardCard();
    public Boolean winCondition();
}

public class Player {
    private LinkedList<Card> cards;
    private Integer preferedDenom;
    // also pass in decks
    public Player(Card[] hand) {}
}
