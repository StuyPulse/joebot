/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.wpi.first.wpilibj.Compressor;

/**
 *
 * @author Kevin Wang
 */
public class DriveManualJoystickControl extends CommandBase {
    
    public DriveManualJoystickControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        drivetrain.compressor.start();
        
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        drivetrain.tankDrive(-oi.getLeftStick().getY(), -oi.getRightStick().getY());
        // If we're driving, stop the compressor from turning on. Otherwise, it's fine.
        if (oi.getLeftStick().getY() != 0 || oi.getRightStick().getY() != 0) {
            drivetrain.compressor.stop();
        } else {
            drivetrain.compressor.start();
        }
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
