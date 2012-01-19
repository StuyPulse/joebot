/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2011. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.testing;

import com.sun.squawk.Klass;
import com.sun.squawk.Suite;
import com.sun.squawk.VM;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 * This class should be extended by test classes. Make sure to call the
 * constructor for each subclass once so that the class is added to the master
 * hashtable
 * @author dtjones
 */
public abstract class TestClass {
    static Hashtable testClasses = new Hashtable();
    Hashtable tests = new Hashtable();
    static Vector failedTests = new Vector();

    private static boolean isTest(Klass klass) {
        Klass comp;
        while ((comp = klass.getSuperclass()) != null) {
            if (Klass.asClass(comp).equals(TestClass.class))
                return true;
            klass = comp;
        }
        return false;
    }
    
    static {
        Suite mySuite = VM.getCurrentIsolate().getLeafSuite();
        int length = mySuite.getClassCount();
        for (int i = 0; i < length; i++) {
            Klass k = mySuite.getKlass(i);
            if (k.hasDefaultConstructor() && isTest(k))
                k.newInstance();
        }
    }

    /**
     * Create a new instance of the test class and add it to the master table
     * of test classes
     */
    public TestClass () {
        testClasses.put(getName(), this);
    }

    /**
     * This class should be extended within subclasses of each TestClass to
     * add a test. In the constructor for the TestClass you could put :
     *  new Test() {
     *      public String getName() { return "Sample"; }
     *      public void run() {
     *          //your test here
     *          assertFail("I don't work");
     *      }
     *  };
     */
    public abstract class Test {
        private String testName;
        /**
         * Create a new Test and add it to the master classes table of tests
         * @param name The name for this test, This should be unique for this TestClass.
         */
        public Test(String name) {
            this.testName = name;
            tests.put(getName(), this);
        }
        /**
         * Get the name of this test, should be unique within this test class.
         * @return the name of the test
         */
        public String getName () { return this.testName;};
        /**
         * The code to test. Failure is triggered using one of the assert methods
         */
        public abstract void run ();
        /**
         * Setup code to run before this test is run
         */
        public void setup() {}
        /**
         * Teardown code to run after the test is run
         */
        public void teardown() {}
        
        /**
         * Return a string representing this Test.
         * @return The name of the Test.
         */
        public String toString() {
            return getName();
        }
        
        final void test() {
            try {
                setup();
                run();
                System.out.println("    " + getName() + " passed");
            } catch (TestFailure e) {
                System.out.println("    " + getName() + " failed : " + e.getMessage());
                System.out.print("    ");
                e.failedTest.print(System.out);
                System.out.println();
                e.setTestName(
                    TestClass.this.getName(),
                    this.getName());
                failedTests.addElement(new Failure(e));
            } finally {
                teardown();
            }
        }
    }

    /**
     * Get the name of this TestClass. This should be unique within the testClasses
     * @return The name of this test class
     */
    public abstract String getName();

    /**
     * Setup code to run before the tests within this class are run
     */
    public void setup() {}
    /**
     * Teardown code to run after the tests within this class are run
     */
    public void teardown() {}

    /**
     * Get a list of all the failed tests.
     * @return A list of all of the failed tests.
     */
    public static Failure[] getFailures() {
        Failure[] failures = new Failure[failedTests.size()];
        failedTests.copyInto(failures);
        return failures;
    }

    /**
     * Get a string representing this TestClass.
     * @return The name of this test class.
     */
    public String toString() {
        return getName();
    }

    final void test() {
        System.out.println("Running " + getName());
        setup();
        Enumeration elements = tests.elements();
        while(elements.hasMoreElements())
            ((Test)elements.nextElement()).test();
        teardown();
    }

    final void test(String test) {
        System.out.println("Running " + getName());
        setup();
        ((Test)tests.get(test)).test();
        teardown();
    }

    /**
     * Run the tests within the class name given
     * @param clas The name of the TestClass to run
     */
    public static void run(String clas) {
        ((TestClass)testClasses.get(clas)).test();
        System.out.println("Tests complete");
    }

    /**
     * Run the given test within the given class
     * @param clas The name of the class to run.
     * @param test The name of the test to run.
     */
    public static void run(String clas, String test) {
        ((TestClass)testClasses.get(clas)).test(test);
        System.out.println("Tests complete");
    }

    /**
     * Run all of the tests in all of the classes.
     */
    public static void runAll () {
        Enumeration elements = testClasses.elements();
        while(elements.hasMoreElements())
            ((TestClass)elements.nextElement()).test();
        System.out.println("Tests complete");
    }

    /**
     * Fail the test.
     * @param msg A message descibing the failure.
     */
    protected void assertFail (String msg) {
        throw new TestFailure(msg);
    }

    /**
     * Fail the test if the given items are not the same
     * @param expected The expected value.
     * @param actual The actual value.
     */
    protected void assertEquals (Object expected, Object actual) {
        if ((null == expected) ^ (null == actual))
            throw new TestFailure("Expected " + expected + " got " + actual);
        if (!expected.equals(actual))
            throw new TestFailure("Expected " + expected + " got " + actual);
    }

    /**
     * Fail the test if the given items are not the same
     * @param expected The expected value.
     * @param actual The actual value.
     */
    protected void assertEquals (long expected, long actual) {
        if (expected != actual)
            throw new TestFailure("Expected " + expected + " got " + actual);
    }

    /**
     * Fail the test if the given items are not the same
     * @param expected The expected value.
     * @param actual The actual value.
     */
    protected void assertEquals (double expected, double actual) {
        if (expected != actual)
            throw new TestFailure("Expected " + expected + " got " + actual);
    }

    /**
     * Fail the test if the given items are not the same
     * @param expected The expected value.
     * @param actual The actual value.
     * @param tolerance The amount by which the values must match
     */
    protected void assertEquals (double expected, double actual, double tolerance) {
        if (Math.abs(expected - actual) > tolerance)
            throw new TestFailure("Expected " + expected + " got " + actual);
    }

    /**
     * Fail the test if the given value is true.
     * @param value Boolean value that must be true for success
     */
    protected void assertTrue (boolean value) {
        if ( ! value )
            throw new TestFailure("Assertion was false");
    }

    /**
     * Fail the test if the given value is false.
     * @param value Boolean value that must be false for success
     */
    protected void assertFalse (boolean value) {
        if ( value )
            throw new TestFailure("Assertion was true");
    }
}
