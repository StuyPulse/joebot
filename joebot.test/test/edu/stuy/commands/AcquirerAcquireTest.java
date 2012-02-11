/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.commands;

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
public class AcquirerAcquireTest {

    public AcquirerAcquireTest() {
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

    /**
     * Should not run the conveyor
     * -Bottom Sensor does not sense a ball
     * Result: don't run conveyor
     */
    @Test
    public void testRunNoBallNoConvey() {
        sensorDoesNotSense();
        AcquirerAcquire cmd = new AcquirerAcquire();
        cmd.execute();
        assertConveyorIsNotRunning();
    }

    /**
     * Should run the conveyor
     * -Bottom Sensor senses a ball
     * Result: run conveyor
     */
    @Test
    public void testRunYesBallConvey() {
        sensorDoesSense();
        AcquirerAcquire cmd = new AcquirerAcquire();
        cmd.execute();
        assertConveyorIsRunning();
    }

    public void sensorDoesNotSense() {
        CommandBase.conveyor.lowerSensor.value = false;
    }

    public void sensorDoesSense() {
        CommandBase.conveyor.lowerSensor.value = true;
    }

    public void assertConveyorIsNotRunning() {
        assertEquals(0, CommandBase.conveyor.getRoller(), 0.01);
    }

    public void assertConveyorIsRunning() {
        assertEquals(1, CommandBase.conveyor.getRoller(), 0.01);
    }
}