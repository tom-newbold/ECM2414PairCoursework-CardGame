package Tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.StringReader;

import CardGame.CardGame;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestCard.class, TestDeck.class, TestPlayer.class, TestPlayerThread.class})
public class TestCardGame {
    // Simply a container for other test classes
    @Test
    public void testCardGame() {
        CardGame cg = new CardGame();
        System.setIn(new StringReader("   "));
        cg.main();
        assertNotEquals(0, (int)CardGame.winPlayer);
        assertEquals(4, (int)CardGame.players);
        assertEquals("pack.txt", CardGame.packFile);
    }
}
