package Tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({TestCard.class, TestDeck.class, TestPlayer.class, TestPlayerThread.class})
public class TestCardGame {
    // Simply a container for other test classes    
}
