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
 * @author Kevin Wang
 */
public class Shooter extends Subsystem {
    private Victor upperRoller;
    private Victor lowerRoller;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public Shooter() {
        upperRoller = new Victor(RobotMap.UPPER_SHOOTER_ROLLER);
        lowerRoller = new Victor(RobotMap.LOWER_SHOOTER_ROLLER);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void rollRollers(double upperSpeed, double lowerSpeed) {
        upperRoller.set(upperSpeed);
        lowerRoller.set(lowerSpeed);
    }
}
