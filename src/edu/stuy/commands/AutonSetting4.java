/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 * Vomit balls and push down bridge
 * @author Kevin Wang
 */

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonSetting4 extends CommandGroup {
   public AutonSetting4() {
       addSequential(new AutonVomit());
       addSequential(new TusksExtend());
       addSequential(new AutonBackUpToBridge(Autonomous.t_closeKeyToBridge));
    }
}