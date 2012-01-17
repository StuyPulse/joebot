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
    JoeSpeed upperRoller;
    JoeSpeed lowerRoller;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public Shooter() {
        upperRoller = new JaguarSpeed(RobotMap.UPPER_SHOOTER_ROLLER);
        lowerRoller = new JaguarSpeed(RobotMap.LOWER_SHOOTER_ROLLER);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public void rollRollers(double upperRPM, double lowerRPM) {
        upperRoller.setRPM(upperRPM);
        lowerRoller.setRPM(lowerRPM);
    }
}
