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
        
        SmartDashboard.putDouble("Left encoder distance", CommandBase.drivetrain.getLeftEncoderDistance());
        SmartDashboard.putDouble("Right encoder distance", CommandBase.drivetrain.getRightEncoderDistance());
        SmartDashboard.putDouble("Encoder average distance", CommandBase.drivetrain.getAvgDistance());
        
        SmartDashboard.putDouble("Gyro angle", CommandBase.drivetrain.getGyroAngle());
        
        SmartDashboard.putBoolean("Upper conveyor sensor", CommandBase.conveyor.ballAtTop());
        SmartDashboard.putBoolean("Lower conveyor sensor", CommandBase.conveyor.ballAtBottom());
        
        // TODO: Camera target info
    }
}
