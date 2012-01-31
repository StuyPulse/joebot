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
    Solenoid solenoid;
    DigitalInput extendedSwitch;
    DigitalInput retractedSwitch;

    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public Tusks() {
        solenoid = new Solenoid(RobotMap.TUSKS_SOLENOID);
        extendedSwitch = new DigitalInput(2, RobotMap.TUSKS_EXTENDED_SWITCH);
        retractedSwitch = new DigitalInput(2, RobotMap.TUSKS_RETRACTED_SWITCH);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void extend() {
        solenoid.set(true);
    }
    
    public void retract() {
        solenoid.set(false);    }

    public int getExtendedState() {
        return extendedSwitch.get() ? 1 : 0;
    }

    public int getRetractedState() {
        return retractedSwitch.get() ? 1 : 0;
    }

    /*
     *  Extended | Retracted | returned value
     *     0     |     0     |       0
     *     1     |     0     |       1
     *     0     |     1     |      -1
     * The following function is just magical math based on the above table.
     */
    public int getTuskState() {
        return getExtendedState() - getRetractedState();
    }
}
