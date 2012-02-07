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

import edu.stuy.subsystems.Drivetrain.SpeedRamp;

/**
 *
 * @author admin
 */
public class SpeedProfileTest {
    
    public SpeedProfileTest() {
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
    public void testProfileSpeed() {
        System.out.println("distance from start, speed");
        // start at key, move closer to wall in 1/4-inch steps
        
        double distToTravel = 144 - 38.75;
        double distToWall = 144;
        int forward = 1;
        for (double distanceFromStart = 0; distanceFromStart <= 144; distanceFromStart += 0.25) {
            double sonarDistance = distToWall - distanceFromStart;
            double speed = SpeedRamp.profileSpeed_Bravo(sonarDistance - 38.75, distToTravel, forward);
            System.out.println(distanceFromStart + ", " + speed);
        }
    }
}
