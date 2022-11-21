package game;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Runs all the test related to the CardDeck class
 */
public class CardDeckTestRunner {
    public static void main(String[] args) {
        boolean fail = false;
        Result result = JUnitCore.runClasses(CardDeckTestSuite.class);  //Runs the tests in the suite
        System.out.println(result.getRunCount() + " tests have been run in this test suite");
        System.out.println("The CardDeckTestSuite failed in " + result.getFailureCount() + " tests");
        System.out.println("Failure percentage = " + calculatePercentage(result.getRunCount(), result.getFailureCount()));
        if (result.getFailureCount() > 0) {
            System.out.println("");
            System.out.println("Printing out the errors occured:");
        }
        for (Failure failure : result.getFailures()) {
            fail = true;
            System.out.println(failure.toString());
        }
        if (!fail) {
            System.out.println("All the tests were successful!");
        }
    }

    private static double calculatePercentage(int runCount, int failureCount) {
        return ((double) failureCount / (double) runCount);
    }
}
