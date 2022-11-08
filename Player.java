import java.util.LinkedList;

public class Player {
    private Integer playerID;
    private LinkedList<Card> hand;
    private Integer preferredDenom;
    public Player(Integer pID, Card[] cards) {
        this.playerID = pID;
        this.preferredDenom = pID;
        for (Card c : cards) {
            this.hand.add(c);
        }
    }

    /**
     * Draws card from the referenced deck and adds to Players hand
     * @param d The deck to draw from
     */
    private void drawCard(Deck d) throws InterruptedException {
        this.hand.add(d.drawTopCard());
    }

    /**
     * Chooses and discards a card from the Player's hand (not of prefered denomination)
     * @returns The discarded card
     */
    private Card discardCard() {
        Card toDiscard;
        int maxAge = -1;
        for (Card c : this.hand) {
            if(c.getValue() != this.preferredDenom && c.getAge()>maxAge) {
                toDiscard = c;
                maxAge = c.getAge();
            }
        }
        // toDiscard should always be defined ????????
        return this.hand.pop(); // TODO: return c
    }

    /**
     * Performs a players full turn atomically (before checking win condition);
     * Player's hand will only ever contain 4 cards outside of this function
     * @param d1 The deck to draw from
     * @param d2 The deck to discard to
     */
    public void atomicTurn(Deck d1, Deck d2) throws InterruptedException {
        this.drawCard(d1);
        d2.addCard(this.discardCard());
    }

    /**
     * Checks the players cards to see if
     * Does not have to be preferred they have a winning hand
     * @return True if the player has a winning hand
     */
    public Boolean winCondition() {
        /*
        if (hand.contains(preferedDenom)) {
            
        }
        */
        return false; 
    } // TODO
}
