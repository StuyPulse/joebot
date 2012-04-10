package edu.stuy.commands;

/**
 * Backs up to bridge from close key.
 * @author admin
 */

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonSetting5 extends CommandGroup {
    
    public AutonSetting5() {
        addSequential(new AutonBackUpToBridge(Autonomous.t_closeKeyToBridge));
    }
}
