/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

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
    
    
    boolean hasTimeout = false;
    double timeout;
    
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
                
        if (flywheel.isSpeedGood() && flywheel.isSpinning() && // flywheel's at correct speed
                flywheel.isSpeedSettled() &&                                // flywheel's been stable at correct speed for long enough
                conveyor.ballSettled) {  // conveyor's not accelerating ball
            conveyor.convey();
            
            // Restart ball delay, only after ball exits the top sensor zone
            // Without this check, this algorithm would continuously halt the 
            // ball as it passes through the sensor's line of sight
            if (!conveyor.curBallAtTop) {
                conveyor.startBallDelayTime = -1;
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
