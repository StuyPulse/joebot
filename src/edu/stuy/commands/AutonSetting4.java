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

public class AutonSetting4 extends CommandGroup {

    public AutonSetting4() {
        // TODO: Get tusks running concurrently with backing up, have them retract after backuptobridge is done
        addSequential(new TusksExtend());

        addSequential(new AutonBackUpToBridge(Autonomous.INCHES_TO_BRIDGE - Autonomous.INCHES_TO_FENDER));

        // addSequential(new ShooterShoot(2.0, Autonomous.FENDER_SPEED));

        addSequential(new TusksRetract());
    }
}
