/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.stuy.Devmode;
import edu.stuy.RobotMap;
import edu.stuy.commands.DriveManualJoystickControl;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.*;

/**
 *
 * @author Kevin Wang
 */
public class Drivetrain extends Subsystem {
    private RobotDrive drive;
    private Solenoid gearShift;

    public Encoder leftEnc, rightEnc;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public Drivetrain() {
        Victor frontLeftMotor = new Victor(RobotMap.FRONT_LEFT_MOTOR);
        Victor rearLeftMotor = new Victor(RobotMap.REAR_LEFT_MOTOR);
        Victor frontRightMotor = new Victor(RobotMap.FRONT_RIGHT_MOTOR);
        Victor rearRightMotor = new Victor(RobotMap.REAR_RIGHT_MOTOR);
        drive = new RobotDrive(frontLeftMotor, rearLeftMotor, frontRightMotor, rearRightMotor);
        drive.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        drive.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        
        leftEnc = new Encoder(RobotMap.LEFT_ENCODER_A, RobotMap.LEFT_ENCODER_B);
        rightEnc = new Encoder(RobotMap.RIGHT_ENCODER_A, RobotMap.RIGHT_ENCODER_B);
        if (!Devmode.DEV_MODE) {
            gearShift = new Solenoid(RobotMap.GEAR_SHIFT);
        }
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new DriveManualJoystickControl());
    }

    public void tankDrive(double leftValue, double rightValue) {
        drive.tankDrive(leftValue, rightValue);
    }

    public void setGear(boolean high) {
        gearShift.set(high);
    }
}
