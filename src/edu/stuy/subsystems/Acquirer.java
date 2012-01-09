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
    Victor roller;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public Acquirer() {
        roller = new Victor(RobotMap.ACQUIRER_ROLLER);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void rollRoller(double rollerSpeed) {
        roller.set(rollerSpeed);
    }
}
