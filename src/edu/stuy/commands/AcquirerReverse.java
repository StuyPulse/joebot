/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 *
 * @author Eric Lam
 */
public class AcquirerReverse extends CommandBase {
    boolean hasTimeout = false;
    double timeout;

    public AcquirerReverse() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(acquirer);
    }

    // Reverses acquirer for timeout seconds
    public AcquirerReverse(double timeout) {
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
        acquirer.acquireReverse();
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
        acquirer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
