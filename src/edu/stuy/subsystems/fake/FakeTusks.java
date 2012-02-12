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
public class FakeTusks extends Subsystem {

  
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public FakeTusks() {
    }

    public void initDefaultCommand() {
    }

    public void extend() {
        System.out.println("tuskExtend");
    }

    public void retract() {
        System.out.println("Tusks retract");
    }

    public boolean isExtended() {
        return false;
    }
}
