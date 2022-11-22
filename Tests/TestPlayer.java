package Tests;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.io.FileWriter;
import java.io.IOException;

import CardGame.CardGame;
import CardGame.Card;
import CardGame.Deck;
import CardGame.Player;

public class TestPlayer {
    // drawCard() and discardCard() methods are private;
    // They are tested using reflection, but also within atomicTurn() tests 

    @Before
    public void preventDivideByZero() {
        CardGame.players = 4;
    }

    @Test
    public void testDrawCard() throws IOException, NoSuchMethodException {
        Deck d1 = new Deck(new Card[]{ new Card(1) });
        Card[] cards = new Card[4];
        for(Integer i=0;i<4;i++) { cards[i] = new Card(2); }
        FileWriter f = new FileWriter("test_out.txt");
        Player p = new Player(f, 1, cards);

        Method _drawCard = Player.class.getDeclaredMethod("drawCard", Deck.class);
        _drawCard.setAccessible(true);
        try {
            _drawCard.invoke(p, d1);
        } catch (Exception e) {}
        _drawCard.setAccessible(false);

        Card[] resultingHand = p.getHand();
        assertEquals("drawCard() failed: hand does not contain 5 cards", 5, (int)resultingHand.length);
        for(Integer i=0; i<4;i++) {
            assertEquals("drawCard() failed: residual deck mismatch", 2, (int)resultingHand[i].getValue());
        }
        assertEquals("drawCard() failed: residual deck mismatch", 1, (int)resultingHand[4].getValue());
    }

    @Test
    public void testDiscardCard() throws IOException, NoSuchMethodException {
        Card[] cards = new Card[5];
        for(Integer i=0;i<4;i++) { cards[i] = new Card(2); }
        cards[4] = new Card(1);
        FileWriter f = new FileWriter("test_out.txt");
        Player[] players = new Player[]{ new Player(f, 1, cards), new Player(f, 2, cards) };

        Method _discardCard = Player.class.getDeclaredMethod("discardCard");
        _discardCard.setAccessible(true);
        try {
            for( Player p : players ) {
                _discardCard.invoke(p);
            }
        } catch (Exception e) {}
        _discardCard.setAccessible(false);

        Card[] resultingHand;
        for(Integer i : new Integer[]{ 1,2 }) {
            resultingHand = players[i-1].getHand();
            assertEquals("discardCard() failed: hand does not contain 4 cards", 4, (int)resultingHand.length);
            for(Integer j=0; j<3; j++) {
                assertEquals("discardCard() failed: residual deck mismatch", 2, (int)resultingHand[j].getValue());
            }
            assertEquals("discardCard() failed: residual deck mismatch", (int)i, (int)resultingHand[3].getValue());
        }
    }

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
        assertEquals("Discard condition failed: card with value 1 should be retained",
            1, (int)p.getHand()[3].getValue());
        assertNotEquals("Discard condition failed: card with value 1 should be retained",
            1, (int)d2.getDeck()[2].getValue());
    }

    @Test
    public void testAtomicTurn_Win() throws IOException, InterruptedException {
        Card[] deck = new Card[]{new Card(1), new Card(2)};
        Deck d1 = new Deck(deck);
        Card[] cards = new Card[4];
        cards[0] = new Card(2);
        for(Integer i=1;i<4;i++) { cards[i] = new Card(1); }
        FileWriter f = new FileWriter("test_out.txt");
        Player p = new Player(f, 1, cards);
        p.atomicTurn(d1, d1);
        assertTrue("Player turn failed to induce a winning state", p.winCondition());
    }

    @Test
    public void testAtomicTurn_discardByAge() throws IOException, InterruptedException {
        Deck d1 = new Deck(new Card[]{new Card(3)});
        Card[] cards = new Card[4];
        for(Integer i=0;i<4;i++) { cards[i] = new Card(2); }
        FileWriter f = new FileWriter("test_out.txt");
        Player p = new Player(f, 1, cards);
        for(Integer i=0; i<8; i++) {
            for(Card c : p.getHand()) { c.resetAge(); }
            d1.getDeck()[0].age();
            d1.getDeck()[0].age();
            p.atomicTurn(d1, d1);
            assertEquals("discardCard() failed: oldest card not discarded", 3, (int)d1.getDeck()[0].getValue());
        }
    }

    @Test
    public void testAtomicTurn_discardNonPrefered() throws IOException, InterruptedException {
        Deck d1 = new Deck(new Card[]{new Card(3)});
        Card[] cards = new Card[4];
        for(Integer i=0;i<4;i++) { cards[i] = new Card(2); }
        FileWriter f = new FileWriter("test_out.txt");
        Player p = new Player(f, 2, cards);
        for(Integer i=0; i<8; i++) {
            p.atomicTurn(d1, d1);
            assertEquals("discardCard() failed: non-prefered denomination not discarded", 3, (int)d1.getDeck()[0].getValue());
        }
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
