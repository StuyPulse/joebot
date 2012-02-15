/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.util;

import edu.stuy.commands.*;
import edu.stuy.subsystems.Flywheel;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 * @author 694
 */
public class SystemsCheck extends CommandGroup {

    public SystemsCheck() {
        checkAcquirer();
        addSequential(new WaitCommand(1));
        checkConveyor();
        addSequential(new WaitCommand(1));
        checkShooter();
        addSequential(new WaitCommand(1));
        checkTusks();
    }

    private void checkAcquirer(){
        addSequential(new AcquirerAcquire(1));
        addSequential(new AcquirerReverse(1));
        addSequential(new AcquirerStop());
    }

    private void checkConveyor() {
        addSequential(new ConveyAutomatic(1));
        addSequential(new ConveyReverseManual(1));
        addSequential(new ConveyStop());
    }

    private void checkShooter() {
        addSequential(new CheckFlywheelSpeedControl());
        addSequential(new FlywheelStop());
    }

    private void checkTusks() {
        addSequential(new TusksExtend());
        addSequential(new WaitCommand(1));
        addSequential(new TusksRetract());
    }
}
