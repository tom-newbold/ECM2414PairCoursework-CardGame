public class Card {
    private Integer value;
    private Integer age = 0;
    public Card(int value) {}

    /**
     * Only getter method; value is read-only so no syncronisation required
     * @return The value of the card
     */

    public Integer getValue() { return 0; }

    /**
     * @return The "age" of the card (number of turns it has been in the players hand)
     */
    public Integer getAge() { return 0; }
    /**
     * "Ages" the card; used to prevent stale hands
     */
    public void age() {} // age++;
    /**
     * Sets the age to zero (when a card is picked up from the deck)
     */
    public void resetAge() {}
}
