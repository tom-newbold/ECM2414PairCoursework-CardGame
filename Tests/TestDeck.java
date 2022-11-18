package Tests;
import org.junit.Test;
import static org.junit.Assert.*;

import CardGame.Deck;
import CardGame.Card;

public class TestDeck {
    @Test
    public void testDrawTopCard() throws InterruptedException {
        Card[] cards = new Card[4];
        for(Integer i=0;i<4;i++) { cards[i] = new Card(i+1); }
        Deck deck = new Deck(cards);
        assertEquals("drawTopCard() failed", cards[0], deck.drawTopCard());
    }

    @Test
    public void testAddCard() {
        Card[] cards = new Card[4];
        for(Integer i=0;i<4;i++) { cards[i] = new Card(i+1); }
        Deck deck = new Deck(cards);
        deck.addCard(new Card(5));
        assertEquals("addCard() failed", 5, deck.getDeck().length);
    }

    @Test
    public void testGetDeck() {
        Card[] cards = new Card[4];
        for(Integer i=0;i<4;i++) { cards[i] = new Card(i+1); }
        Deck deck = new Deck(cards);
        assertNotEquals("getDeck() failed", cards, deck.getDeck());
    }
}
