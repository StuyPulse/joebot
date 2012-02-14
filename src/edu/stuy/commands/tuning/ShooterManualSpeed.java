/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands.tuning;

import edu.stuy.commands.CommandBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author 694
 */
public class ShooterManualSpeed extends CommandBase {

    public ShooterManualSpeed() {
        requires(flywheel);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        System.out.println("rpmTop, rpmBottom"); // column headers for CSV
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        setManualSpeed();
        showSpeed();
    }

    public void setManualSpeed() {
        double rpmTop = 0;
        double rpmBottom = 0;
        try {
            rpmTop = SmartDashboard.getDouble("setRPMtop");
            rpmBottom = SmartDashboard.getDouble("setRPMbottom");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        flywheel.setFlywheelSpeeds(rpmTop, rpmBottom);
    }

    public void showSpeed() {
        double rpmTop = flywheel.upperRoller.getRPM();
        double rpmBottom = flywheel.lowerRoller.getRPM();
        try {
            SmartDashboard.putDouble("getRPMtop", rpmTop);
            SmartDashboard.putDouble("getRPMbottom", rpmBottom);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(rpmTop + ", " + rpmBottom);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}