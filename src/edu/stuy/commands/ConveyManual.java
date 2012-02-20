/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 *
 * @author Danny
 */
public class ConveyManual extends CommandBase {
    boolean hasTimeout = false;
    double timeout;
    
    public ConveyManual() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(conveyor);
    }
    
    public ConveyManual(double timeout){
        this();
        hasTimeout = true;
        this.timeout = timeout;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
        if (hasTimeout) {
            setTimeout(timeout);
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        conveyor.convey();
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
