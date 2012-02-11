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

    public int getExtendedState() {
        return 0;
    }

    public int getRetractedState() {
        return 0;
    }

    /*
     *  Extended | Retracted | returned value
     *     0     |     0     |       0
     *     1     |     0     |       1
     *     0     |     1     |      -1
     * The following function is just magical math based on the above table.
     */
    public int getTuskState() {
        return 0;
    }
}
