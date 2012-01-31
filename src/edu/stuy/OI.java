package edu.stuy;

import edu.stuy.commands.DrivetrainSetGear;
import edu.stuy.commands.ShooterShoot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;

public class OI {
    private Joystick leftStick;
    private Joystick rightStick;

    public static final int DISTANCE_BUTTON_AUTO = 0;
    public static final int DISTANCE_BUTTON_FAR = 1;
    public static final int DISTANCE_BUTTON_FENDER_WIDE = 2;
    public static final int DISTANCE_BUTTON_FENDER_NARROW = 3;
    public static final int DISTANCE_BUTTON_FENDER_SIDE = 4;
    public static final int DISTANCE_BUTTON_FENDER = 5;
    
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

    public boolean getShootButton() {
        return false;
    }

    public boolean getShootOverrideButton() {
        return false;
    }

    public int getPressedDistanceButton() {
        return 0;
    }

    public int getAutonSetting() {
        return 0;
    }

    public boolean getAcquirerInSwitch() {
        return false;
    }

    public boolean getAcquirerOutSwitch() {
        return false;
    }

    public boolean getManualConveyInSwitch() {
        return false;
    }

    public boolean getManualConveyOutSwitch() {
        return false;
    }

    public double getSpeedPot() {
        return 0.0;
    }

    public double getSpinPot() {
        return 0.0;
    }
}

