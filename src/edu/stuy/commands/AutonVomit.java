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
        //First ball
       addParallel(new ConveyReverseManual(1.25));
       addSequential(new AcquirerReverse(3.25));

       //Second ball
       addParallel(new ConveyReverseManual(1.875));
       addSequential(new AcquirerReverse(1.875));
    }
}