package Tests;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;

import CardGame.Card;
import CardGame.Deck;
import CardGame.PlayerThread;

public class TestPlayerThread {
    @Test
    public void testRun() throws IOException {
        Card[] cards = new Card[10];
        for(Integer i=0;i<10;i++) { cards[i] = new Card(i+1); }
        Deck d1 = new Deck(cards);
        Card[] hand = new Card[4];
        for(Integer i=0;i<4;i++) { hand[i] = new Card(i+1); }
        FileWriter f = new FileWriter("test_out.txt");
        PlayerThread pt = new PlayerThread(Thread.currentThread(), f, 1, hand, d1, d1);
        pt.start();
        try {
            Thread.sleep(500);
        } catch (Exception e) {}
        pt.interrupt();
        assertEquals("run() failed: Player's hand does not contain 4 cards", 4, pt.getPlayer().getHand().length);
    }

    // use this test?
    @Test
    public void testRun_Win() throws IOException {
        Card[] cards = new Card[4];
        Card[] hand = new Card[4];
        for(Integer i=0;i<4;i++) {
            cards[i] = new Card(1);
            hand[i] = new Card(2);
        }
        Deck d1 = new Deck(cards);
        FileWriter f = new FileWriter("test_out.txt");
        PlayerThread pt = new PlayerThread(Thread.currentThread(), f, 1, hand, d1, d1);
        pt.start();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {}
        assertTrue("Player failed to win game", pt.getPlayer().winCondition());
    }

    @Test
    public void testGetPlayer() throws IOException {
        Card[] cards = new Card[10];
        for(Integer i=0;i<10;i++) { cards[i] = new Card(i+1); }
        Deck d1 = new Deck(cards);
        Card[] hand = new Card[4];
        for(Integer i=0;i<4;i++) { hand[i] = new Card(i+1); }
        FileWriter f = new FileWriter("test_out.txt");
        PlayerThread pt = new PlayerThread(Thread.currentThread(), f, 1, hand, d1, d1);
        Card[] playerHand = pt.getPlayer().getHand();
        for(Integer i=0;i<4;i++) { 
            assertEquals("getplayer() failed: hand mismatch", hand[i].getValue(), playerHand[i].getValue());
        }
    }
}
