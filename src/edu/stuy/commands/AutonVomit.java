/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 * @author 694
 */
public class AutonVomit extends CommandGroup {

    public AutonVomit() {
       addParallel(new ConveyReverseManual(.5));
       addSequential(new AcquirerReverse(.5));
       addSequential(new WaitCommand(1));
       addParallel(new ConveyReverseManual(3));
       addSequential(new AcquirerReverse(3));
    }
}