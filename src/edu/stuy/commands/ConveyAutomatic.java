/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 *
 * @author Kevin Wang
 */
public class ConveyAutomatic extends CommandBase {
    
    boolean hasTimeout = false;
    double timeout;

    public ConveyAutomatic() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(conveyor);
        requires(flywheel);
    }

    public ConveyAutomatic(double timeout) {
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
        if (flywheel.isSpeedGood() || !conveyor.ballAtTop()) {
            conveyor.convey();
        }
        else {
            conveyor.stop();
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
//        (new ConveyorPushDown(1)).start();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
