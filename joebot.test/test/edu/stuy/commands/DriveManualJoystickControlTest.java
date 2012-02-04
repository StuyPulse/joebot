/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import org.junit.*;
import static org.junit.Assert.*;
import java.io.*;

import edu.stuy.*;
import edu.wpi.first.wpilibj.*;
import crio.hardware.*;
<<<<<<< HEAD
import edu.stuy.commands.CommandBase;
=======
import edu.stuy.commands.base.CommandBase;
>>>>>>> feature-drivetrain

/**
 *
 * @author admin
 */
public class DriveManualJoystickControlTest {
    
    static JoeBot j;
    
    public DriveManualJoystickControlTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
<<<<<<< HEAD
        InitTests.setUpTests(InitTests.WITH_PHYSICS);
=======
        InitTests.setUpTests();
>>>>>>> feature-drivetrain
        j = new JoeBot();
        j.robotInit();

        // "Drive" the joysticks
<<<<<<< HEAD
        Joystick.setStickAxis(RobotMap.LEFT_JOYSTICK_PORT, 2, -1);
        Joystick.setStickAxis(RobotMap.RIGHT_JOYSTICK_PORT, 2, -1);
=======
        Joystick.setStickAxis(RobotMap.LEFT_JOYSTICK_PORT, 1, 1);
        Joystick.setStickAxis(RobotMap.RIGHT_JOYSTICK_PORT, 1, 1);
>>>>>>> feature-drivetrain
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        InitTests.tearDownTests();

        // "un-drive" the joysticks
        Joystick.setStickAxis(RobotMap.LEFT_JOYSTICK_PORT, 2, 0);
        Joystick.setStickAxis(RobotMap.RIGHT_JOYSTICK_PORT, 2, 0);
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
    public void testDriveForward() throws Encoder.NotInitializedException {
        DriveManualJoystickControl cmd = new DriveManualJoystickControl();
        cmd.initialize();
        double startTime = Timer.getFPGATimestamp();
        while (Timer.getFPGATimestamp() - startTime < 1) {
            cmd.execute();
        }
        cmd.end();

        // TODO: get encoder readings from drivetrain object
<<<<<<< HEAD
        double leftDist = CommandBase.drivetrain.encoderLeft.getDistance();
        double rightDist = CommandBase.drivetrain.encoderRight.getDistance();
=======
        double leftDist = CommandBase.drivetrain.leftEnc.getDistance();
        double rightDist = CommandBase.drivetrain.rightEnc.getDistance();
>>>>>>> feature-drivetrain
        System.out.println(leftDist + " " + rightDist);
        assertTrue(leftDist > 0);
        assertTrue(rightDist > 0);
        assertEquals(leftDist, rightDist, 2.0);
    }
}
