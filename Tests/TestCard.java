package Tests;
import org.junit.Test;
import static org.junit.Assert.*;

import Main.Card;

public class TestCard {
    @Test
    public void testCreateCard() {
        Card c = new Card(1);
        assertEquals(c.getValue(),1);
    }

    @Test
    public void testGetValue() {}

    @Test
    public void testGetAge() {}

    @Test
    public void testAge() {}

    @Test
    public void testResetAge() {}
}
