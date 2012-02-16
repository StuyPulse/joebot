/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.stuy.RobotMap;
import edu.stuy.commands.TusksRetract;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Kevin Wang
 */
public class Tusks extends Subsystem {
    Solenoid solenoidExtend;
    Solenoid solenoidRetract;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public Tusks() {
        solenoidExtend = new Solenoid(RobotMap.TUSKS_SOLENOID_EXTEND);
        solenoidRetract = new Solenoid(RobotMap.TUSKS_SOLENOID_RETRACT);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void extend() {
        solenoidExtend.set(true);
        solenoidRetract.set(false);
    }
    
    public void retract() {
        solenoidRetract.set(true); 
        solenoidExtend.set(false);
    }

    public boolean isExtended() {
        return solenoidExtend.get();
    }
}
