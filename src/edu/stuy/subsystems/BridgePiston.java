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
public class BridgePiston extends Subsystem {
    Solenoid PistonExtend;
    Solenoid PistonRetract;
    
    public BridgePiston() {
        PistonExtend = new Solenoid(2, RobotMap.BRIDGE_BALANCING_EXTEND);
        PistonRetract = new Solenoid(2, RobotMap.BRIDGE_BALANCING_RETRACT);
    }
    
    public void initDefaultCommand() {
        
    }
    
    public void extend() {
        PistonExtend.set(true);
        PistonRetract.set(false);
    }
    
    public void retract() {
        PistonExtend.set(false);
        PistonRetract.set(true);
    }
    
    public boolean isExtended() {
        return PistonExtend.get();
    }
}
