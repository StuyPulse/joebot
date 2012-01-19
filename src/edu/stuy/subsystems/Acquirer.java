/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.stuy.RobotMap;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Kevin Wang
 */
public class Acquirer extends Subsystem {
    Victor intakeRollerA;
    Victor intakeRollerB;
    Victor hopperRoller;
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public Acquirer() {
        intakeRollerA = new Victor(RobotMap.ACQUIRER_INTAKE_ROLLER_A);
        intakeRollerB = new Victor(RobotMap.ACQUIRER_INTAKE_ROLLER_B);
        hopperRoller = new Victor(RobotMap.ACQUIRER_HOPPER_ROLLER);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void intake(double rollerSpeedA, double rollerSpeedB) {
        intakeRollerA.set(rollerSpeedA);
        intakeRollerB.set(rollerSpeedB);
    }

    public void rollHopperRoller(double speed) {
        hopperRoller.set(speed);
    }
}
