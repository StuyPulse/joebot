package edu.stuy;

import edu.stuy.commands.AcquirerAcquire;
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
    public static final int DISTANCE_BUTTON_STOP = 6;
    
    private DriverStationEnhancedIO enhancedIO;
    
    // EnhancedIO digital input
    private static final int BIT_1_CHANNEL = 1;
    private static final int BIT_2_CHANNEL = 2;
    private static final int BIT_3_CHANNEL = 3;
    private static final int ACQUIRER_IN_SWITCH_CHANNEL = 4;
    private static final int ACQUIRER_OUT_SWITCH_CHANNEL = 5;
    private static final int CONVEYOR_IN_SWITCH_CHANNEL = 6;
    private static final int CONVEYOR_OUT_SWITCH_CHANNEL = 7;
    private static final int SHOOT_BUTTON_CHANNEL = 8;
    private static final int OVERRIDE_BUTTON_CHANNEL = 9;
    
    // EnhancedIO digital output
    private static final int DISTANCE_BUTTON_AUTO_LIGHT_CHANNEL = 10;
    private static final int DISTANCE_BUTTON_FAR_LIGHT_CHANNEL = 11;
    private static final int DISTANCE_BUTTON_FENDER_WIDE_LIGHT_CHANNEL = 12;
    private static final int DISTANCE_BUTTON_FENDER_NARROW_LIGHT_CHANNEL = 13;
    private static final int DISTANCE_BUTTON_FENDER_SIDE_LIGHT_CHANNEL = 14;
    private static final int DISTANCE_BUTTON_FENDER_LIGHT_CHANNEL = 15;
    private static final int DISTANCE_BUTTON_STOP_LIGHT_CHANNEL = 16;
    
    // EnhancedIO analog input
    private static final int DISTANCE_BUTTONS_CHANNEL = 1;
    private static final int SPEED_TRIM_POT_CHANNEL = 2;
    private static final int SPIN_TRIM_POT_CHANNEL = 3;
    
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
            
            new DigitalIOButton(ACQUIRER_IN_SWITCH_CHANNEL).whileHeld(new AcquirerAcquire());
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

