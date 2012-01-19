/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.buttons;

import edu.wpi.first.wpilibj.Joystick;

/**
 *
 * @author bradmiller
 */
public class JoystickButton extends Button {
    
    Joystick m_joystick;
    int m_buttonNumber;

    /**
     * Create a joystick button for triggering commands
     * @param joystick The joystick object that has the button
     * @param buttonNumber The button number (see {@link Joystick#getRawButton(int) }
     */
    public JoystickButton(Joystick joystick, int buttonNumber) {
        m_joystick = joystick;
        m_buttonNumber = buttonNumber;
    }
    
    /**
     * Gets the value of the joystick button
     * @return The value of the joystick button
     */
    public boolean get() {
        return m_joystick.getRawButton(m_buttonNumber);
    }
}
