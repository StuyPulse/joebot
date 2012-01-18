/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj;

import com.sun.squawk.*;
import edu.wpi.first.wpilibj.communication.FRCControl;

/**
 * IterativeRobot implements a specific type of Robot Program framework, extending the RobotBase class.
 *
 * The IterativeRobot class is intended to be subclassed by a user creating a robot program.
 *
 * This class is intended to implement the "old style" default code, by providing
 * the following functions which are called by the main loop, startCompetition(), at the appropriate times:
 *
 * robotInit() -- provide for initialization at robot power-on
 *
 * init() functions -- each of the following functions is called once when the
 *                     appropriate mode is entered:
 *  - DisabledInit()   -- called only when first disabled
 *  - AutonomousInit() -- called each and every time autonomous is entered from another mode
 *  - TeleopInit()     -- called each and every time teleop is entered from another mode
 *
 * Periodic() functions -- each of these functions is called iteratively at the
 *                         appropriate periodic rate (aka the "slow loop").  The period of
 *                         the iterative robot is synced to the driver station control packets,
 *                         giving a periodic frequency of about 50Hz (50 times per second).
 *   - disabledPeriodic()
 *   - autonomousPeriodic()
 *   - teleopPeriodic()
 *
 * Continuous() functions -- each of these functions is called repeatedly as
 *                           fast as possible:
 *   - disabledContinuous()
 *   - autonomousContinuous()
 *   - teleopContinuous()
 *
 */
public class IterativeRobot extends RobotBase {

    private final static boolean TRACE_LOOP_ALLOCATIONS = false; // master tracing switch
    private final static boolean TRACE_LOOP_ALLOCATIONS_AFTER_INIT = true;  // trace before or after all phases initialize

    private boolean m_disabledInitialized;
    private boolean m_autonomousInitialized;
    private boolean m_teleopInitialized;

    /**
     * Constructor for RobotIterativeBase
     *
     * The constructor initializes the instance variables for the robot to indicate
     * the status of initialization for disabled, autonomous, and teleop code.
     */
    public IterativeRobot() {
        // set status for initialization of disabled, autonomous, and teleop code.
        m_disabledInitialized = false;
        m_autonomousInitialized = false;
        m_teleopInitialized = false;
    }

    /**
     * Provide an alternate "main loop" via startCompetition().
     *
     * This specific startCompetition() implements "main loop" behavior like that of the FRC
     * control system in 2008 and earlier, with a primary (slow) loop that is
     * called periodically, and a "fast loop" (a.k.a. "spin loop") that is
     * called as fast as possible with no delay between calls.
     */
    public void startCompetition() {
        robotInit();

        // tracing support:
        final int TRACE_LOOP_MAX = 100;
        int loopCount = TRACE_LOOP_MAX;
        Object marker = null;
        boolean didDisabledPeriodic = false;
        boolean didAutonomousPeriodic = false;
        boolean didTeleopPeriodic = false;
        if (TRACE_LOOP_ALLOCATIONS) {
            GC.initHeapStats();
            if (!TRACE_LOOP_ALLOCATIONS_AFTER_INIT) {
                System.out.println("=== Trace allocation in competition loop! ====");
                marker = new Object(); // start counting objects before any loop starts - includes class initialization
            }
        }

        // loop forever, calling the appropriate mode-dependent function
        while (true) {
            if (TRACE_LOOP_ALLOCATIONS && didDisabledPeriodic && didAutonomousPeriodic && didTeleopPeriodic && --loopCount <= 0) {
                System.out.println("!!!!! Stop loop!");
                break;
            }
            // Call the appropriate function depending upon the current robot mode
            if (isDisabled()) {
                // call DisabledInit() if we are now just entering disabled mode from
                // either a different mode or from power-on
                if (!m_disabledInitialized) {
                    disabledInit();
                    m_disabledInitialized = true;
                    // reset the initialization flags for the other modes
                    m_autonomousInitialized = false;
                    m_teleopInitialized = false;
                }
                if (nextPeriodReady()) {
                    FRCControl.observeUserProgramDisabled();
                    disabledPeriodic();
                    didDisabledPeriodic = true;
                }
                disabledContinuous();
            } else if (isAutonomous()) {
                // call Autonomous_Init() if this is the first time
                // we've entered autonomous_mode
                if (!m_autonomousInitialized) {
                    // KBS NOTE: old code reset all PWMs and relays to "safe values"
                    // whenever entering autonomous mode, before calling
                    // "Autonomous_Init()"
                    autonomousInit();
                    m_autonomousInitialized = true;
                    m_teleopInitialized = false;
                    m_disabledInitialized = false;
                }
                if (nextPeriodReady()) {
                    getWatchdog().feed();
                    FRCControl.observeUserProgramAutonomous();
                    autonomousPeriodic();
                    didAutonomousPeriodic = true;
                }
                autonomousContinuous();
            } else {
                // call Teleop_Init() if this is the first time
                // we've entered teleop_mode
                if (!m_teleopInitialized) {
                    teleopInit();
                    m_teleopInitialized = true;
                    m_autonomousInitialized = false;
                    m_disabledInitialized = false;
                }
                if (nextPeriodReady()) {
                    getWatchdog().feed();
                    FRCControl.observeUserProgramTeleop();
                    teleopPeriodic();
                    didTeleopPeriodic = true;
                }
                teleopContinuous();
            }

            if (TRACE_LOOP_ALLOCATIONS && TRACE_LOOP_ALLOCATIONS_AFTER_INIT &&
                    didDisabledPeriodic && didAutonomousPeriodic && didTeleopPeriodic && loopCount == TRACE_LOOP_MAX) {
                System.out.println("=== Trace allocation in competition loop! ====");
                marker = new Object(); // start counting objects after 1st loop completes - ignore class initialization
            }
        }
        if (TRACE_LOOP_ALLOCATIONS) {
            GC.printHeapStats(marker, false);
        }
    }

