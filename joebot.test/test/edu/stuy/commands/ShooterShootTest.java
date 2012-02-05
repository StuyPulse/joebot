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
import edu.stuy.subsystems.*;

/**
 *
 * @author prog694
 */
public class ShooterShootTest {

    static JoeBot j;

    public ShooterShootTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        InitTests.setUpTests(InitTests.NO_PHYSICS);
        j = new JoeBot();
        j.robotInit();
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
    public void testdesiredExitRPM() {
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
    }

}