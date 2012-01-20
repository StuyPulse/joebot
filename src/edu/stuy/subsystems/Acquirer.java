/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.stuy.RobotMap;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Kevin Wang
 */
public class Acquirer extends Subsystem {
    Victor upperRoller;
    Victor lowerRoller;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public Acquirer() {
        upperRoller = new Victor(RobotMap.ACQUIRER_UPPER_ROLLER);
        lowerRoller = new Victor(RobotMap.ACQUIRER_LOWER_ROLLER);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void roll(double upperSpeed, double lowerSpeed) {
        upperRoller.set(upperSpeed);
        lowerRoller.set(lowerSpeed);
    }
}
