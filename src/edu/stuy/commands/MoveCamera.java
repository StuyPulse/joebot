/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 *
 * @author Yulli
 */
public class MoveCamera extends CommandBase {
    
    boolean down;
    
    public MoveCamera() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(camera);
    }
    
    public MoveCamera(boolean down) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(camera);
        this.down = down;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        camera.switchView(down);
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
