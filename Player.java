import java.util.LinkedList;

public class Player {
    private Integer playerID;
    private LinkedList<Card> cards;
    private Integer preferedDenom;
    public Player(Integer pID, Card[] hand) {}

    /**
     * Draws card from the referenced deck and adds to Players hand
     * @param d The deck to draw from
     */
    public void drawCard(Deck d) {};

    /**
     * Chooses and discards a card from the Player's hand (not of prefered denomination)
     * @returns The discarded card
     */
    public Card discardCard() { return new Card(0); };

    /**
     * Checks the players cards to see if they have a winning hand
     * @return True if the player has a winning hand
     */
    public Boolean winCondition() { return false; };
}
