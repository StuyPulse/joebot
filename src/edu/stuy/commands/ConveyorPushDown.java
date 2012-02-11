/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 *
 * @author belias
 */
public class ConveyorPushDown extends CommandBase {
    
    double timeout;
    boolean hasTimeout = false;

    public ConveyorPushDown() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        
        setInterruptible(false);
        requires(conveyor);
        requires(acquirer);
    }

    public ConveyorPushDown(double timeout) {
        this();
        hasTimeout = true;
        this.timeout = timeout;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        acquirer.stop();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        if (conveyor.ballAtBottom()) {
            conveyor.stop();
        }
        else {
            conveyor.conveyReverse();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return ((hasTimeout && isTimedOut()) ||   // timed out
                conveyor.ballAtBottom());         // or ball is pushed down
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
