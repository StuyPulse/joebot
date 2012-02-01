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

import edu.stuy.subsystems.*;
import edu.wpi.first.wpilibj.*;
import crio.hardware.*;
import edu.wpi.first.wpilibj.command.*;

/**
 *
 * @author English
 */
public class DriveTrainTest {

    public DriveTrainTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
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
        Command result;
        Command expected = new DriveManualJoystickControl();
        Drivetrain test = new Drivetrain();
        test.initDefaultCommand();
        result = test.getDefaultCommand();
        assertNotNull(test);
        assertEquals(result.getName(), expected.getName());
    }

    @Test
    public void testTankDrive(){
        Drivetrain test = new Drivetrain();
        test.tankDrive(1, 1);
    }

    @Test
    public void testSetGear(){
        Drivetrain test = new Drivetrain();
        test.setGear(false);
        assertFalse(test.gearShift.get());
        test.setGear(true);
        assertTrue(test.gearShift.get());
    }
}