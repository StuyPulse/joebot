/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Kevin Wang
 */
public class ConveyAutomaticAuton extends CommandBase {
    double PAUSE_DURATION = 1;
    
    boolean hasTimeout = false;
    double timeout;
    double pauseTime = 0;

    public ConveyAutomaticAuton() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(conveyor);
    }

    public ConveyAutomaticAuton(double timeout) {
        this();
        hasTimeout = true;
        this.timeout = timeout;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        setTimeout(timeout);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (!flywheel.isSpeedGood()) {
            pauseTime = Timer.getFPGATimestamp();
            conveyor.stop();
        }
        else if (Timer.getFPGATimestamp() - pauseTime < PAUSE_DURATION || !flywheel.isSpinning()) {
            conveyor.stop();
        }
        else {
            conveyor.convey();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (hasTimeout) {
            return isTimedOut();
        }
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        conveyor.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
