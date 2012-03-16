/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author English
 */
public abstract class AutonDrive extends CommandBase {
    private int direction;
    private double timeoutTime;

    //Low gear speed 4 ft/s
    //High gear speed 10 ft/s
    public AutonDrive(int direction, double time) {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);
        this.direction = direction;
        timeoutTime = time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        this.setTimeout(timeoutTime);
        SmartDashboard.putString("AutonDrive", "start");
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute(){
        drivetrain.tankDrive(0.4 * direction, 0.4 * direction);
        // slower than full speed so that we actually bring down the bridge,
        // not just slam it and push balls the wrong way
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
        drivetrain.tankDrive(0, 0);

        SmartDashboard.putString("AutonDrive", "stop");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
