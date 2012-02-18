/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 *
 * @author 694
 */
import edu.stuy.subsystems.Flywheel;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonSetting3 extends CommandGroup {

    /**
     * Shoots at key.
     */
    public AutonSetting3() {
        double distanceInches = Flywheel.distances[Flywheel.KEY_INDEX];
        addSequential(new FlywheelRun(distanceInches, Flywheel.speedsTopHoop));
        addSequential(new ConveyAutomatic(4)); //value of 4 is hardcoded. Please change.
    }
}
