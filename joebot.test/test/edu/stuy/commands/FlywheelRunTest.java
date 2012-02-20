/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.commands;

import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
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
public class FlywheelRunTest {

    FlywheelRun cmd;

    public FlywheelRunTest() {
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
        CommandBase.init();
        cmd = new FlywheelRun();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAdjustSpeed() {
        // change OI input
        DriverStationEnhancedIO.digitalIOs[CommandBase.oi.HOOP_HEIGHT_SWITCH_CHANNEL] = true;
        DriverStationEnhancedIO.analogIOs[CommandBase.oi.MAX_ANALOG_CHANNEL - 1] = 3.3;
        DriverStationEnhancedIO.analogIOs[CommandBase.oi.SHOOTER_BUTTON_CHANNEL - 1] =
                (CommandBase.oi.DISTANCE_BUTTON_FENDER - 0.5) *
                DriverStationEnhancedIO.analogIOs[CommandBase.oi.MAX_ANALOG_CHANNEL - 1] / 8;

        // use top hoop
        cmd.execute();
        // assert that speed changed
        double rpm = -CommandBase.flywheel.speedsTopHoop[CommandBase.flywheel.FENDER_INDEX];
        System.out.println("desired flywheel speed" + rpm);
        assertEquals(rpm,
                CommandBase.flywheel.upperRoller.getRPM(), 1);
    }

}