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
<<<<<<< HEAD
    Solenoid solenoidExtend;
    Solenoid solenoidRetract;
    DigitalInput extendedSwitch;
    DigitalInput retractedSwitch;
=======
    Solenoid solenoid;
>>>>>>> 9debb1385c73d2155e536f8d7c8777357c8c4487
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public Tusks() {
<<<<<<< HEAD
        solenoidExtend = new Solenoid(RobotMap.TUSKS_SOLENOID_EXTEND);
        solenoidRetract = new Solenoid(RobotMap.TUSKS_SOLENOID_RETRACT);
        extendedSwitch = new DigitalInput(2, RobotMap.TUSKS_EXTENDED_SWITCH);
        retractedSwitch = new DigitalInput(2, RobotMap.TUSKS_RETRACTED_SWITCH);
=======
        solenoid = new Solenoid(RobotMap.TUSKS_SOLENOID);
>>>>>>> 9debb1385c73d2155e536f8d7c8777357c8c4487
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
<<<<<<< HEAD
        solenoidRetract.set(true); 
        solenoidExtend.set(false);
    }

    public int getExtendedState() {
        return extendedSwitch.get() ? 1 : 0;
    }

    public int getRetractedState() {
        return retractedSwitch.get() ? 1 : 0;
=======
        solenoid.set(false);
>>>>>>> 9debb1385c73d2155e536f8d7c8777357c8c4487
    }

    public boolean isExtended() {
        return solenoid.get();
    }
}
