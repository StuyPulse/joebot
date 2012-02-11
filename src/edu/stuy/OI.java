package edu.stuy;

import edu.stuy.commands.*;
import edu.stuy.subsystems.Shooter;
import edu.wpi.first.wpilibj.DriverStation;
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
    
    private static final int ACQUIRER_IN_SWITCH_CHANNEL = 1;
    private static final int ACQUIRER_OUT_SWITCH_CHANNEL = 2;
    private static final int BIT_1_CHANNEL = 3;
    private static final int BIT_2_CHANNEL = 4;
    private static final int BIT_3_CHANNEL = 5;
    private static final int SHOOT_BUTTON_CHANNEL = 6;
    private static final int OVERRIDE_BUTTON_CHANNEL = 7;
    private static final int CONVEYOR_IN_SWITCH_CHANNEL = 8;
    private static final int CONVEYOR_OUT_SWITCH_CHANNEL = 9;
    
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
    private static final int MAX_ANALOG_CHANNEL = 4;
    
    public OI() {
        enhancedIO = DriverStation.getInstance().getEnhancedIO();
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
            new JoystickButton(rightStick, 1).whenPressed(new DrivetrainSetGear(false));
            new JoystickButton(rightStick, 2).whenPressed(new DrivetrainSetGear(true));
            new JoystickButton(leftStick, 1).whenPressed(new TusksExtend());
            new JoystickButton(leftStick, 2).whenPressed(new TusksRetract());
            
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
        }
    }
    
    // Copied from last year's DesDroid code. 
    
    public double getRawAnalogVoltage() {
        try {
            return enhancedIO.getAnalogIn(DISTANCE_BUTTONS_CHANNEL);
        }
        catch (EnhancedIOException e) {
            return 0;
        }
    }
    
    public double getMaxVoltage() {
        try {
            return enhancedIO.getAnalogIn(MAX_ANALOG_CHANNEL);
        }
        catch (EnhancedIOException e) {
            return 2.2;
        }
    }
    
    /**
     * Determines which height button is pressed. All (7 logically because
     * side buttons are wired together) buttons are wired by means of
     * resistors to one analog input. Depending on the button that is pressed, a
     * different voltage is read by the analog input. Each resistor reduces the
     * voltage by about 1/7 the maximum voltage.
     *
     * @return An integer value representing the height button that was pressed.
     */
    public int getDistanceButton() {
        return (int) ((getRawAnalogVoltage() / (getMaxVoltage() / 7)) + 0.5);
    }
    
    public double getDistanceFromHeightButton(){
        double distance = 0;
        switch(getDistanceButton()){
            // Automatic
            case 1:
                distance = CommandBase.drivetrain.getSonarDistance_in();
                break;
            // Full Throttle
            case 2:
                distance = 725; // TODO: Max distance to max speed?
                break;
            // Fender + Long
            case 3:
                distance = Shooter.distances[Shooter.FENDER_LONG_INDEX];
                break;
            // Fender + Wide
            case 4:
                distance = Shooter.distances[Shooter.FENDER_WIDE_INDEX];
                break;
            // Fender
            case 5:
                distance = Shooter.distances[Shooter.FENDER_INDEX];
                break;
            // Side Fender
            case 6:
                distance = Shooter.distances[Shooter.FENDER_SIDE_INDEX];
                break;
            // Stop Flywheels
            default:
                distance = 0;
                break;
        }
        return distance;
    }
    
    // Copied from last year's DesDroid code. 
    
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
    
    public int getDebugBoxBinaryAutonSetting() {
        int switchNum = 0;
        int[] binaryValue = new int[4];

        boolean[] dIO = {debugBox.getRawButton(1), debugBox.getRawButton(2), debugBox.getRawButton(3), debugBox.getRawButton(4)};

        for (int i = 0; i < 4; i++) {
            if (dIO[i]) {
                binaryValue[i] = 1;
            }
            else {
                binaryValue[i] = 0;
            }
        }

        binaryValue[0] *= 8; // convert all binaryValues to decimal values
        binaryValue[1] *= 4;
        binaryValue[2] *= 2;

        for (int i = 0; i < 4; i++) { // finish binary -> decimal conversion
            switchNum += binaryValue[i];
        }

        return switchNum;
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

