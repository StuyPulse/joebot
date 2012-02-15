/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.stuy.subsystems.Flywheel;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author admin
 */
public class AutonSetting1 extends CommandGroup {

    public AutonSetting1() {
        addSequential(new AutonDriveToFender(Autonomous.INCHES_TO_FENDER));

        double distanceInches = Flywheel.distances[Flywheel.FENDER_INDEX];
        addSequential(new FlywheelRun(distanceInches, Flywheel.speedsTopHoop));
        addSequential(new ConveyAutomatic(4)); //4 sec is a raw value. Change.

        // TODO: Get tusks running concurrently with backing up, have them retract after backuptobridge is done
        addParallel(new TusksExtend());
        addSequential(new AutonBackUpToBridge(Autonomous.INCHES_TO_BRIDGE));
        addSequential(new TusksRetract());

    }
}
