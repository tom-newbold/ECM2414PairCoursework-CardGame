package Tests;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;

import CardGame.Card;
import CardGame.Deck;
import CardGame.Player;

public class TestPlayer {
    // drawCard() and discardCard() methods are private;
    // They are tested by atomicTurn() tests 

    @Test
    public void testAtomicTurn() throws IOException, InterruptedException {
        Card[] cards = new Card[]{new Card(1), new Card(2)};
        Deck d1 = new Deck(cards);
        Deck d2 = new Deck(cards);
        Card[] hand = new Card[4];
        for(Integer i=0;i<4;i++) { hand[i] = new Card(i+1); }
        FileWriter f = new FileWriter("test_out.txt");
        Player p = new Player(f, 1, hand);
        p.atomicTurn(d1, d2);
        assertEquals("Draw unsuccessful", d1.getDeck().length, 1);
        assertEquals("Discard unsuccessful", d2.getDeck().length, 3);
        assertEquals("Hand card count mismatch", 4, p.getHand().length);
        assertEquals("Discard condition failed: card with value 1 should be retained", 1, (int)p.getHand()[3].getValue());
        assertNotEquals("Discard condition failed: card with value 1 should be retained", 1, (int)d2.getDeck()[2].getValue());
    }

    @Test
    public void testWinCondition() throws IOException {
        Card[] losingCards = new Card[4];
        for(Integer i=0;i<4;i++) { losingCards[i] = new Card(i+1); }
        FileWriter f = new FileWriter("test_out.txt",true);
        Player p_1 = new Player(f, 0, losingCards);
        
        assertFalse("winCondition() failed on losing hand", p_1.winCondition());
        
        Card[] winningCards = new Card[4];
        for(Integer i=0;i<4;i++) { winningCards[i] = new Card(1); }
        Player p_2 = new Player(f, 1, winningCards);
        Player p_3 = new Player(f, 0, winningCards);
        
        assertTrue("winCondition() failed on winning hand", p_2.winCondition());
        assertTrue("winCondition() failed on id/card value mismatch", p_3.winCondition());
    }

    @Test
    public void testgetHand() throws IOException {
        Card[] hand = new Card[4];
        for(Integer i=0;i<4;i++) { hand[i] = new Card(i+1); }
        FileWriter f = new FileWriter("test_out.txt",true);
        Player p = new Player(f, 0, hand);
        
        assertNotEquals("getHand() failed", hand, p.getHand());
    }
}
