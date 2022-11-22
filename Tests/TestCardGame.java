package Tests;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import CardGame.CardGame;

public class TestCardGame {  
    @Test
    public void testCardGame() {
        String textInputs = "4\n./pack.txt";
        InputStream testInput = new ByteArrayInputStream(textInputs.getBytes(StandardCharsets.UTF_8));
        System.setIn(testInput);
        CardGame.main(null);
        assertNotEquals(0, (int)CardGame.winPlayer);
        assertEquals(4, (int)CardGame.players);
        assertEquals("pack.txt", CardGame.packFile);
    }
}
