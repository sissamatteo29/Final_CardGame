package game;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Runs all the test methods related to the Game class
 */
public class GameTestRunner {
    public static void main(String[] args) {
        boolean fail = false;
        Result result = JUnitCore.runClasses(GameTestSuite.class);
        System.out.println(result.getRunCount() + " tests have been run in this test suite");
        System.out.println("The GameTestSuite failed in " + result.getFailureCount() + " tests");
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
