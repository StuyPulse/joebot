/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.stuy;

import edu.stuy.camera.CameraVision;
import edu.stuy.commands.Autonomous;
import edu.stuy.commands.CommandBase;
import edu.stuy.commands.FlywheelRun;
import edu.stuy.commands.TusksRetract;
import edu.stuy.subsystems.Flywheel;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class JoeBot extends IterativeRobot {

    Command autonomousCommand;
//    Thread ariel;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        // instantiate the command used for the autonomous period
        // autonomousCommand = new ExampleCommand();

        if (!Devmode.DEV_MODE) {
            Compressor compressor = new Compressor(RobotMap.PRESSURE_SWITCH_CHANNEL, RobotMap.COMPRESSOR_RELAY_CHANNEL);
            compressor.start();
        }

        // Initialize all subsystems
        CommandBase.init();
//        if (!Devmode.DEV_MODE) {
//            CameraVision.getInstance().setCamera(true);
//        }
//        ariel = CameraVision.getInstance();
    }

    public void disabledPeriodic() {
        updateSmartDashboard();
        CommandBase.oi.resetBox();
//        CameraVision.getInstance().setCamera(false);
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
        new TusksRetract().start();
        autonomousCommand = new Autonomous();
        autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        updateSmartDashboard();
    }

    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
        new TusksRetract().start();
//        CameraVision.getInstance().setCamera(true);
//        ariel.start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();

        if (!DriverStation.getInstance().isFMSAttached()) {
            tuneShooter();
        }

        CommandBase.oi.updateLights();
        updateSmartDashboard();
    }

    // We use SmartDashboard to monitor bot information.
    // Here, we put things to the SmartDashboard
    private void updateSmartDashboard() {
        SmartDashboard.putDouble("Sonar distance (in)", CommandBase.drivetrain.getSonarDistance_in());
        SmartDashboard.putDouble("Button Pressed: ", CommandBase.oi.getDistanceButton());
        SmartDashboard.putDouble("Distance: ", CommandBase.oi.getDistanceFromDistanceButton());

        SmartDashboard.putBoolean("Acquirer In: ", CommandBase.oi.getDigitalValue(OI.ACQUIRER_IN_SWITCH_CHANNEL));
        SmartDashboard.putBoolean("Acquirer Out: ", CommandBase.oi.getDigitalValue(OI.ACQUIRER_OUT_SWITCH_CHANNEL));
        SmartDashboard.putBoolean("Shoot Button: ", CommandBase.oi.getDigitalValue(OI.SHOOTER_BUTTON_CHANNEL));
        SmartDashboard.putBoolean("Hoop Height Button: ", CommandBase.oi.getDigitalValue(OI.HOOP_HEIGHT_SWITCH_CHANNEL));
        SmartDashboard.putBoolean("Conveyor In: ", CommandBase.oi.getDigitalValue(OI.CONVEYOR_IN_SWITCH_CHANNEL));
        SmartDashboard.putBoolean("Conveyor Out: ", CommandBase.oi.getDigitalValue(OI.CONVEYOR_OUT_SWITCH_CHANNEL));

        SmartDashboard.putDouble("Auton Setting Switch: ", CommandBase.oi.getAutonSetting());
        SmartDashboard.putDouble("Speed Trim: ", CommandBase.oi.getSpeedPot());
        SmartDashboard.putDouble("Delay Pot: ", CommandBase.oi.getDelayPot());
        SmartDashboard.putDouble("Delay Time: ", CommandBase.oi.getDelayTime());
        SmartDashboard.putDouble("Max Voltage: ", CommandBase.oi.getMaxVoltage());

        SmartDashboard.putBoolean("Upper Conveyor Sensor: ", CommandBase.conveyor.upperSensor.get());
        SmartDashboard.putBoolean("Lower Conveyor Sensor: ", CommandBase.conveyor.lowerSensor.get());

        // Camera target info
//        SmartDashboard.putInt("Center of mass 0", CameraVision.getInstance().getCenterMass(0));
//        SmartDashboard.putInt("Center of mass 1", CameraVision.getInstance().getCenterMass(1));
//        SmartDashboard.putBoolean("Is aligned", CameraVision.getInstance().isAligned());
    }

    private void tuneShooter() {
        boolean useSmartDashboardTuning = false;
        try {
            useSmartDashboardTuning = SmartDashboard.getBoolean("useSDBtuning");
        }
        catch (NetworkTableKeyNotDefined e) {
            useSmartDashboardTuning = false;
            SmartDashboard.putBoolean("useSDBtuning", false);
        }
        double setRpmTop = 0;
        double setRpmBottom = 0;
        try {
            setRpmTop = SmartDashboard.getDouble("setRPMtop");
            setRpmBottom = SmartDashboard.getDouble("setRPMbottom");
        } catch (NetworkTableKeyNotDefined e) {
            SmartDashboard.putDouble("setRPMtop", 0);
            SmartDashboard.putDouble("setRPMbottom", 0);
        }
        CommandBase.flywheel.setFlywheelSpeeds(setRpmTop, setRpmBottom);


        double rpmTop = Flywheel.upperRoller.getRPM();
        double rpmBottom = Flywheel.lowerRoller.getRPM();
        try {
            SmartDashboard.putDouble("getRPMtop", rpmTop);
            SmartDashboard.putDouble("getRPMbottom", rpmBottom);
        } catch (Exception e) {
        }
        Flywheel.upperRoller.setPID("upper");
        Flywheel.lowerRoller.setPID("lower");
    }
}
