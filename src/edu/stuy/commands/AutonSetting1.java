/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author admin
 */
public class AutonSetting1 extends CommandGroup {

    public AutonSetting1() {
        addSequential(new AutonDriveToFender(Autonomous.INCHES_TO_FENDER));

        // TODO: Call ConveyAutomatic for a set time interval OR ConveySemiauto for two balls
        addSequential(new ShooterShoot(2.0));

        // TODO: Get tusks running concurrently with backing up, have them retract after backuptobridge is done
        addParallel(new TusksExtend());

        addSequential(new AutonBackUpToBridge(Autonomous.INCHES_TO_BRIDGE));
        addSequential(new TusksRetract());

    }
}
