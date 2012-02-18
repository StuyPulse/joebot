/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.stuy.subsystems.Flywheel;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives to Fender, shoots balls, then backs up to bridge, knocks down bridge.
 * @author admin
 */
public class AutonSetting0 extends CommandGroup {

    public AutonSetting0() {
        addSequential(new AutonDriveToFender(Autonomous.INCHES_TO_FENDER));

        double distanceInches = Flywheel.distances[Flywheel.FENDER_INDEX];
        addParallel(new FlywheelRun(distanceInches, Flywheel.speedsTopHoop));
        addSequential(new ConveyAutomatic(Autonomous.CONVEY_AUTO_TIME));

        // TODO: Get tusks running concurrently with backing up, have them retract after backuptobridge is done
        addParallel(new TusksExtend());
        addSequential(new AutonBackUpToBridge(Autonomous.INCHES_TO_BRIDGE));
        addSequential(new TusksRetract());

    }
}
