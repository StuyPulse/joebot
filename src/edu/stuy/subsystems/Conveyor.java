/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.stuy.RobotMap;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Danny
 */
public class Conveyor extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Victor upperRoller;
    Victor lowerRoller;
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public Conveyor() {
        upperRoller = new Victor(RobotMap.CONVEYOR_UPPER_ROLLER);
        lowerRoller = new Victor(RobotMap.CONVEYOR_LOWER_ROLLER);
        
    }
    
    public void roll(double upperSpeed, double lowerSpeed) {
        upperRoller.set(upperSpeed);
        lowerRoller.set(lowerSpeed);
    }
}
