/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.commands;

import edu.stuy.subsystems.Flywheel;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Kevin Wang
 */
public class FlywheelRun extends CommandBase {

    double distanceInches;
    double[] speeds;
    boolean useOI;
    
    public FlywheelRun(double distanceInches, double[] speeds) {
        requires(flywheel);
        this.distanceInches = 0;
        setDistanceInches(distanceInches);
        this.speeds = speeds;
        useOI = false;
    }

    public FlywheelRun() {
        requires(flywheel);
        this.speeds = Flywheel.speedsTopHoop;
        useOI = true;
    }

    /**
     * Sets the distance setpoint instance variable to a distance.
     *
     * If the distance has changed from the last check, reset the Jaguars.
     *
     * @param newDistanceInches Distance setpoint
     */
    private void setDistanceInches(double newDistanceInches) {

        // If the new distance is different from the old distance, reset the Jaguars
        if (Math.abs(distanceInches - newDistanceInches) > 0.1) {
            flywheel.resetJaguars();
        }

        //Set the new distanceInches
        distanceInches = newDistanceInches;

    }

    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        // SmartDashboard should override OI or passed-in distance when tuning
        if (useSmartDashboardTuning()) {
                tuneShooter();
                return;
        }
        if (useOI) {
            setDistanceInches(CommandBase.oi.getDistanceFromDistanceButton());


            if (CommandBase.oi.getHoopHeightButton()) {
                this.speeds = Flywheel.speedsTopHoop;
            } else {
                this.speeds = Flywheel.speedsMiddleHoop;
            }
        }
        double[] rpm;
        if(distanceInches < 0){
            rpm = new double[2];
            rpm[0] = Flywheel.reverseRPM;
            rpm[1] = Flywheel.reverseRPM;
        }
        else{
            rpm = flywheel.lookupRPM(distanceInches, speeds);
            if (rpm[0] != 0 && rpm[1] != 0) {
                trimSpeedsFromOI(rpm);
            }
        }
        flywheel.setFlywheelSpeeds(rpm[0], rpm[1]);
        //SmartDashboard.putDouble("setRPMtop", rpm[0]);
        //SmartDashboard.putDouble("setRPMottom", rpm[1]);
    }

    public void trimSpeedsFromOI(double[] rpms) {
        double speedTrim = CommandBase.oi.getSpeedTrim();
        rpms[0] += speedTrim;
        rpms[1] += speedTrim;
    }
    
    private boolean useSmartDashboardTuning() {
        boolean useSmartDashboardTuning = false;
        try {
            try {
                useSmartDashboardTuning = SmartDashboard.getBoolean("useSDBtuning");
            } catch (NetworkTableKeyNotDefined e) {
                useSmartDashboardTuning = false;
                SmartDashboard.putBoolean("useSDBtuning", false);
            }
        }
        catch (Exception e) { // Don't use SmartDashboard if anything went wrong
                              // (i.e. SmartDashboard throws another Exception)
        }
        return useSmartDashboardTuning;
    }

    private void tuneShooter() {
        double RPM = 0;
//        double setRpmBottom = 0;
        try {
            RPM = SmartDashboard.getDouble("setRPM");
//            setRpmBottom = SmartDashboard.getDouble("setRPMbottom");
        } catch (NetworkTableKeyNotDefined e) {
            SmartDashboard.putDouble("setRPM", 0);
//            SmartDashboard.putDouble("setRPMbottom", 0);
        }
        CommandBase.flywheel.setFlywheelSpeeds(RPM, RPM);

//        Flywheel.upperRoller.setPID("upper");
//        Flywheel.lowerRoller.setPID("lower");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
        flywheel.setFlywheelSpeeds(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
