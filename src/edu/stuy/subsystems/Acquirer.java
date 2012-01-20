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
    Victor lowerRoller;
    Victor upperRoller;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public Acquirer() {
        lowerRoller = new Victor(RobotMap.ACQUIRER_ROLLER_LOWER);
        upperRoller = new Victor(RobotMap.ACQUIRER_ROLLER_UPPER);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void roll(double lowerSpeed, double upperSpeed) {
        lowerRoller.set(lowerSpeed);
        upperRoller.set(upperSpeed);
    }
}
