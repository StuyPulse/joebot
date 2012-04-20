/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author prog694
 */
public class AutonVomitFast extends CommandGroup {
    
    public AutonVomitFast() {
       addParallel(new ConveyReverseManual(.5));
       addSequential(new AcquirerReverse(.5));
       addParallel(new ConveyReverseManual(3));
       addSequential(new AcquirerReverse(3));
    }
            
}
