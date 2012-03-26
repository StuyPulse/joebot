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
 * @author admin
 */
public class Stinger extends Subsystem {
    Solenoid solenoidExtend;
    Solenoid solenoidRetract;
    
    public Stinger() {
        solenoidExtend = new Solenoid(2, RobotMap.STINGER_SOLENOID_EXTEND);
        solenoidRetract = new Solenoid(2, RobotMap.STINGER_SOLENOID_RETRACT);
    }
    
    public void initDefaultCommand() {
        
    }
    
    public void extend() {
        solenoidExtend.set(true);
        solenoidRetract.set(false);
    }
    
    public void retract() {
        solenoidExtend.set(false);
        solenoidRetract.set(true);
    }
    
    public boolean isExtended() {
        return solenoidExtend.get();
    }
}
