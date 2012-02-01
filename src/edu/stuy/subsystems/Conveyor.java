/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.stuy.RobotMap;
import edu.stuy.speed.VictorSpeed;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Danny
 */
public class Conveyor extends Subsystem {
    private Victor upperRoller;
    private Victor lowerRoller;
    private DigitalInput upperSensor;
    private DigitalInput lowerSensor;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public Conveyor() {
        upperRoller = new Victor(RobotMap.CONVEYOR_UPPER_ROLLER);
        lowerRoller = new Victor(RobotMap.CONVEYOR_LOWER_ROLLER);
        upperSensor = new DigitalInput(RobotMap.UPPER_CONVEYOR_SENSOR);
        lowerSensor = new DigitalInput(RobotMap.LOWER_CONVEYOR_SENSOR);
    }
    
    public void roll(double upperSpeed, double lowerSpeed) {
        upperRoller.set(upperSpeed);
        lowerRoller.set(lowerSpeed);
    }

    public void convey() {
        roll(1, 1);
    }

    public void conveyReverse() {
        roll(-1, -1);
    }

    public void stop() {
        roll(0, 0);
    }

    public boolean ballAtTop() {
        return upperSensor.get();
    }

    public boolean ballAtBottom() {
        return lowerSensor.get();
    }

    public double getUpperRoller() {
        return upperRoller.get();
    }

    public double getLowerRoller() {
        return lowerRoller.get();
    }
}
