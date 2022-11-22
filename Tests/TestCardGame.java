package Tests;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import CardGame.CardGame;

public class TestCardGame {
    @Test
    public void testCardGameDuplicate() throws IOException {
        String textInputs = "8\npack.txt\nbin/pack_8player.txt";
        InputStream testInput = new ByteArrayInputStream(textInputs.getBytes(StandardCharsets.UTF_8));
        System.setIn(testInput);
        CardGame.main(null);
        assertNotEquals(0, (int)CardGame.winPlayer);
        assertEquals(8, (int)CardGame.players);
        assertEquals("bin/pack_8player.txt", CardGame.packFile);
        testInput.close();
        CardGame.winPlayer = 0;
    }

    @Test
    public void testCardGame() throws IOException {
        String textInputs = "4\nnotapack.txt\nbin/pack.txt";
        InputStream testInput = new ByteArrayInputStream(textInputs.getBytes(StandardCharsets.UTF_8));
        System.setIn(testInput);
        CardGame.main(null);
        assertNotEquals(0, (int)CardGame.winPlayer);
        assertEquals(4, (int)CardGame.players);
        assertEquals("bin/pack.txt", CardGame.packFile);
        testInput.close();
        CardGame.winPlayer = 0;
    }

    @After
    public void restore() {
        System.setIn(System.in);
    }
}
