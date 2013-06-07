/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.stuy.Constants;
import edu.wpi.first.wpilibj.RobotDrive;

/**
 *
 * @author ivy
 */
public class Drivetrain {
    
    private RobotDrive drivetrain;
    
    private Drivetrain() {
        drivetrain = new RobotDrive(Constants.FRONT_LEFT_MOTOR,Constants.REAR_LEFT_MOTOR,Constants.FRONT_RIGHT_MOTOR,Constants.REAR_RIGHT_MOTOR);
    }
    
}
