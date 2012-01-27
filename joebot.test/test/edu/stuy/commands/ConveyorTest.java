/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import crio.hardware.DigitalSidecar;
import edu.stuy.InitTests;
import edu.stuy.JoeBot;
import edu.wpi.first.wpilibj.Timer;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Kevin Wang
 */
public class ConveyorTest {
    
    public ConveyorTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        InitTests.setUpTests();
        JoeBot j = new JoeBot();
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
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void conveyorExists() {
        System.out.println(CommandBase.conveyor);
        assertTrue(CommandBase.conveyor != null);
    }
    
    @Test
    public void testConveyorTurnsOn() {
        ConveyorConvey cmd = new ConveyorConvey();
        cmd.initialize();
        double startTime = Timer.getFPGATimestamp();
        while (Timer.getFPGATimestamp() - startTime < 1) {
            cmd.execute();
        }
        assertEquals(1, DigitalSidecar.register[8], 0);
        cmd.end();
        assertEquals(1, DigitalSidecar.register[8], 0);
    }
}
