/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.stuy.subsystems.*;

/**
 *
 * @author admin
 */
public class AutonSetting1 extends CommandGroup {

    public AutonSetting1() {
        addSequential(new AutonDriveToFender(Autonomous.INCHES_TO_FENDER));

        double distanceInches = Shooter.distances[Shooter.FENDER_INDEX];
        addSequential(new ShooterMoveFlyWheel(2.0, distanceInches));


        // TODO: Get tusks running concurrently with backing up, have them retract after backuptobridge is done
        addParallel(new TusksExtend());

        addSequential(new AutonBackUpToBridge(Autonomous.INCHES_TO_BRIDGE));
        addSequential(new TusksRetract());

    }
}
