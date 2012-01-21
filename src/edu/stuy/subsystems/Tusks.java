/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.stuy.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Kevin Wang
 */
public class Tusks extends Subsystem {
    Solenoid solenoid;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public Tusks() {
        solenoid = new Solenoid(RobotMap.TUSKS_SOLENOID);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void extend() {
        solenoid.set(true);
    }
    
    public void retract() {
        solenoid.set(false);
    }
}
