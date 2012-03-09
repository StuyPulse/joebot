/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.stuy.RobotMap;
import edu.stuy.commands.ConveyorAssistAcquire;
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

    public static final double FORWARD = 1;
    public static final double BACKWARD = -1;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new ConveyorAssistAcquire());
    }
    
    public Conveyor() {
        roller = new Victor(RobotMap.CONVEYOR_ROLLER);
        upperSensor = new DigitalInput(RobotMap.UPPER_CONVEYOR_SENSOR);
        lowerSensor = new DigitalInput(RobotMap.LOWER_CONVEYOR_SENSOR);
    }

    /**
     * Should be set to either FORWARD or BACKWARD or 0
     * @param speed
     */
    private void roll(double speed) {
        roller.set(speed);
    }

    /**
     * Conveys ball upwards towards the top of the conveyor
     */
    public void convey() {
        roll(FORWARD);
    }

    public void conveyReverse() {
        roll(BACKWARD);
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

    public double getRoller() {
        return roller.get();
    }
}
