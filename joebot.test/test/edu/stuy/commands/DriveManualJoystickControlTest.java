/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.*;

import edu.stuy.*;
import edu.wpi.first.wpilibj.*;
import crio.hardware.*;

/**
 *
 * @author admin
 */
public class DriveManualJoystickControlTest {
    
    public DriveManualJoystickControlTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        InitTests.setUpTests();
        JoeBot j = new JoeBot();
        j.robotInit();

        // "Drive" the joysticks
        Joystick.setStickAxis(RobotMap.LEFT_JOYSTICK_PORT, 1, -1);
        Joystick.setStickAxis(RobotMap.RIGHT_JOYSTICK_PORT, 1, -1);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        InitTests.tearDownTests();

        // "un-drive" the joysticks
        Joystick.setStickAxis(RobotMap.LEFT_JOYSTICK_PORT, 1, 0);
        Joystick.setStickAxis(RobotMap.RIGHT_JOYSTICK_PORT, 1, 0);
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testDriveForward() {
        DriveManualJoystickControl cmd = new DriveManualJoystickControl();
        cmd.initialize();
        double startTime = Timer.getFPGATimestamp();
        while (Timer.getFPGATimestamp() - startTime < 1) {
            cmd.execute();
        }
        cmd.end();

        // TODO: get encoder readings from drivetrain object
        double leftDist = CRIO.client.getdata()[1];
        double rightDist = CRIO.client.getdata()[2];
        System.out.println(leftDist + " " + rightDist);
        assertTrue(leftDist > 0);
        assertTrue(rightDist > 0);
        assertEquals(leftDist, rightDist, 2.0);
    }
}
