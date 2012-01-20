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
    Solenoid solenoidA;
    Solenoid solenoidB;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public Tusks() {
        solenoidA = new Solenoid(RobotMap.TUSKS_SOLENOID_A);
        solenoidB = new Solenoid(RobotMap.TUSKS_SOLENOID_B);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void extend() {
        solenoidA.set(true);
        solenoidB.set(true);
    }
    
    public void retract() {
        solenoidA.set(false);
        solenoidB.set(false);
    }
}
