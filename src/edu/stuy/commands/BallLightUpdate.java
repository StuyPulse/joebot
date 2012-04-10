/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author admin
 */
public class BallLightUpdate extends CommandBase {
    private int BLINK_FREQUENCY_HZ = 7;
    private double lastTime = 0;
    
    public BallLightUpdate() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(ballLight);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        double time = Timer.getFPGATimestamp();
        if (conveyor.ballAtTop()) {
            if (time - lastTime > (1.0 / BLINK_FREQUENCY_HZ)) {
                ballLight.setLight(!ballLight.isOn()); // Invert ball light every 1/frequency (period) seconds
                lastTime = time;
            }
        }
        else if (conveyor.ballAtBottom()) {
            ballLight.setLight(true);
        }
        else {
            ballLight.setLight(false);
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
