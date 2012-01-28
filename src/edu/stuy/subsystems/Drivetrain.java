/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.stuy.Devmode;
import edu.stuy.RobotMap;
import edu.stuy.commands.DriveManualJoystickControl;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Encoder;

/**
 *
 * @author Kevin Wang
 */
public class Drivetrain extends Subsystem {
    RobotDrive drive;
    Solenoid gearShift;
    public Encoder leftEnc, rightEnc;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public Drivetrain() {
        drive = new RobotDrive(RobotMap.FRONT_LEFT_MOTOR, RobotMap.REAR_LEFT_MOTOR, RobotMap.FRONT_RIGHT_MOTOR, RobotMap.REAR_RIGHT_MOTOR);
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
