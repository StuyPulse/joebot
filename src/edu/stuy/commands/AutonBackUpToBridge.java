/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Kevin Wang
 */
public class AutonBackUpToBridge extends AutonDrive {
    public AutonBackUpToBridge(double time) {
        super(-1, time);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        super.initialize(); // Enables "drive straight" controller
    }
}
