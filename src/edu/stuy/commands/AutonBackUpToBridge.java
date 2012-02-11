/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 *
 * @author Kevin Wang
 */
public class AutonBackUpToBridge extends AutonDrive {
    public AutonBackUpToBridge(double inches) {
        super(inches, -1);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        super.initialize(); // Enables "drive straight" controller
    }
}
