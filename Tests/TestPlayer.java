package Tests;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;

import CardGame.Card;
import CardGame.Deck;
import CardGame.Player;

public class TestPlayer {
    /* Cannot test drawCard() or discardCard() methods 
     * as they private so tests done in atomicTurn() method 
     */

    @Test
    public void testAtomicTurn() throws IOException, InterruptedException {
        Card[] cards = new Card[]{new Card(1), new Card(2)};
        Deck d1 = new Deck(cards);
        Deck d2 = new Deck(cards);
        Card[] hand = new Card[4];
        for(Integer i=0;i<4;i++) { hand[i] = new Card(i+1); }
        FileWriter f = new FileWriter("test_out.txt");
        Player p = new Player(f, f, 1, hand);
        p.atomicTurn(d1, d2);
        assertEquals("Draw unsuccessful", d1.getDeck().length, 1);
        assertEquals("Discard unsuccessful", d2.getDeck().length, 3); 
        assertNotEquals(1,(int)d2.getDeck()[d2.getDeck().length-1].getValue());
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
