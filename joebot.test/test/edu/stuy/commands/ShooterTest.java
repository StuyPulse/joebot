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

import edu.stuy.subsystems.Shooter;
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
        /*InitTests.setUpTests(InitTests.NO_PHYSICS);
        theRobot = new JoeBot();
        theRobot.robotInit();*/
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
        double distanceInches;
        distanceInches = Shooter.distances[Shooter.fenderIndex];
        /*System.out.println(distanceInches);
        System.out.println(distanceInches + 4 + " " + Shooter.theoreticalDesiredExitRPM(distanceInches + 4.0));
        System.out.println(distanceInches + " " + Shooter.theoreticalDesiredExitRPM(distanceInches));
        System.out.println(distanceInches - 4 + " " + Shooter.theoreticalDesiredExitRPM(distanceInches - 4.0));*/
    }

}