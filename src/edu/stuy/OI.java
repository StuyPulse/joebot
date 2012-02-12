package edu.stuy;

import edu.stuy.commands.*;
import edu.stuy.commands.tuning.ShooterManualSpeed;
import edu.stuy.subsystems.Shooter;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.DigitalIOButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
    private Joystick leftStick;
    private Joystick rightStick;
    private Joystick shooterStick;
    private Joystick debugBox;

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
    
    public double distanceInches;
    
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

        shooterStick = new Joystick(RobotMap.SHOOTER_JOYSTICK_PORT);
        debugBox = new Joystick(RobotMap.DEBUG_BOX_PORT);
        
        try {
            if (!Devmode.DEV_MODE) {
                enhancedIO.setDigitalConfig(BIT_1_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
                enhancedIO.setDigitalConfig(BIT_2_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
                enhancedIO.setDigitalConfig(BIT_3_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
                enhancedIO.setDigitalConfig(ACQUIRER_IN_SWITCH_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
                enhancedIO.setDigitalConfig(ACQUIRER_OUT_SWITCH_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
                enhancedIO.setDigitalConfig(CONVEYOR_IN_SWITCH_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
                enhancedIO.setDigitalConfig(CONVEYOR_OUT_SWITCH_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
                enhancedIO.setDigitalConfig(SHOOT_BUTTON_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
                enhancedIO.setDigitalConfig(OVERRIDE_BUTTON_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);

                enhancedIO.setDigitalConfig(DISTANCE_BUTTON_AUTO_LIGHT_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kOutput);
                enhancedIO.setDigitalConfig(DISTANCE_BUTTON_FAR_LIGHT_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kOutput);
                enhancedIO.setDigitalConfig(DISTANCE_BUTTON_FENDER_WIDE_LIGHT_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kOutput);
                enhancedIO.setDigitalConfig(DISTANCE_BUTTON_FENDER_NARROW_LIGHT_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kOutput);
                enhancedIO.setDigitalConfig(DISTANCE_BUTTON_FENDER_SIDE_LIGHT_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kOutput);
                enhancedIO.setDigitalConfig(DISTANCE_BUTTON_FENDER_LIGHT_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kOutput);
                enhancedIO.setDigitalConfig(DISTANCE_BUTTON_STOP_LIGHT_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kOutput);
            }
        } catch (EnhancedIOException e) {
        }

        // Defaults shooter speed to fender
        double distanceInches = Shooter.distances[Shooter.FENDER_INDEX];


        if (!Devmode.DEV_MODE) {
            new JoystickButton(leftStick, 1).whileHeld(new ShooterMoveFlyWheel(distanceInches));
            new JoystickButton(leftStick, 2).whenPressed(new DrivetrainSetGear(false));
            new JoystickButton(rightStick, 2).whenPressed(new DrivetrainSetGear(true));
            
            // OI box switches
            new DigitalIOButton(ACQUIRER_IN_SWITCH_CHANNEL).whileHeld(new AcquirerAcquire());
            new DigitalIOButton(ACQUIRER_OUT_SWITCH_CHANNEL).whileHeld(new AcquirerReverse());
            new DigitalIOButton(CONVEYOR_IN_SWITCH_CHANNEL).whileHeld(new ConveyManual());
            new DigitalIOButton(CONVEYOR_OUT_SWITCH_CHANNEL).whileHeld(new ConveyReverseManual());
            new DigitalIOButton(SHOOT_BUTTON_CHANNEL).whileHeld(new ConveyAutomatic());
            
            new JoystickButton(shooterStick, 1).whileHeld(new ConveyAutomatic());
            new JoystickButton(shooterStick, 2).whileHeld(new AcquirerAcquire());
            new JoystickButton(shooterStick, 3).whileHeld(new ConveyManual());
            new JoystickButton(shooterStick, 4).whileHeld(new ConveyReverseManual());
            new JoystickButton(shooterStick, 5).whileHeld(new AcquirerReverse());
            new JoystickButton(shooterStick, 6).whenPressed(new ShooterManualSpeed());
        }
    }
    
    public Joystick getLeftStick() {
        return leftStick;
    }
    
    public Joystick getRightStick() {
        return rightStick;
    }
    
    public Joystick getDebugBox() {
        return debugBox;
    }

    public boolean getShootOverrideButton() {
        try {
            return !enhancedIO.getDigital(OVERRIDE_BUTTON_CHANNEL) || shooterStick.getRawButton(8);
        } catch (EnhancedIOException ex) {
            return shooterStick.getRawButton(8);
        }
    }

    public int getPressedDistanceButton() {
       if (shooterStick.getRawButton(6)) {
           return DISTANCE_BUTTON_STOP;
       }
       if (shooterStick.getRawButton(7)) {
           return DISTANCE_BUTTON_AUTO;
       }
       if (shooterStick.getRawButton(10)) {
           return DISTANCE_BUTTON_FENDER;
       }
       if (shooterStick.getRawButton(11)) {
           return DISTANCE_BUTTON_FAR;
       }
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

    public double getSpeedPot() {
        try {
            return enhancedIO.getAnalogIn(SPEED_TRIM_POT_CHANNEL);
        } catch (EnhancedIOException ex) {
            return 0.0;
        }
    }

    public double getSpinPot() {
        try {
            return enhancedIO.getAnalogIn(SPIN_TRIM_POT_CHANNEL);
        } catch (EnhancedIOException ex) {
            return 0.0;
        }
    }
}

