package Tests;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({TestCardGame.class, TestCard.class, TestDeck.class,
    TestPlayer.class,TestPlayerThread.class})
public class CardGameTestSuite {
    // Simply a container for other test classes
}
