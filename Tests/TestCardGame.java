package Tests;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

//imports to remove?
import java.util.Scanner;
import java.io.IOException;

import CardGame.CardGame;

public class TestCardGame {  
    @Test
    public void testCardGame() throws IOException {
        System.out.println(new java.io.File(".").getAbsolutePath());
        String textInputs = "4\npack.txt";
        InputStream testInput = new ByteArrayInputStream(textInputs.getBytes(StandardCharsets.UTF_8));
        System.setIn(testInput);
        CardGame.main(null);
        assertNotEquals(0, (int)CardGame.winPlayer);
        assertEquals(4, (int)CardGame.players);
        assertEquals("pack.txt", CardGame.packFile);
    }
}
