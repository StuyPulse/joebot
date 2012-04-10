/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 * Shoots from close key.
 * @author 694
 */
import edu.stuy.subsystems.Flywheel;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonSetting1 extends CommandGroup {

    public AutonSetting1() {
        double distanceInches = Flywheel.distances[Flywheel.CLOSE_KEY_INDEX];
        addParallel(new AutonWaitThenConvey());
        addSequential(new FlywheelRun(distanceInches, Flywheel.speedsTopHoop));
    }
}
