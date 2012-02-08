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
    public Victor roller;
    public DigitalInput upperSensor;
    public DigitalInput lowerSensor;
    public DigitalInput middleSensor;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public Conveyor() {
        roller = new Victor(RobotMap.CONVEYOR_ROLLER);
        upperSensor = new DigitalInput(RobotMap.UPPER_CONVEYOR_SENSOR);
        lowerSensor = new DigitalInput(RobotMap.LOWER_CONVEYOR_SENSOR);
        middleSensor = new DigitalInput(RobotMap.MIDDLE_CONVEYOR_SENSOR);
    }
    
    public void roll(double speed) {
        roller.set(speed);
    }

    public void convey() {
        roll(1);
    }

    public void conveyReverse() {
        roll(-1);
    }

    public void stop() {
        roll(0);
    }

    public boolean ballAtTop() {
        return upperSensor.get();
    }

    public boolean ballAtBottom() {
        return lowerSensor.get();
    }

    public boolean ballAtMiddle(){
        return middleSensor.get();
    }

    public double getRoller() {
        return roller.get();
    }
}
