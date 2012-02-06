package edu.stuy.commands;

import edu.stuy.*;
import edu.stuy.subsystems.Shooter;
import org.junit.*;
import static org.junit.Assert.*;

public class ConveyAutomaticTest {

    public ConveyAutomaticTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        InitTests.setUpTests(InitTests.NO_PHYSICS);
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

    /**
     * Should run the conveyor.
     *
     *  - Shooter speed good
     *  - Ball not at top
     * Result: run conveyor
     */
    @Test
    public void testRunNoBallGoodSpeed() {
        makeSpeedGood();
        makeBallNotAtTop();
        ConveyAutomatic cmd = new ConveyAutomatic();
        cmd.execute();
        assertConveyorIsRunning();
    }

    /**
     * Should run the conveyor
     *  - Shooter speed bad
     *  - No ball at top
     * Result: run conveyor
     */
    @Test
    public void testRunNoBallSpeedBad() {
        makeSpeedBad();
        makeBallNotAtTop();
        ConveyAutomatic cmd = new ConveyAutomatic();
        cmd.execute();
        assertConveyorIsRunning();
    }

    /**
     * Should run the conveyor
     *  - Shooter speed good
     *  - Ball at top
     * Result: run conveyor
     */
    @Test
    public void testRunYesBallSpeedGood() {
        makeSpeedGood();
        makeBallAtTop();
        ConveyAutomatic cmd = new ConveyAutomatic();
        cmd.execute();
        assertConveyorIsRunning();
    }

    /**
     * Should not run the conveyor
     *  - Shooter speed bad
     *  - Ball at top
     * Result: don't run conveyor
     */
    @Test
    public void testNoRunYesBallSpeedBad() {
        makeSpeedBad();
        makeBallAtTop();
        ConveyAutomatic cmd = new ConveyAutomatic();
        cmd.execute();
        assertConveyorIsNotRunning();
    }

    public void assertConveyorIsRunning() {
        assertEquals(1, CommandBase.conveyor.getUpperRoller(), 0.01);
        assertEquals(1, CommandBase.conveyor.getLowerRoller(), 0.01);
    }

    public void assertConveyorIsNotRunning() {
        assertEquals(0, CommandBase.conveyor.getUpperRoller(), 0.01);
        assertEquals(0, CommandBase.conveyor.getLowerRoller(), 0.01);
    }

    public void makeSpeedGood() {
        double distanceInches = Shooter.distances[Shooter.KEY_INDEX];
        ShooterShoot cmd = new ShooterShoot(distanceInches);
        cmd.initialize();
        cmd.execute();
    }

    public void makeSpeedBad() {
        double distanceInches = Shooter.distances[Shooter.KEY_INDEX]+50;
        ShooterShoot cmd = new ShooterShoot(distanceInches);
        cmd.initialize();
        cmd.execute();
    }

    public void makeBallAtTop() {
        CommandBase.conveyor.upperSensor.value = true;
    }

    public void makeBallNotAtTop() {
        CommandBase.conveyor.upperSensor.value = false;
    }
}