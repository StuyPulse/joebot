/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 * Shoot from key at slanted position at top hoop.
 * @author admin
 */

import edu.stuy.subsystems.Flywheel;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonSetting5 extends CommandGroup {
   public AutonSetting5() {
        double distanceInches = Flywheel.distances[Flywheel.KEY_SLANT_INDEX];
        addParallel(new FlywheelRun(distanceInches, Flywheel.speedsTopHoop));
        addSequential(new ConveyAutomaticAuton(Autonomous.CONVEY_AUTO_TIME));
    }
}