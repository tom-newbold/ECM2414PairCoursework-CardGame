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
        assertEquals("drawTopCard() failed: drawn card incorrect", cards[0], deck.drawTopCard());
        Card[] resultingDeck = deck.getDeck();
        for(Integer i=0; i<3;i++) {
            assertEquals("drawTopCard() failed: residual deck mismatch", i+2, (int)resultingDeck[i].getValue());
        }
    }

    @Test
    public void testAddCard() {
        Card[] cards = new Card[4];
        for(Integer i=0;i<4;i++) { cards[i] = new Card(i+1); }
        Deck deck = new Deck(cards);
        deck.addCard(new Card(5));
        assertEquals("addCard() failed: no card added", 5, deck.getDeck().length);
        Card[] resultingDeck = deck.getDeck();
        for(Integer i=0; i<5;i++) {
            assertEquals("addCard() failed: residual deck mismatch", i+1, (int)resultingDeck[i].getValue());
        }
    }

    @Test
    public void testGetDeck() {
        Card[] cards = new Card[4];
        for(Integer i=0;i<4;i++) { cards[i] = new Card(i+1); }
        Deck deck = new Deck(cards);
        assertNotEquals("getDeck() failed", cards, deck.getDeck());
    }
}
