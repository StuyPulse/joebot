/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.stuy.RobotMap;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Kevin Wang
 */
public class Tusks extends Subsystem {
    Solenoid solenoidExtend;
    Solenoid solenoidRetract;
    DigitalInput extendedSwitch;
    DigitalInput retractedSwitch;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public Tusks() {
        solenoidExtend = new Solenoid(RobotMap.TUSKS_SOLENOID_EXTEND);
        solenoidRetract = new Solenoid(RobotMap.TUSKS_SOLENOID_RETRACT);
        extendedSwitch = new DigitalInput(2, RobotMap.TUSKS_EXTENDED_SWITCH);
        retractedSwitch = new DigitalInput(2, RobotMap.TUSKS_RETRACTED_SWITCH);
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
