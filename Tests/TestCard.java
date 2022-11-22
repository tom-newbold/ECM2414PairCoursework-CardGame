package Tests;
import org.junit.Test;
import static org.junit.Assert.*;

import CardGame.Card;

public class TestCard {
    /*
    @Test
    public void testCreateCard() {
        Card c = new Card(1);
        assertEquals("Value set incorrectly",1,(int)c.getValue());
        assertEquals("Age initialized incorrectly",0,(int)c.getAge());
    }
    */

    @Test
    public void testGetValue() {
        Card c = new Card(1);
        assertEquals("getValue() failed",1,(int)c.getValue());
    }

    @Test
    public void testGetValueDuplicate() {
        Card c = new Card(1);
        assertNotEquals("getValue() failed",2,(int)c.getValue());
    }

    @Test
    public void testGetAge() {
        Card c = new Card(1);
        assertEquals("getAge() failed",0,(int)c.getAge());
    }

    @Test
    public void testGetAgeDuplicate() {
        Card c = new Card(1);
        assertNotEquals("getAge() failed",1,(int)c.getAge());
    }

    @Test
    public void testAge() {
        Card c = new Card(1);
        c.age();
        assertEquals("age() failed",1,(int)c.getAge());
    }

    @Test
    public void testResetAge() {
        Card c = new Card(1);
        c.age();
        c.resetAge();
        assertEquals("resetAge() failed",0,(int)c.getAge());
    }

    @Test
    public void testResetAgeDuplicate() {
        Card c = new Card(1);
        c.age();
        c.resetAge();
        assertNotEquals("resetAge() failed",1,(int)c.getAge());
    }
}
