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
import edu.stuy.commands.ConveyorPushDown;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
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
        
        CameraVision.getInstance();
        CameraVision.getInstance().doCamera();
        CameraVision.getInstance().toggleTargetLightIfAligned();
    }
    
    public void disabledPeriodic() {
        updateSmartDashboard();
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
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

                // Note that OI starts a bunch of other commands
                // by attaching them to joystick buttons.  Check OI.java
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        //
        Scheduler.getInstance().run();
        
        // Debug box actions
        CommandBase.oi.updateLights();
        updateSmartDashboard();
    }
    
    private void updateSmartDashboard() {
        SmartDashboard.putDouble("Sonar distance (in)", CommandBase.drivetrain.getSonarDistance_in());
        SmartDashboard.putDouble("Button Pressed: ", CommandBase.oi.getDistanceButton());
        SmartDashboard.putBoolean("Auto: ", CommandBase.oi.getValue(OI.DISTANCE_BUTTON_AUTO));
        SmartDashboard.putBoolean("Far: ", CommandBase.oi.getValue(OI.DISTANCE_BUTTON_FAR));
        SmartDashboard.putBoolean("Length: ", CommandBase.oi.getValue(OI.DISTANCE_BUTTON_FENDER_WIDE));
        SmartDashboard.putBoolean("Width: ", CommandBase.oi.getValue(OI.DISTANCE_BUTTON_FENDER_NARROW));
        SmartDashboard.putBoolean("Fender Side: ", CommandBase.oi.getValue(OI.DISTANCE_BUTTON_FENDER_SIDE));
        SmartDashboard.putBoolean("Fender: ", CommandBase.oi.getValue(OI.DISTANCE_BUTTON_FENDER));
        SmartDashboard.putBoolean("Stop: ", CommandBase.oi.getValue(OI.DISTANCE_BUTTON_STOP));
        
        
        // TODO: Camera target info
    }
}
