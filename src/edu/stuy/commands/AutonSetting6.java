/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.stuy.subsystems.*;

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