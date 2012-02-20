/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.stuy.subsystems.Flywheel;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives to fender and shoots balls and backs up to bridge.
 * @author 694
 */
public class AutonSetting3 extends CommandGroup {

    public AutonSetting3() {
        addSequential(new AutonDriveToFender(Autonomous.INCHES_TO_FENDER));

        // TODO: Call ConveyAutomatic for a set time interval OR ConveySemiauto for two balls

        double distanceInches = Flywheel.distances[Flywheel.FENDER_INDEX];
        addParallel(new FlywheelRun(distanceInches, Flywheel.speedsTopHoop));
        addSequential(new ConveyAutomatic(Autonomous.CONVEY_AUTO_TIME)); //value of 4 is hardcoded. Please change.
        
        addParallel(new TusksExtend());

        addSequential(new AutonBackUpToBridge(Autonomous.INCHES_TO_BRIDGE - Autonomous.INCHES_TO_FENDER));

        addSequential(new TusksRetract());
    }
}
