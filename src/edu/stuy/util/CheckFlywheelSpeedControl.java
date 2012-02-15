/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.util;

import edu.stuy.commands.CommandBase;
import edu.stuy.subsystems.Shooter;


/**
 *
 * @author 694
 */
public class CheckFlywheelSpeedControl extends CommandBase {
    private double distanceInches;

    public CheckFlywheelSpeedControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(flywheel);
        distanceInches = Shooter.distances[Shooter.FENDER_INDEX];
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        setTimeout(5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double[] rpm = flywheel.lookupRPM(distanceInches, Shooter.speedsTopHoop);
        flywheel.setFlywheelSpeeds(rpm[0], rpm[1]);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (isTimedOut()) {
            System.out.println("Flywheel speed control FAIL! Timed out!");
            return true;
        }
        if (flywheel.isSpeedGood()) {
            System.out.println("Flywheel speed control PASS!");
            return true;
        }
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        flywheel.setFlywheelSpeeds(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}