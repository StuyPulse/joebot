/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.stuy;


import edu.stuy.commands.Autonomous;
import edu.stuy.commands.CommandBase;
import edu.stuy.commands.ConveyorPushDown;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

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
            autonomousCommand = new Autonomous();
            Compressor compressor = new Compressor(RobotMap.PRESSURE_SWITCH_CHANNEL, RobotMap.COMPRESSOR_RELAY_CHANNEL);
            compressor.start();
        }

        // Initialize all subsystems
        CommandBase.init();
    }

    public void autonomousInit() {
        // schedule the autonomous command (example)
        autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to 
		// continue until interrupted by another command, remove
		// this line or comment it out.
		autonomousCommand.cancel();

                // Note that OI starts a bunch of other commands
                // by attaching them to joystick buttons.  Check OI.java
                (new ConveyorPushDown()).start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        //
        Scheduler.getInstance().run();
        
        // Debug box actions
    }
}
