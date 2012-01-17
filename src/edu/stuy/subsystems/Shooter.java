/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.stuy.RobotMap;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Kevin Wang
 */
public class Shooter extends Subsystem {
    private static final double KP = 0.0;
    private static final double KI = 0.0;
    private static final double KD = 0.0;
    
    private Jaguar upperRoller;
    private Jaguar lowerRoller;
    
    private Encoder upperEncoder;
    private Encoder lowerEncoder;
    
    private PIDController upperController;
    private PIDController lowerController;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public Shooter() {
        upperRoller = new Jaguar(RobotMap.UPPER_SHOOTER_ROLLER);
        lowerRoller = new Jaguar(RobotMap.LOWER_SHOOTER_ROLLER);
        
        //upperEncoder = new Encoder(RobotMap.UPPER_ROLLER_ENCODER_A, RobotMap.UPPER_ROLLER_ENCODER_B, false, CounterBase.EncodingType.k); //TODO Fix this!
        lowerEncoder = new Encoder(RobotMap.LOWER_ROLLER_ENCODER_A, RobotMap.LOWER_ROLLER_ENCODER_B);
        
        upperController = new PIDController(KP, KI, KD, upperEncoder, upperRoller);
        lowerController = new PIDController(KP, KI, KD, lowerEncoder, upperRoller);
        
        upperEncoder.setDistancePerPulse(RobotMap.DISTANCE_PER_PULSE);
        lowerEncoder.setDistancePerPulse(RobotMap.DISTANCE_PER_PULSE);
        
        upperEncoder.start();
        lowerEncoder.start();
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void rollRollers(double upperSpeed, double lowerSpeed) {
        upperRoller.set(upperSpeed);
        lowerRoller.set(lowerSpeed);
    }
    
    public double getUpperEnc(){
        return upperEncoder.getDistance();
    }
    
    public double getLowerEnc(){
        return lowerEncoder.getDistance();
    }
    
    public void resetEncoders(){
        upperEncoder.reset();
        lowerEncoder.reset();
    }
}
