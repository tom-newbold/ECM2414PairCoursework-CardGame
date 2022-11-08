public class Card {
    public int value;
    public int age = 0;
    public Card(int value) {}
    /**
     * "Ages" the card; used to prevent stale hands
     */
    public void age() {} // age++;
}
