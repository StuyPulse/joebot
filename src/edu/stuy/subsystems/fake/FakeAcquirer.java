/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems.fake;

import edu.stuy.subsystems.*;
import edu.stuy.RobotMap;
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
    
    public void roll(double upperSpeed, double lowerSpeed) {
        System.out.println("Acquirer roll");
    }
}
