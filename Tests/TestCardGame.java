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
    public void testCardGame() throws IOException {
        String inputs = "-1\n0\n4\nbin/notapack.txt\nbin/pack.txt";
        InputStream testInputStream = new ByteArrayInputStream(inputs.getBytes(StandardCharsets.UTF_8));
        System.setIn(testInputStream);
        CardGame.main(null);
        assertNotEquals(0, (int)CardGame.winPlayer);
        assertEquals(4, (int)CardGame.players);
        assertEquals("bin/pack.txt", CardGame.packFile);
        testInputStream.close();
        CardGame.winPlayer = 0;
        CardGame.players = 0;
        CardGame.packFile = "";
    }

    @Test
    public void testCardGameDuplicate() throws IOException {
        String inputs_duplicate = "a\nZ\n8\nbin/pack.txt\nbin/pack_8player.txt";
        InputStream testInputStream_duplicate = new ByteArrayInputStream(inputs_duplicate.getBytes(StandardCharsets.UTF_8));
        System.setIn(testInputStream_duplicate);
        CardGame.main(null);
        assertNotEquals(0, (int)CardGame.winPlayer);
        assertEquals(8, (int)CardGame.players);
        assertEquals("bin/pack_8player.txt", CardGame.packFile);
        testInputStream_duplicate.close();
        CardGame.winPlayer = 0;
        CardGame.players = 0;
        CardGame.packFile = "";
    }

    @After
    public void restore() {
        System.setIn(System.in);
    }
}
