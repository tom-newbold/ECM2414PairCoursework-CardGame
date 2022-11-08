import java.util.ArrayList;
import java.util.Random;

import javax.print.DocFlavor.READER;

public class Player {
    private Integer playerID;
    private ArrayList<Card> hand = new ArrayList<Card>();
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
        ArrayList<Integer> toDiscard = new ArrayList<Integer>();
        Integer maxAge = -1;
        for (Integer i=0; i<hand.size(); i++) {
            Card c = this.hand.get(i);
            if(c.getValue() != this.preferredDenom && c.getAge()>=maxAge) {
                if(c.getAge()!=maxAge) {
                    toDiscard.clear();
                    maxAge = c.getAge();
                }
                toDiscard.add(i);
            }
        }
        // toDiscard should never be empty ??
        Integer choice_i;
        if(toDiscard.size()==1) {
            choice_i = 0;
        } else {
            Random r = new Random();
            choice_i = r.nextInt(toDiscard.size());
        }
        Integer choice = toDiscard.get(choice_i);
        return this.hand.remove(choice);
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
     * Checks the players cards to see if they have a winning hand.
     * Does not have to be preferred denomination, as long as there is 4 cards of the same value
     * @return True if the player has a winning hand
     */
    public Boolean winCondition() {
        for (Card value: hand) {
            if (value.equals(hand.get(0)))
                return true;
        }
        return false; 
    }
}
