package Tests;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;

import CardGame.Card;
import CardGame.Deck;
import CardGame.Player;

public class TestPlayer {
    /*
    @Test
    public void testDrawCard() throws InterruptedException, IOException {
        Card[] cards = new Card[4];
        for(Integer i=0;i<4;i++) { cards[i] = new Card(i+1); }
        Card[] hand = new Card[4];
        for(Integer i=0;i<4;i++) { hand[i] = new Card(i+1); }
        FileWriter f = new FileWriter("test_out.txt",true);
        Player p = new Player(f, f, 0, hand);

    }

    @Test
    public void testDiscardCard() {}
     */

    @Test
    public void testAtomicTurn() throws IOException, InterruptedException {
        Card[] cards = new Card[4];
        for(Integer i=0;i<4;i++) { cards[i] = new Card(i+1); }
        Deck d1 = new Deck(cards);
        Deck d2 = new Deck(new Card[0]);
        Card[] hand = new Card[4];
        for(Integer i=0;i<4;i++) { hand[i] = new Card(i+1); }
        FileWriter f = new FileWriter("test_out.txt",true);
        Player p = new Player(f, f, 2, hand);
        p.atomicTurn(d1, d2);
        assertEquals("Draw unsuccessful", d1.getDeck(), 3);
        assertEquals("Discard unsuccessful", d2.getDeck().length, 1); 
        //assertEquals(p, p);    
    }

    @Test
    public void testWinCondition() throws IOException {
        Card[] losingCards = new Card[4];
        for(Integer i=0;i<4;i++) { losingCards[i] = new Card(i+1); }
        FileWriter f = new FileWriter("test_out.txt",true);
        Player p = new Player(f, f, 0, losingCards);
        
        assertFalse("winCondition() failed", p.winCondition());
        
        Card[] winningCards = new Card[4];
        for(Integer i=0;i<4;i++) { winningCards[i] = new Card(1); }
        Player q = new Player(f, f, 1, winningCards);
        
        assertTrue("winCondition() failed", q.winCondition());
    }

    @Test
    public void testgetHand() throws IOException {
        Card[] hand = new Card[4];
        for(Integer i=0;i<4;i++) { hand[i] = new Card(i+1); }
        FileWriter f = new FileWriter("test_out.txt",true);
        Player p = new Player(f, f, 0, hand);
        
        assertNotEquals("getHand() failed", hand, p.getHand());
    }
}
