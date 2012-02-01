/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.stuy.RobotMap;
import edu.stuy.speed.JaguarSpeed;
import edu.stuy.speed.JoeSpeed;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Kevin Wang
 */
public class Shooter extends Subsystem {
    public static JoeSpeed upperRoller;
    public static JoeSpeed lowerRoller;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public Shooter() {
        upperRoller = new JaguarSpeed(RobotMap.SHOOTER_UPPER_ROLLER);
        lowerRoller = new JaguarSpeed(RobotMap.SHOOTER_LOWER_ROLLER);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public void rollRollers(double upperRPM, double lowerRPM) {
        upperRoller.setRPM(upperRPM);
        lowerRoller.setRPM(lowerRPM);
    }
    
    public boolean isSpeedGood() {
        boolean speedGood = false;
        if(upperRoller.isAtSetPoint() && lowerRoller.isAtSetPoint()){
            speedGood = true;
        }
        return speedGood;
    }
}