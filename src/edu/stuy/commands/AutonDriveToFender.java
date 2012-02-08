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
    public AutonDriveToFender(double inches) {
        super(inches, 1);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        super.initialize(); // Enables "drive straight" controller
        drivetrain.setForward();
    }
}
