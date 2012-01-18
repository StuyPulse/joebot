package edu.wpi.first.wpilibj;

import edu.wpi.first.wpilibj.command.Scheduler;

/**
 * This class represents a robot which uses the Command architecture.
 *
 * <p>It contains methods that will be called when any of the
 * {@link CommandBasedRobot#operatorControl() teleop},
 * {@link CommandBasedRobot#autonomous() autonomous}
 * or {@link CommandBasedRobot#disabled() disabled} modes
 * first begin</p>
 *
 * <p>It also calls the Scheduler often, so that the Command system may function.</p>
 *
 * @see edu.wpi.first.wpilibj.command.Command Command
 * @author Joe Grinstead
 */
public class CommandBasedRobot extends RobotBase {

    /**
     * Robot-wide initialization code should go here.
     *
     * Users should override this method for default Robot-wide initialization which will
     * be called when the robot is first powered on.
     *
     * Called exactly 1 time when the competition starts.
     */
    protected void robotInit() {
    }

    /**
     * Disabled should go here.
     * Users should overload this method to run code that should run while the field is
     * disabled.
     */
    protected void disabled() {
    }

    /**
     * Autonomous should go here.
     * Users should add autonomous code to this method that should run while the field is
     * in the autonomous period.
     */
    public void autonomous() {
    }

    /**
     * Operator control (tele-operated) code should go here.
     * Users should add Operator Control code to this method that should run while the field is
     * in the Operator Control (tele-operated) period.
     */
    public void operatorControl() {
    }

    /**
     * Start a competition.
     * This code tracks the order of the field starting to ensure that everything happens
     * in the right order. Repeatedly run the correct method, either Autonomous or OperatorControl
     * when the robot is enabled. After running the correct method, wait for some state to change,
     * either the other mode starts or the robot is disabled. Then go back and wait for the robot
     * to be enabled again.
     */
    public void startCompetition() {
        robotInit();

        while (true) {
            if (isDisabled()) {
                disabled();
                while (isDisabled()) {
                    getWatchdog().feed();
                    m_ds.waitForData();
                    getWatchdog().feed();
                    Scheduler.getInstance().run();
                }
            } else if (isAutonomous()) {
                autonomous();
                while (isAutonomous() && !isDisabled()) {
                    getWatchdog().feed();
                    m_ds.waitForData();
                    getWatchdog().feed();
                    Scheduler.getInstance().run();
                }
            } else {
                operatorControl();
                while (isOperatorControl() && !isDisabled()) {
                    getWatchdog().feed();
                    m_ds.waitForData();
                    getWatchdog().feed();
                    Scheduler.getInstance().run();
                }
            }
        }
    }
}
