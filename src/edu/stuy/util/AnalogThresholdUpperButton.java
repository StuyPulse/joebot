/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.util;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

/**
 *
 * @author Eric
 */
public class AnalogThresholdUpperButton extends Button {
    
    public static final double ANALOG_THRESHOLD = 0.8;
    
    private Joystick pad;
    private int axisChannel;
    
    public AnalogThresholdUpperButton(Joystick gamepad, int axis) {
        pad = gamepad;
        axisChannel = axis;
    }
    
    public boolean get() {
        return pad.getRawAxis(axisChannel) > ANALOG_THRESHOLD;
    }
    
}
