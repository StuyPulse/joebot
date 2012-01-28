/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.stuy.Devmode;
import edu.stuy.RobotMap;
import edu.stuy.commands.DriveManualJoystickControl;
import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Kevin Wang
 */
public class Drivetrain extends Subsystem {

    RobotDrive drive;
    Solenoid gearShift;
    AnalogChannel sonar;
    Encoder encoderLeft;
    Encoder encoderRight;
    Gyro gyro;
    final int WHEEL_RADIUS                 = 3; 
    final double CIRCUMFERENCE             = 2 * Math.PI * WHEEL_RADIUS;
    final int ENCODER_CODES_PER_REV        = 360;
    final double DISTANCE_PER_PULSE        = CIRCUMFERENCE / ENCODER_CODES_PER_REV;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public Drivetrain() {
        drive = new RobotDrive(RobotMap.FRONT_LEFT_MOTOR, RobotMap.REAR_LEFT_MOTOR, RobotMap.FRONT_RIGHT_MOTOR, RobotMap.REAR_RIGHT_MOTOR);

        encoderLeft = new Encoder(RobotMap.ENCODER_CHANNEL_1A, RobotMap.ENCODER_CHANNEL_1B, true);
        encoderRight = new Encoder(RobotMap.ENCODER_CHANNEL_2A, RobotMap.ENCODER_CHANNEL_2B, true);

        encoderLeft.setDistancePerPulse(DISTANCE_PER_PULSE);
        encoderRight.setDistancePerPulse(DISTANCE_PER_PULSE);

        gyro = new Gyro(RobotMap.GYRO_CHANNEL);

        if (!Devmode.DEV_MODE) {
            gearShift = new Solenoid(RobotMap.GEAR_SHIFT);
        }

        sonar = new AnalogChannel(RobotMap.SONAR_CHANNEL);
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
