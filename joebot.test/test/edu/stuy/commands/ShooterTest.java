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

import edu.stuy.*;

import edu.stuy.subsystems.Flywheel;
/**
 *
 * @author prog694
 */
public class ShooterTest {

    static JoeBot theRobot;

    public ShooterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        InitTests.setUpTests(InitTests.NO_PHYSICS);
        theRobot = new JoeBot();
        theRobot.robotInit();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        //InitTests.tearDownTests();
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testdesiredExitRPM() {
        System.out.println("Actual points\ndistance, top hoop speed, mid hoop speed");
        for (int i = 0; i < Flywheel.numDistances; i++) {
            System.out.println(Flywheel.distances[i] + ", " + Flywheel.speedsTopHoop[i]);
        }

        System.out.println("Interpolated points\ndistance, speed");
        for (double distanceInches = Flywheel.distances[Flywheel.FENDER_INDEX];
             distanceInches <= Flywheel.distances[Flywheel.KEY_INDEX];
             distanceInches += 1) {
            System.out.println(distanceInches + ", " +
                    CommandBase.flywheel.lookupRPM(distanceInches, Flywheel.speedsTopHoop)[0] + ", " +
                    CommandBase.flywheel.lookupRPM(distanceInches, Flywheel.speedsMiddleHoop)[0]);
        }
    }
}