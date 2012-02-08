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
public class Acquirer extends Subsystem {
    private Victor roller;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public Acquirer() {
        roller = new Victor(RobotMap.ACQUIRER_ROLLER);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void roll(double speed) {
        roller.set(speed);
    }

    public void stop() {
        roll(0);
    }

    public void acquire() {
        roll(1);
    }

    public void acquireReverse() {
        roll(-1);
    }
}
