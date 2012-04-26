package edu.stuy.commands;

/**
 * Drives to fender, shoots balls at two point hoop.
 * @author 694
 */

import edu.stuy.subsystems.Flywheel;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonSetting7_3Point extends CommandGroup {

    public AutonSetting7_3Point() {
        addSequential(new AutonDriveToFender(Autonomous.t_closeKeyToFender));
        double distanceInches = Flywheel.distances[Flywheel.FENDER_INDEX];
        addParallel(new AutonWaitThenConvey());
        addSequential(new FlywheelRun(distanceInches, Flywheel.speedsTopHoop));
    }
}