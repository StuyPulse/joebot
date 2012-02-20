/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.stuy.subsystems.Flywheel;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives to Fender, shoots balls
 * @author admin
 */
public class AutonSetting0 extends CommandGroup {

    public AutonSetting0() {
        addSequential(new AutonDriveToFender(Autonomous.INCHES_TO_FENDER));

        double distanceInches = Flywheel.distances[Flywheel.FENDER_INDEX];
        addParallel(new FlywheelRun(distanceInches, Flywheel.speedsTopHoop));
        addSequential(new ConveyAutomatic(Autonomous.CONVEY_AUTO_TIME));

    }
}
