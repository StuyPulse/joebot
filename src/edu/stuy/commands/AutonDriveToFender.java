/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 *
 * @author Kevin Wang
 */
public class AutonDriveToFender extends AutonDrive {
    public AutonDriveToFender(double time) {
        super(1, time);
    }
    
    public AutonDriveToFender(double leftSpeed, double rightSpeed, double time) {
        super(1, leftSpeed, rightSpeed, time);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        super.initialize(); // Enables "drive straight" controller
    }
}
