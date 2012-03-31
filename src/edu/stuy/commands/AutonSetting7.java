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

public class AutonSetting7 extends CommandGroup {
   public AutonSetting7() {
       addSequential(new AutonVomit());
       addSequential(new TusksExtend());
       addSequential(new AutonBackUpToBridge(Autonomous.t_closeKeyToBridge));
    }
}