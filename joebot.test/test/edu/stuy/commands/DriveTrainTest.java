/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.commands;


import edu.stuy.subsystems.Drivetrain;
import edu.wpi.first.wpilibj.command.Command;
import edu.stuy.*;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author English
 */
public class DriveTrainTest {

    static JoeBot theRobot;

    public DriveTrainTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        InitTests.setUpTests(InitTests.NO_PHYSICS);
        theRobot = new JoeBot();
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    @Test
    public void testInitDefaultCommand() {
        Command expected = new DriveManualJoystickControl();

        assertEquals(CommandBase.drivetrain.getDefaultCommand().getName(), expected.getName());
    }

    @Test
    public void testTankDrive(){
        Drivetrain test = new Drivetrain();
        test.tankDrive(1, 1);
    }

    @Test
    public void testSetGear(){
        Drivetrain test = CommandBase.drivetrain;
        test.setGear(false);
        assertFalse(test.gearShift.get());
        test.setGear(true);
        assertTrue(test.gearShift.get());
    }
}