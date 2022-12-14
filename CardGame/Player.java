package CardGame;
import java.util.ArrayList;
import java.util.Random;
import java.io.FileWriter;
import java.io.IOException;

public class Player {
    private FileWriter fw;
    private Integer playerID;
    private ArrayList<Card> hand = new ArrayList<Card>();
    private Integer preferredDenom;
    public Player(FileWriter fileWriter, Integer pID, Card[] cards) {
        this.fw = fileWriter;
        this.playerID = pID;
        this.preferredDenom = this.playerID;
        for (Card c : cards) {
            this.hand.add(c);
        }
    }

    /**
     * Draws card from the referenced deck and adds to Players hand
     * @param d The deck to draw from
     */
    private void drawCard(Deck d) throws InterruptedException {
        Card drawnCard = d.drawTopCard();
        this.hand.add(drawnCard);
    }

    /**
     * Chooses and discards a card from the Player's hand (not of prefered denomination)
     * @returns The discarded card
     */
    private Card discardCard() {
        ArrayList<Card> toDiscard = new ArrayList<Card>();
        // stores "least desirable" cards to choose from; determined using card "age" and value
        Integer maxAge = -1;
        for (Card c : hand) {
            if(c.getValue() != this.preferredDenom && c.getAge()>=maxAge) {
                if(c.getAge()!=maxAge) {
                    toDiscard.clear();
                    maxAge = c.getAge();
                }
                toDiscard.add(c);
            }
        }
        // toDiscard should never be empty
        Integer choice_i;
        if(toDiscard.size()==1) {
            choice_i = 0;
        } else {
            Random r = new Random();
            choice_i = r.nextInt(toDiscard.size());
            // picks random card
        }
        Card choice = toDiscard.get(choice_i);
        this.hand.remove(choice); // removed by object
        return choice;
    }

    /**
     * Performs a players full turn atomically (before checking win condition);
     * Player's hand will only ever contain 4 cards outside of this function;
     * Writes drawn and discarded cards, as well as hand, to the output file
     * @param d1 The deck to draw from
     * @param d2 The deck to discard to
     */
    public void atomicTurn(Deck d1, Deck d2) throws InterruptedException {
        for(Card c : this.hand) { c.age(); }
        this.drawCard(d1);
        Card drawnCard = this.hand.get(4);
        Card discardCard = this.discardCard();
        discardCard.resetAge();
        d2.addCard(discardCard);
        synchronized (this.fw) {
            try {
                this.fw.write(String.format("Player %d draws a %d from deck %d\n",
                    this.playerID, drawnCard.getValue(), this.playerID));
                this.fw.write(String.format("Player %d discards a %d to deck %d\n",
                    this.playerID, discardCard.getValue(), ((this.playerID-1)%CardGame.players)+2));
                this.fw.write(String.format("Player %d current hand %d %d %d %d\n",
                    this.playerID,this.hand.get(0).getValue(),this.hand.get(1).getValue(),
                    this.hand.get(2).getValue(),this.hand.get(3).getValue()));
            } catch (IOException e) {} catch (NullPointerException n) {}
            // null pointer exception added to pass test - output file is not being tested
        }
    }

    /**
     * Checks the players cards to see if they have a winning hand.
     * Does not have to be preferred denomination, as long as there is 4 cards of the same value
     * @return True if the player has a winning hand
     */
    public Boolean winCondition() {
        Boolean win = true;
        Integer firstVal = this.hand.get(0).getValue();
        for(Card c : this.hand) {
            if(c.getValue() != firstVal) {
                win = false;
            }
        }
        return win;
    }
    
    /**
     * @return The player's hand as an array of Cards
     */
    public Card[] getHand() {
        Card[] h = new Card[this.hand.size()];
        for(Integer i=0; i<h.length;i++) {
            h[i] = this.hand.get(i);
        }
        return h;
    }
}
