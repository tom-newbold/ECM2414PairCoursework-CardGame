package Tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({TestCard.class, TestDeck.class,
    TestPlayer.class,TestPlayerThread.class, TestCardGame.class})
public class CardGameTestSuite {
    // Simply a container for other test classes
}
