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
        assertEquals(4, pt.getPlayer().getHand().length);
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
        for(Integer i=0;i<4;i++) { 
            assertEquals("getplayer() failed: hand ",hand[i].getValue(), pt.getPlayer().getHand()[i].getValue());
        }
    }
}
