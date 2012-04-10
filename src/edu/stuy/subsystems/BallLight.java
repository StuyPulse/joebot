/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.stuy.RobotMap;
import edu.stuy.commands.BallLightUpdate;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Light that turns on when the conveyor upper sensor is triggered.
 * @author admin
 */
public class BallLight extends Subsystem {
    private Relay ballLight;
    private boolean isOn = false;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public BallLight() {
        ballLight = new Relay(RobotMap.BALL_LIGHT, Relay.Direction.kForward);
    }
    
    public void setLight(boolean on) {
        // Sets the light and the light state variable
        ballLight.set((isOn = on) ? Relay.Value.kOn : Relay.Value.kOff);
    }

    public boolean isOn() {
        return isOn;
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new BallLightUpdate());
    }
}
