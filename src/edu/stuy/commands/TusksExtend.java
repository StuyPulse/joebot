/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Danny
 */
public class TusksExtend extends CommandBase {
    
    public TusksExtend() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(tusks);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        SmartDashboard.putString("TusksExtend", "start");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        tusks.extend();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return tusks.isExtended();
    }

    // Called once after isFinished returns true
    protected void end() {
         SmartDashboard.putString("TusksExtend", "stop");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