    /**
     * Determine if the appropriate next periodic function should be called.
     * Call the periodic functions whenever a packet is received from the Driver Station, or about every 20ms.
     */
    private boolean nextPeriodReady() {
        return m_ds.isNewControlData();
    }

    /* ----------- Overridable initialization code -----------------*/

    /**
     * Robot-wide initialization code should go here.
     *
     * Users should override this method for default Robot-wide initialization which will
     * be called when the robot is first powered on.  It will be called exactly 1 time.
     */
    public void robotInit() {
        System.out.println("Default IterativeRobot.robotInit() method... Overload me!");
    }

    /**
     * Initialization code for disabled mode should go here.
     *
     * Users should override this method for initialization code which will be called each time
     * the robot enters disabled mode.
     */
    public void disabledInit() {
        System.out.println("Default IterativeRobot.disabledInit() method... Overload me!");
    }

    /**
     * Initialization code for autonomous mode should go here.
     *
     * Users should override this method for initialization code which will be called each time
     * the robot enters autonomous mode.
     */
    public void autonomousInit() {
        System.out.println("Default IterativeRobot.autonomousInit() method... Overload me!");
    }

    /**
     * Initialization code for teleop mode should go here.
     *
     * Users should override this method for initialization code which will be called each time
     * the robot enters teleop mode.
     */
    public void teleopInit() {
        System.out.println("Default IterativeRobot.teleopInit() method... Overload me!");
    }

   /* ----------- Overridable periodic code -----------------*/

    private boolean dpFirstRun = true;
    /**
     * Periodic code for disabled mode should go here.
     *
     * Users should override this method for code which will be called periodically at a regular
     * rate while the robot is in disabled mode.
     */
    public void disabledPeriodic() {
        if (dpFirstRun) {
            System.out.println("Default IterativeRobot.disabledPeriodic() method... Overload me!");
            dpFirstRun = false;
        }
        Timer.delay(0.001);
    }

    private boolean apFirstRun = true;

    /**
     * Periodic code for autonomous mode should go here.
     *
     * Users should override this method for code which will be called periodically at a regular
     * rate while the robot is in autonomous mode.
     */
    public void autonomousPeriodic() {
        if (apFirstRun) {
            System.out.println("Default IterativeRobot.autonomousPeriodic() method... Overload me!");
            apFirstRun = false;
        }
        Timer.delay(0.001);
    }

    private boolean tpFirstRun = true;

    /**
     * Periodic code for teleop mode should go here.
     *
     * Users should override this method for code which will be called periodically at a regular
     * rate while the robot is in teleop mode.
     */
    public void teleopPeriodic() {
        if (tpFirstRun) {
            System.out.println("Default IterativeRobot.teleopPeriodic() method... Overload me!");
            tpFirstRun = false;
        }
        Timer.delay(0.001);
    }

    /* ----------- Overridable "continuous" code -----------------*/

    private boolean dcFirstRun = true;
    /**
     * Continuous code for disabled mode should go here.
     *
     * Users should override this method for code which will be called repeatedly as frequently
     * as possible while the robot is in disabled mode.
     */
    public void disabledContinuous() {
        if (dcFirstRun) {
            System.out.println("Default IterativeRobot.disabledContinuous() method... Overload me!");
            dcFirstRun = false;
        }
        m_ds.waitForData();
    }

    private boolean acFirstRun = true;

    /**
     * Continuous code for autonomous mode should go here.
     *
     * Users should override this method for code which will be called repeatedly as frequently
     * as possible while the robot is in autonomous mode.
     */
    public void autonomousContinuous() {
        if (acFirstRun) {
            System.out.println("Default IterativeRobot.autonomousContinuous() method... Overload me!");
            acFirstRun = false;
        }
        m_ds.waitForData();
    }

    private boolean tcFirstRun = true;

    /**
     * Continuous code for teleop mode should go here.
     *
     * Users should override this method for code which will be called repeatedly as frequently
     * as possible while the robot is in teleop mode.
     */
    public void teleopContinuous() {
        if (tcFirstRun) {
            System.out.println("Default IterativeRobot.teleopContinuous() method... Overload me!");
            tcFirstRun = false;
        }
        m_ds.waitForData();
    }

}
