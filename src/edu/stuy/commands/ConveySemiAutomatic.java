/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 *
 * @author Kevin Wang
 */
public class ConveySemiAutomatic extends CommandBase {
    
    public ConveySemiAutomatic() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(conveyor);
        requires(shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        conveyor.convey();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !shooter.isSpeedGood();
    }

    // Called once after isFinished returns true
    protected void end() {
        conveyor.roll(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
