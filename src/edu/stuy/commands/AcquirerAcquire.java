/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 *
 * @author Kevin Wang
 */
public class AcquirerAcquire extends CommandBase {
    boolean hasTimeout = false;
    double timeout;
    
    public AcquirerAcquire() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(acquirer);
        requires(shooter);
    }

    public AcquirerAcquire(double timeout) {
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
        boolean top = conveyor.ballAtTop();
        boolean bottom = conveyor.ballAtBottom();

        if (top && bottom) {
            conveyor.stop();
            acquirer.stop();
        }
        else if (top && !bottom) {
            conveyor.stop();
            acquirer.acquire();
        }
        else if (!top && bottom) {
            conveyor.convey();
            acquirer.acquire();
        }
        else if (!top && !bottom) {
            conveyor.stop();
            acquirer.acquire();
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
        acquirer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
