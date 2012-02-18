/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.stuy.subsystems.Flywheel;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Backs up to and knocks down bridge, and then drives to fender and shoots.
 */
public class AutonSetting1 extends CommandGroup {

    public AutonSetting1() {
        addSequential(new TusksExtend());

        addSequential(new AutonBackUpToBridge(Autonomous.INCHES_TO_BRIDGE - Autonomous.INCHES_TO_FENDER));

        addSequential(new TusksRetract());

        addSequential(new AutonDriveToFender(Autonomous.INCHES_TO_BRIDGE));
        // TODO: Call ConveyAutomatic for a set time interval OR ConveySemiauto for two balls

        double distanceInches = Flywheel.distances[Flywheel.FENDER_INDEX];
        addParallel(new FlywheelRun(distanceInches, Flywheel.speedsTopHoop));
        addSequential(new ConveyAutomatic(Autonomous.CONVEY_AUTO_TIME));
    }
}
