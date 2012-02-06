/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 *
 * @author 694
 */
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.stuy.subsystems.*;

public class AutonSetting4 extends CommandGroup {

    /**
     * Shoots at key.
     */
    public AutonSetting4() {
        double distanceInches = Shooter.distances[Shooter.KEY_INDEX];
        addSequential(new ShooterMoveFlyWheel(2.0, distanceInches));

    }
}
