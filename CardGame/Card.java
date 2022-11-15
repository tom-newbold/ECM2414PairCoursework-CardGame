package CardGame;

public class Card {
    private Integer value;
    private Integer age;
    public Card(Integer v) {
        this.value = v;
        resetAge();
    }

    /**
     * Only getter method; value is read-only so no syncronisation required
     * @return The value of the card
     */
    public Integer getValue() { return this.value; }

    /**
     * @return The "age" of the card (number of turns it has been in the players hand)
     */
    public Integer getAge() { return this.age; }
    /**
     * "Ages" the card; used to prevent stale hands
     */
    public void age() { this.age++; }
    /**
     * Sets the age to zero (when a card is picked up from the deck)
     */
    public void resetAge() { this.age = 0; }
}
