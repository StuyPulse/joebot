/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.stuy.subsystems.Flywheel;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives to fender and shoots balls.
 * @author 694
 */
public class AutonSetting4 extends CommandGroup {

    public AutonSetting4() {
        addSequential(new AutonDriveToFender(Autonomous.INCHES_TO_FENDER));

        // TODO: Call ConveyAutomatic for a set time interval OR ConveySemiauto for two balls

        double distanceInches = Flywheel.distances[Flywheel.FENDER_INDEX];
        addSequential(new FlywheelRun(distanceInches, Flywheel.speedsTopHoop));
        addSequential(new ConveyAutomatic(Autonomous.CONVEY_AUTO_TIME)); //value of 4 is hardcoded. Please change.
    }
}
