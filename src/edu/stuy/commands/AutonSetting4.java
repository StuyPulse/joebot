/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

/**
 *
 * @author 694
 */
import edu.stuy.subsystems.Flywheel;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutonSetting4 extends CommandGroup {

    /**
     * Backs up from far key
     */
    public AutonSetting4() {
        SmartDashboard.putInt("Auton commandgroup added", 4);
        double distanceInches = Flywheel.distances[Flywheel.FAR_KEY_INDEX];
        addSequential(new TusksExtend());
        addSequential(new AutonBackUpToBridge(Autonomous.t_farKeyToBridge));
    }
}
