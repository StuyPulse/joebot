/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.commands.tuning;

import edu.stuy.*;
import edu.stuy.commands.CommandBase;
import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
public class ShooterManualSpeedTest {

    public ShooterManualSpeedTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        InitTests.setUpTests(InitTests.NO_PHYSICS);
        JoeBot theRobot = new JoeBot();
        theRobot.robotInit();
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
    public void testSetManualSpeed() throws NetworkTableKeyNotDefined {
        SmartDashboard.putDouble("setRPMtop", 1000);
        SmartDashboard.putDouble("setRPMbottom", 1200);
        ShooterManualSpeed cmd = new ShooterManualSpeed();
        cmd.init();
        cmd.execute();
        assertEquals(1000, CommandBase.shooter.upperRoller.getRPM(), 0.01);
        assertEquals(1200, CommandBase.shooter.lowerRoller.getRPM(), 0.01);
    }

}