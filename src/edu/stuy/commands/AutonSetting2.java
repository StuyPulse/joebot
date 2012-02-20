/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 * Shoots from key.
 * @author 694
 */
import edu.stuy.subsystems.Flywheel;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonSetting2 extends CommandGroup {

    /**
     * Shoots from key.
     */
    public AutonSetting2() {
        double distanceInches = Flywheel.distances[Flywheel.KEY_INDEX];
        addParallel(new AutonWaitThenConvey());
        addSequential(new FlywheelRun(distanceInches, Flywheel.speedsTopHoop));
    }
}
