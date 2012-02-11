/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy;

import edu.stuy.JoeBot;
import edu.stuy.InitTests;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author 694
 */
public class JoeBotTest {

    public JoeBotTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        InitTests.setUpTests(InitTests.NO_PHYSICS);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        InitTests.tearDownTests();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testNoConveyorOnStartup() {
        JoeBot theRobot = new JoeBot();
        theRobot.robotInit();

    }

}