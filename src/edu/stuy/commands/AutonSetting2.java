/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.commands;

/**
 * Knocks down bridge.
 * @author 694
 */
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonSetting2 extends CommandGroup {

    public AutonSetting2() {
        // TODO: Get tusks running concurrently with backing up, have them retract after backuptobridge is done
        addSequential(new TusksExtend());

        addSequential(new AutonBackUpToBridge(Autonomous.INCHES_TO_BRIDGE - Autonomous.INCHES_TO_FENDER));

        addSequential(new TusksRetract());
    }
}
