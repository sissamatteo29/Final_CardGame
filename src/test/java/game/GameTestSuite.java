package game;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Placeholder to create the Suite of all the test classes related to the Game class
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({GameDistributeCardsTest.class, GameReadFileTest.class,
                        GameMinorMethodsTest.class, GameUserInputTest.class})
public class GameTestSuite {

}
