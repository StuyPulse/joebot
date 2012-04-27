package edu.stuy.commands;

/**
 * Drives to fender, shoots balls at two point hoop.
 * @author 694
 */

import edu.stuy.subsystems.Flywheel;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonSetting7 extends CommandGroup {

    public AutonSetting7() {
        double leftSpeed, rightSpeed, time;
        if (SmartDashboard.getBoolean("SDB auton drive tuning", false)) {
            leftSpeed = SmartDashboard.getDouble("Auton left speed", 0.0);
            rightSpeed = SmartDashboard.getDouble("Auton right speed", 0.0);
            time = SmartDashboard.getDouble("Auton drive time", 0.0);
        }
        else {
            leftSpeed = 0.6;
            rightSpeed = 0.6;
            time = Autonomous.t_closeKeyToFender;
        }
        addSequential(new AutonDriveToFender(leftSpeed, rightSpeed, time));
        double distanceInches = Flywheel.distances[Flywheel.FENDER_WIDE_INDEX];
        addParallel(new AutonWaitThenConvey());
        addSequential(new FlywheelRun(distanceInches, Flywheel.speedsTopHoop));
    }
}