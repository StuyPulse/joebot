package edu.stuy.commands;

/**
 * Drives to fender, shoots balls.
 * @author admin
 */

import edu.stuy.subsystems.Flywheel;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonSetting6 extends CommandGroup {

    public AutonSetting6() {
        addSequential(new AutonDriveToFender(Autonomous.t_closeKeyToFender));
        double distanceInches = Flywheel.distances[Flywheel.FENDER_INDEX];
        addParallel(new AutonWaitThenConvey());
        addSequential(new FlywheelRun(distanceInches, Flywheel.speedsTopHoop));
    }
}
