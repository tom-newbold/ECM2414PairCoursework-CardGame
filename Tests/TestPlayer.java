package Tests;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.InterruptedByTimeoutException;

import CardGame.Card;
import CardGame.Deck;
import CardGame.Player;

public class TestPlayer {
    @Test
    public void testDrawCard() throws InterruptedException {
        Card[] cards = new Card[4];
        for(Integer i=0;i<4;i++) { cards[i] = new Card(i+1); }
        Card[] hand = new Card[4];
        for(Integer i=0;i<4;i++) { hand[i] = new Card(i+1); }
        Deck deck = new Deck(cards);

    }

    @Test
    public void testDiscardCard() {}

    @Test
    public void testAtomicTurn() {}

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
    public void testgetHand() {}
}
