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
        System.out.println("Actual points\ndistance, speed");
        for (int i = 0; i < Shooter.numDistances; i++) {
            System.out.println(Shooter.distances[i] + ", " + Shooter.speeds[i]);
        }

        System.out.println("Interpolated points\ndistance, speed");
        for (double distanceInches = Shooter.distances[Shooter.fenderIndex];
             distanceInches <= Shooter.distances[Shooter.keyIndex];
             distanceInches += 1) {
            System.out.println(distanceInches + ", " + CommandBase.shooter.lookupRPM(distanceInches)[0]);
        }
<<<<<<< HEAD
    }

    @Test
    public void testRPMTolerance() {
        double min, mid, max;
        
        System.out.println("key-shots:");
        min = Shooter.theoreticalDesiredExitRPM(129-5);
        System.out.println(min);
        mid = Shooter.theoreticalDesiredExitRPM(129);
        System.out.println(mid);
        max = Shooter.theoreticalDesiredExitRPM(129+5);
        System.out.println(max);
        System.out.println("error: " + (max - mid));
        System.out.println("error: " + (mid - min));
        
        System.out.println("fender-shots:");
        min = Shooter.theoreticalDesiredExitRPM(77.25-5);
        System.out.println(min);
        mid = Shooter.theoreticalDesiredExitRPM(77.25);
        System.out.println(mid);
        max = Shooter.theoreticalDesiredExitRPM(77.25+5);
        System.out.println(max);
        System.out.println("error: " + (max - mid));
        System.out.println("error: " + (mid - min));
=======
        /*System.out.println(distanceInches);
        System.out.println(distanceInches + 4 + " " + Shooter.theoreticalDesiredExitRPM(distanceInches + 4.0));
        System.out.println(distanceInches + " " + Shooter.theoreticalDesiredExitRPM(distanceInches));
        System.out.println(distanceInches - 4 + " " + Shooter.theoreticalDesiredExitRPM(distanceInches - 4.0));*/
>>>>>>> 2584c84ca7485312df0ac5c22e2330a51b5d9883
    }

}