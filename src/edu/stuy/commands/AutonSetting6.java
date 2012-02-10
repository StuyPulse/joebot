/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author 694
 */
public class AutonSetting6 extends CommandGroup {

    // Does nothing.
    public AutonSetting6() {
        addSequential(new AutonDriveToFender(0));
    }
}