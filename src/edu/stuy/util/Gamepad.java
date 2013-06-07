/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.util;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Class for both the Logitech Dual Action 2 Gamepad and the Logitech Gamepad
 * F310. The Logitech Gamepad F310 must have the switch on the back set to "D"
 * for this class to work. This class probably also works with the Logitech
 * Wireless Gamepad F710 (untested, but it has the exact same layout as the
 * F310).
 * @author kevin
 */
public class Gamepad extends Joystick {
    public Gamepad(int port) {
        super(port);
    }
    
    /**
     * The left analog stick x-axis.
     * @return value of left analog x-axis
     */
    public double getLeftX() {
        return getRawAxis(1);
    }
    
    /**
     * The left analog stick y-axis.
     * @return value of left analog y-axis
     */
    public double getLeftY() {
        return getRawAxis(2);
    }
    
    /**
     * The right analog stick x-axis.
     * @return value of right analog x-axis
     */
    public double getRightX() {
        return getRawAxis(3);
    }
    
    /**
     * The right analog stick y-axis.
     * @return value of right analog y-axis
     */
    public double getRightY() {
        return getRawAxis(4);
    }
    
    /**
     * The directional pad of the gamepad.
     * @return value of the left/right d-pad buttons
     */
    public double getDPadX() {
        return getRawAxis(5);
    }
    
    /**
     * The direction pad of the gamepad.
     * @return value of the up/down d-pad buttons
     */
    public double getDPadY() {
        return getRawAxis(6);
    }
    
    /**
     * The upper d-pad button.
     * @return if upper d-pad button is pressed
     */
    public boolean getDPadUp() {
        return getDPadY() < -.5;
    }
    
    /**
     * The lower d-pad button.
     * @return if the lower d-pad button is pressed
     */
    public boolean getDPadDown() {
        return getDPadY() > .5;
    }
    
    /**
     * The left d-pad button.
     * @return if the left d-pad button is pressed
     */
    public boolean getDPadLeft() {
        return getDPadX() < -.5;
    }
    
    /**
     * The right d-pad button.
     * @return if the right d-pad button is pressed
     */
    public boolean getDPadRight() {
        return getDPadX() > .5;
    }
    
    /**
     * The left bumper.
     * @return if the left bumper is pressed
     */
    public boolean getLeftBumper() {
        return getRawButton(5);
    }
    
    /**
     * The right bumper.
     * @return if the right bumper is pressed
     */
    public boolean getRightBumper() {
        return getRawButton(6);
    }
    
    /**
     * The left trigger.
     * @return if the left trigger is pressed
     */
    public boolean getLeftTrigger() {
        return getRawButton(7);
    }
    
    /**
     * The right trigger.
     * @return if the right trigger is pressed
     */
    public boolean getRightTrigger() {
        return getRawButton(8);
    }

    /**
     * The left button of the button group. On some gamepads this is X.
     * @return if the left button is pressed
     */
    public boolean getLeftButton() {
        return getRawButton(1);
    }

    /**
     * The left button of the button group. On some gamepads this is A.
     * @return if the bottom button is pressed
     */
    public boolean getBottomButton() {
        return getRawButton(2);
    }

    /**
     * The left button of the button group. On some gamepads this is B.
     * @return if the right button is pressed
     */
    public boolean getRightButton() {
        return getRawButton(3);
    }

    /**
     * The left button of the button group. On some gamepads this is Y.
     * @return if the top button is pressed
     */
    public boolean getTopButton() {
        return getRawButton(4);
    }

    /**
     * The central left button. On some gamepads this is the select button.
     * @return if the back button is pressed
     */
    public boolean getBackButton() {
        return getRawButton(9);
    }

    /**
     * The central right button. On some gamepads this is the start button.
     * @return if the start button is pressed
     */
    public boolean getStartButton() {
        return getRawButton(10);
    }

    /**
     * The click-function of the left analog stick.
     * @return if the left analog stick is being clicked down
     */
    public boolean getLeftAnalogButton() {
        return getRawButton(11);
    }

    /**
     * The click-function of the right analog stick.
     * @return if the right analog stick is being clicked down
     */
    public boolean getRightAnalogButton() {
        return getRawButton(12);
    }
}
