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
        requires(shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        setManualSpeed();
    }

    public void setManualSpeed() {
        double rpm = 0;
        try {
            rpm = SmartDashboard.getDouble("setRPM");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        shooter.setFlywheelSpeeds(rpm, rpm);
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