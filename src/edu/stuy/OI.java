
package edu.stuy;

import edu.stuy.commands.DrivetrainSetGear;
import edu.stuy.commands.ShooterShoot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
    private Joystick leftStick;
    private Joystick rightStick;
    
    // Process operator interface input here.
    
    public OI() {
        leftStick = new Joystick(RobotMap.LEFT_JOYSTICK_PORT);
        rightStick = new Joystick(RobotMap.RIGHT_JOYSTICK_PORT);

        if (!Devmode.DEV_MODE) {
            new JoystickButton(leftStick, 1).whileHeld(new ShooterShoot());
        
            new JoystickButton(leftStick, 2).whenPressed(new DrivetrainSetGear(false));
            new JoystickButton(rightStick, 2).whenPressed(new DrivetrainSetGear(true));
        }
    }
    
    public Joystick getLeftStick() {
        return leftStick;
    }
    
    public Joystick getRightStick() {
        return rightStick;
    }

    public boolean isConveyorStopButtonPressed() {
        return false;
    }
}

