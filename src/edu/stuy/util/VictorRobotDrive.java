/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.util;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;

/**
 * Extends RobotDrive, forcing it to work with Victors rather than CANJaguars
 * when serial CAN is enabled on the cRIO.
 * @author Kevin Wang
 */
public class VictorRobotDrive extends RobotDrive {
    
    public VictorRobotDrive(int frontLeftMotor, int rearLeftMotor,
            int frontRightMotor, int rearRightMotor) {
        super(new Victor(frontLeftMotor), new Victor(rearLeftMotor), new Victor(frontRightMotor), new Victor(rearRightMotor));
    }
    
    /**
     * Forces RobotDrive to not use CAN.
     * @param leftOutput
     * @param rightOutput 
     */
    public void setLeftRightMotorOutputs(double leftOutput, double rightOutput) {
        m_isCANInitialized = false;
        super.setLeftRightMotorOutputs(leftOutput, rightOutput);
    }
}
