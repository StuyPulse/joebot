/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.stuy.subsystems.*;

/**
 *
 * @author 694
 */
public class AutonSetting5 extends CommandGroup {

    public AutonSetting5() {
        addSequential(new AutonDriveToFender(Autonomous.INCHES_TO_FENDER));

        // TODO: Call ConveyAutomatic for a set time interval OR ConveySemiauto for two balls

        double distanceInches = Shooter.distances[Shooter.FENDER_INDEX];
        addSequential(new ShooterMoveFlyWheel(2.0, distanceInches));
        addSequential(new ConveyAutomatic(4)); //value of 4 is hardcoded. Please change.
    }
}
