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

    boolean hasTimeout = false;
    double timeout;

    public ConveyorPushDown(double timeout) {
        requires(conveyor);
        hasTimeout = true;
        this.timeout = timeout;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        setTimeout(timeout);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
            conveyor.conveyReverse();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return ((hasTimeout && isTimedOut()) ||   // timed out
                conveyor.ballAtBottom());         // or ball is pushed down
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
