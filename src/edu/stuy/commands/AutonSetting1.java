/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 * @author admin
 */
public class AutonSetting1 extends CommandGroup {

    public AutonSetting1() {
        addSequential(new AutonDriveToFender());
        addSequential(new ShooterShoot(2.0));
        addSequential(new AutonBackUpToBridge());
        addSequential(new AcquirerAcquire(2.0));
    }
}
