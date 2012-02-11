package edu.stuy.commands;

import edu.stuy.speed.JaguarSpeed;
import edu.stuy.*;
import edu.stuy.subsystems.Shooter;
import org.junit.*;
import static edu.stuy.assertions.ConveyorAssertions.*;

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

    
    public void makeSpeedGood() {
        double distanceInches = Shooter.distances[Shooter.KEY_INDEX];
        ShooterMoveFlyWheel cmd = new ShooterMoveFlyWheel(distanceInches);
        cmd.initialize();
        cmd.execute();
    }

    public void makeSpeedBad() {
        JaguarSpeed j = Shooter.lowerRoller;
        j.jaguar.value = -100;
    }

    public void makeBallAtTop() {
        CommandBase.conveyor.upperSensor.value = true;
    }

    public void makeBallNotAtTop() {
        CommandBase.conveyor.upperSensor.value = false;
    }
}