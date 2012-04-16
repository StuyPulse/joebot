/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 * @author Kevin Wang
 */
public class ConveyAutomatic extends CommandBase {
    /**
     * Don't shoot until the ball has been sitting at the top of the conveyor
     * for at least this much time.
     * Prevents ball from being shot with extra energy due to the conveyor.
     * Longer / shorter conveyor runs can kill our consistency.
     */
    public static final double BALL_STATIONARY_TIME = 0.5;
    
    
    
    boolean hasTimeout = false;
    double timeout;
    
    double startBallDelayTime = -1;
    
    public ConveyAutomatic() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(conveyor);
    }

    public ConveyAutomatic(double timeout) {
        this();
        hasTimeout = true;
        this.timeout = timeout;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        setTimeout(timeout);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        // Has the ball settled at the top?
        boolean curBallAtTop = conveyor.ballAtTop();
        if (curBallAtTop && startBallDelayTime < 0) startBallDelayTime = Timer.getFPGATimestamp();
        double ballWaitTime = (startBallDelayTime > 0) ? Timer.getFPGATimestamp() - startBallDelayTime : -1;
        boolean ballSettled = startBallDelayTime < 0 || ballWaitTime > BALL_STATIONARY_TIME;
        
        
        if (flywheel.isSpeedGood() && flywheel.isSpinning() && // flywheel's at correct speed
                flywheel.isSpeedSettled() &&                                // flywheel's been stable at correct speed for long enough
                ballSettled) {  // conveyor's not accelerating ball
            conveyor.convey();
            
            // Restart ball delay, only after ball exits the top sensor zone
            // Without this check, this algorithm would continuously halt the 
            // ball as it passes through the sensor's line of sight
            if (!curBallAtTop) {
                startBallDelayTime = -1;
            }
        }
        else {
            conveyor.stop();
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
        conveyor.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
