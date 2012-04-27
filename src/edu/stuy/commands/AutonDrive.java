/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.commands;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author English
 */
public abstract class AutonDrive extends CommandBase {
    private int direction;
    private double timeoutTime;
    private double leftSpeed, rightSpeed;
    public double startTime = -1;

    //Low gear speed 4 ft/s
    //High gear speed 10 ft/s
    public AutonDrive(int direction, double time) {
        // Use requires() here to declare subsystem dependencies
        requires(drivetrain);
        this.direction = direction;
        leftSpeed = 0.7;
        rightSpeed = 0.6;
        timeoutTime = time;
    }
    
    public AutonDrive(int direction, double leftSpeed, double rightSpeed, double time) {
        this(1, time);
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        this.setTimeout(timeoutTime);
    }

    // Called repeatedly when this Command is scheduled to run
    public void execute(){
        double leftSpeedRamp, rightSpeedRamp;

        double runTime;
        if (startTime < 0) {
            startTime = Timer.getFPGATimestamp();
            runTime = 0;
        }
        else {
            runTime = Timer.getFPGATimestamp() - startTime;
        }

        if (runTime < 0.1) {
            leftSpeedRamp = 0.5 * leftSpeed;
            rightSpeedRamp = 0.5 * rightSpeed;
        }
        else if (runTime < 0.25) {
            leftSpeedRamp = 0.75 * leftSpeed;
            rightSpeedRamp = 0.75 * rightSpeed;
        }
        else {
            leftSpeedRamp = leftSpeed;
            rightSpeedRamp = rightSpeed;
        }

        drivetrain.tankDrive(leftSpeedRamp * direction, rightSpeedRamp * direction);
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}
