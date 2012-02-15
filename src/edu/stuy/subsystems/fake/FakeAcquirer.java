/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems.fake;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Kevin Wang
 */
public class FakeAcquirer extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public FakeAcquirer() {
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void roll(double upperSpeed) {
        System.out.println("Acquirer roll");
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

    public double getRoller() {
        return 0.0;
    }
}
