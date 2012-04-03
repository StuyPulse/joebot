/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 * Vomit balls
 * @author Kevin Wang
 */

import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutonSetting3 extends CommandGroup {
    public AutonSetting3() {
       addSequential(new AutonVomit());
    }
}