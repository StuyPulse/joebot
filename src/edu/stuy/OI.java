package edu.stuy;

import edu.stuy.commands.DrivetrainSetGear;
import edu.stuy.commands.ShooterShoot;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
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
    
    private DriverStationEnhancedIO enhancedIO;
    
    // EnhancedIO digital I/O
    private final int BIT_1_CHANNEL = 1;
    private final int BIT_2_CHANNEL = 2;
    private final int BIT_3_CHANNEL = 3;
    
    public OI() {
        leftStick = new Joystick(RobotMap.LEFT_JOYSTICK_PORT);
        rightStick = new Joystick(RobotMap.RIGHT_JOYSTICK_PORT);
        
        try {
            enhancedIO.setDigitalConfig(BIT_1_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
            enhancedIO.setDigitalConfig(BIT_2_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
            enhancedIO.setDigitalConfig(BIT_3_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
        } catch (EnhancedIOException e) {
        }

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

    /**
     * Use a thumb wheel switch to set the autonomous mode setting.
     * @return Autonomous setting to run.
     */
    public int getAutonSetting() {
        try {
            int switchNum = 0;
            int[] binaryValue = new int[3];

            boolean[] dIO = {!enhancedIO.getDigital(BIT_1_CHANNEL), !enhancedIO.getDigital(BIT_2_CHANNEL), !enhancedIO.getDigital(BIT_3_CHANNEL)};

            for (int i = 0; i < 3; i++) {
                if (dIO[i]) {
                    binaryValue[i] = 1;
                }
                else {
                    binaryValue[i] = 0;
                }
            }

            binaryValue[0] *= 4; // convert all binaryValues to decimal values
            binaryValue[1] *= 2;

            for (int i = 0; i < 3; i++) { // finish binary -> decimal conversion
                switchNum += binaryValue[i];
            }

            return switchNum;
        }
        catch (EnhancedIOException e) {
            return -1; // Do nothing in case of failure
        }
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

