package edu.stuy;

import crio.hardware.CRIO;
import java.io.*;

public class InitTests {
    public static final boolean WITH_PHYSICS = true;
    public static final boolean NO_PHYSICS = !WITH_PHYSICS;

    /**
     * Initialize test environment.
     * Sets DEV_MODE to true in order to skip over any code that has been marked
     * currently un-testable.
     * If runWithPhysics is set to WITH_PHYSICS, runs the VIRSYS physics simulator,
     * sends actuator commands and receives sensor readings.
     *
     * @param runWithPhysics Whether to run VIRSYS for this test.  Runs VIRSYS if
     * true, runs without VIRSYS if false.
     * @throws IOException
     */
    public static void setUpTests(boolean runWithPhysics) throws IOException {
        CRIO.init(runWithPhysics, RobotMap.VIRSYS_OUTPUT_MAP, RobotMap.VIRSYS_INPUT_MAP);
        Devmode.DEV_MODE = true;
    }

    public static void tearDownTests() {
        CRIO.end();
    }
}
