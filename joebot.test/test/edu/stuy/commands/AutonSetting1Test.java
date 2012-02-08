/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.commands;

import edu.stuy.*;
import edu.stuy.commands.*;
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
public class AutonSetting1Test {

    public AutonSetting1Test() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        InitTests.setUpTests(InitTests.NO_PHYSICS);
        JoeBot j = new JoeBot();
        j.robotInit();
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
    public void testClass(){
        AutonSetting1 auton = new AutonSetting1();
        assertTrue(auton.isRunning());
        assertEquals(CommandBase.drivetrain.getAvgDistance(),Autonomous.INCHES_TO_BRIDGE,0.1);
    }
}