package edu.stuy;

import edu.stuy.commands.*;
import edu.stuy.subsystems.Flywheel;
import edu.stuy.util.InverseDigitalIOButton;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.EnhancedIOException;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class OI {
    private Joystick leftStick;
    private Joystick rightStick;
    private Joystick shooterStick;
    private Joystick debugBox;
    
    public static final int DISTANCE_BUTTON_KEY = 7;
    public static final int DISTANCE_BUTTON_FAR = 6;
    public static final int DISTANCE_BUTTON_FENDER_LENGTH = 5;
    public static final int DISTANCE_BUTTON_FENDER_WIDTH = 3;
    public static final int DISTANCE_BUTTON_REVERSE = 4;
    public static final int DISTANCE_BUTTON_FENDER = 2;
    public static final int DISTANCE_BUTTON_STOP = 1;
    
    private DriverStationEnhancedIO enhancedIO;
    
    // EnhancedIO digital input
    
    public static final int CONVEYOR_DOWN_SWITCH_CHANNEL = 1;
    public static final int CONVEYOR_UP_SWITCH_CHANNEL = 2;
    public static final int BIT_1_CHANNEL = 5;
    public static final int BIT_2_CHANNEL = 4;
    public static final int BIT_3_CHANNEL = 3;
    public static final int SHOOTER_BUTTON_CHANNEL = 7;
    public static final int STINGER_SWITCH_CHANNEL = 6;
    public static final int ACQUIRER_IN_SWITCH_CHANNEL = 9;
    public static final int ACQUIRER_OUT_SWITCH_CHANNEL = 8;
    
    public int distanceButton;
    public double distanceInches;
    public boolean topHoop = true;
    
    // EnhancedIO digital output
    private static final int DISTANCE_BUTTON_KEY_LIGHT_CHANNEL = 10;
    private static final int DISTANCE_BUTTON_FAR_LIGHT_CHANNEL = 11;
    private static final int DISTANCE_BUTTON_FENDER_WIDE_LIGHT_CHANNEL = 12;
    private static final int DISTANCE_BUTTON_FENDER_NARROW_LIGHT_CHANNEL = 13;
    private static final int DISTANCE_BUTTON_REVERSE_LIGHT_CHANNEL = 15;
    private static final int DISTANCE_BUTTON_FENDER_LIGHT_CHANNEL = 14;
    private static final int DISTANCE_BUTTON_STOP_LIGHT_CHANNEL = 16;
    
    // EnhancedIO analog input
    public static final int DISTANCE_BUTTONS_CHANNEL = 1;
    public static final int SPEED_TRIM_POT_CHANNEL = 2;
    public static final int DELAY_POT_CHANNEL = 3;
    public static final int MAX_ANALOG_CHANNEL = 4;

    public static final int MAX_WAIT_TIME = 10;
    
    public OI() {
        enhancedIO = DriverStation.getInstance().getEnhancedIO();
        leftStick = new Joystick(RobotMap.LEFT_JOYSTICK_PORT);
        rightStick = new Joystick(RobotMap.RIGHT_JOYSTICK_PORT);

        shooterStick = new Joystick(RobotMap.SHOOTER_JOYSTICK_PORT);
        debugBox = new Joystick(RobotMap.DEBUG_BOX_PORT);
        
        distanceButton = DISTANCE_BUTTON_STOP;
        distanceInches = Flywheel.distances[Flywheel.STOP_INDEX];
        
        try {
            if (!Devmode.DEV_MODE) {
                enhancedIO.setDigitalConfig(BIT_1_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
                enhancedIO.setDigitalConfig(BIT_2_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
                enhancedIO.setDigitalConfig(BIT_3_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
                enhancedIO.setDigitalConfig(ACQUIRER_IN_SWITCH_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
                enhancedIO.setDigitalConfig(ACQUIRER_OUT_SWITCH_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
                enhancedIO.setDigitalConfig(CONVEYOR_UP_SWITCH_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
                enhancedIO.setDigitalConfig(CONVEYOR_DOWN_SWITCH_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
                enhancedIO.setDigitalConfig(SHOOTER_BUTTON_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);
                enhancedIO.setDigitalConfig(STINGER_SWITCH_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kInputPullUp);

                enhancedIO.setDigitalConfig(DISTANCE_BUTTON_KEY_LIGHT_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kOutput);
                enhancedIO.setDigitalConfig(DISTANCE_BUTTON_FAR_LIGHT_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kOutput);
                enhancedIO.setDigitalConfig(DISTANCE_BUTTON_FENDER_WIDE_LIGHT_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kOutput);
                enhancedIO.setDigitalConfig(DISTANCE_BUTTON_FENDER_NARROW_LIGHT_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kOutput);
                enhancedIO.setDigitalConfig(DISTANCE_BUTTON_REVERSE_LIGHT_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kOutput);
                enhancedIO.setDigitalConfig(DISTANCE_BUTTON_FENDER_LIGHT_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kOutput);
                enhancedIO.setDigitalConfig(DISTANCE_BUTTON_STOP_LIGHT_CHANNEL, DriverStationEnhancedIO.tDigitalConfig.kOutput);
            }
        } catch (EnhancedIOException e) {
        }

        if (!Devmode.DEV_MODE) {
            new JoystickButton(leftStick, 1).whenPressed(new DrivetrainSetGear(false));
            new JoystickButton(leftStick, 2).whenPressed(new DrivetrainSetGear(true));
            new JoystickButton(rightStick, 1).whenPressed(new TusksExtend());
            new JoystickButton(rightStick, 2).whenPressed(new TusksRetract());
            new JoystickButton(rightStick, 4).whenPressed(new MoveCamera(true));
            new JoystickButton(rightStick, 5).whenPressed(new MoveCamera(false));
            
            // OI box switches
            new InverseDigitalIOButton(ACQUIRER_IN_SWITCH_CHANNEL).whileHeld(new AcquirerAcquire());
            new InverseDigitalIOButton(ACQUIRER_OUT_SWITCH_CHANNEL).whileHeld(new AcquirerReverse());
            new InverseDigitalIOButton(CONVEYOR_UP_SWITCH_CHANNEL).whileHeld(new ConveyManual());
            new InverseDigitalIOButton(CONVEYOR_DOWN_SWITCH_CHANNEL).whileHeld(new ConveyReverseManual());
            new InverseDigitalIOButton(SHOOTER_BUTTON_CHANNEL).whileHeld(new ConveyAutomatic());
            new InverseDigitalIOButton(STINGER_SWITCH_CHANNEL).whileHeld(new StingerExtend());
            
            new JoystickButton(shooterStick, 1).whileHeld(new ConveyManual());
            new JoystickButton(shooterStick, 4).whenPressed(new FlywheelStop());
            new JoystickButton(shooterStick, 5).whileHeld(new AcquirerReverse());
            new JoystickButton(shooterStick, 6).whileHeld(new ConveyReverseManual());
            new JoystickButton(shooterStick, 7).whileHeld(new AcquirerAcquire());
            new JoystickButton(shooterStick, 8).whileHeld(new ConveyAutomatic());

            // see getDistanceButton()
            
            // Debug box switches
            new JoystickButton(debugBox, 1).whileHeld(new FlywheelRun(Flywheel.distances[Flywheel.FENDER_INDEX], Flywheel.speedsTopHoop));
            new JoystickButton(debugBox, 2).whileHeld(new AcquirerAcquire());
            new JoystickButton(debugBox, 3).whileHeld(new ConveyAutomatic());
            new JoystickButton(debugBox, 4).whileHeld(new ConveyManual());
            // Debug box buttons
            new JoystickButton(debugBox, 5).whileHeld(new DrivetrainSetGear(false)); // low gear
            new JoystickButton(debugBox, 6).whileHeld(new DrivetrainSetGear(true)); // high gear
            new JoystickButton(debugBox, 9).whileHeld(new TusksExtend());
            new JoystickButton(debugBox, 10).whileHeld(new TusksRetract());
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
     * @return An integer value representing the distance button that was pressed.
     * If a Joystick button is being used, that will returned. Otherwise, the
     * button will be returned from the voltage (if it returns 0, no button is pressed).
     */
    public int getDistanceButton() {
       if (shooterStick.getRawButton(9)) {
           distanceButton = DISTANCE_BUTTON_STOP;
       }
       else if(shooterStick.getRawButton(10)) {
           distanceButton = DISTANCE_BUTTON_FENDER;
       }
       else if(shooterStick.getRawButton(11)) {
           distanceButton = DISTANCE_BUTTON_FAR;
       }
       int preValue = (int) ((getRawAnalogVoltage() / (getMaxVoltage() / 8)) + 0.5);
       // If no buttons are pressed, it does not update the distance.
       if(preValue != 0){
           distanceButton = preValue;
       }
       return distanceButton;
    }
    
    /**
     * Takes the distance button that has been pressed, and finds the distance for
     * the shooter to use.
     * @return distance for the shooter.
     */
    public double getDistanceFromDistanceButton(){
        switch(distanceButton){
            case DISTANCE_BUTTON_KEY:
                distanceInches = Flywheel.distances[Flywheel.CLOSE_KEY_INDEX];
                break;
            case DISTANCE_BUTTON_FAR:
                distanceInches = Flywheel.distances[Flywheel.MAX_DIST];
                break;
            case DISTANCE_BUTTON_FENDER_LENGTH:
                distanceInches = Flywheel.distances[Flywheel.FENDER_LONG_INDEX];
                break;
            case DISTANCE_BUTTON_FENDER_WIDTH:
                distanceInches = Flywheel.distances[Flywheel.FENDER_WIDE_INDEX];
                break;
            case DISTANCE_BUTTON_REVERSE:
                distanceInches = Flywheel.distances[Flywheel.REVERSE_INDEX];
                break;
            case DISTANCE_BUTTON_FENDER:
                distanceInches = Flywheel.distances[Flywheel.FENDER_INDEX];
                break;
            case DISTANCE_BUTTON_STOP:
                distanceInches = 0;
                break;
            default:
                break;
        }
        return distanceInches;
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

    /**
     * Gets value of hoop height toggle switch.
     * @return true if high setting, false if middle
     */
    public boolean getStingerSwitch() {
        try {
            return !enhancedIO.getDigital(STINGER_SWITCH_CHANNEL);
        } catch (EnhancedIOException ex) {
            return true;
        }
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

        //boolean[] dIO = {debugBox.getRawButton(1), debugBox.getRawButton(2), debugBox.getRawButton(3), debugBox.getRawButton(4)};

        /*for (int i = 0; i < 4; i++) {
            if (dIO[i]) {
                binaryValue[i] = 1;
            }
            else {
                binaryValue[i] = 0;
            }
        }*/

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
            return getMaxVoltage() - enhancedIO.getAnalogIn(SPEED_TRIM_POT_CHANNEL);
        } catch (EnhancedIOException ex) {
            return 0.0;
        }
    }

    public double getSpeedTrim() {
        double trim = getSpeedPot(); // 0 to max voltage (the 0 might be a little negative)
        double halfMax = getMaxVoltage() / 2;
        double speed = 0;
        speed = (trim - halfMax) / halfMax;
        //deadband
        if(speed < -0.1) {
            speed = speed * 1.0/0.9;
        }
        else if (speed < 0.1) {
            speed = 0;
        }
        else {
            speed = speed * 1.0 / 0.9;
        }
        return Flywheel.MAX_TRIM_RPM * speed;
    }
    
    public double getDelayPot() {
        try {
            return getMaxVoltage() - enhancedIO.getAnalogIn(DELAY_POT_CHANNEL);
        } catch (EnhancedIOException ex) {
            return 0.0;
        }
    }
    
    public double getDelayTime() {
        double delay = getDelayPot();

        if (delay > 0) {        //Just in case the value from the pot is negative. We observed a -.2.
            return MAX_WAIT_TIME * delay / getMaxVoltage();
        } else {
            return 0;
        }
    }
    
    /**
     * Turns on specified light on OI.
     * @param lightNum 
     */
    public void setLight(int lightNum) {
        turnOffLights();
        try {
            enhancedIO.setDigitalOutput(lightNum, true);
        }
        catch (EnhancedIOException e) {
        }
    }
    
    /**
     * Turns all lights off.
     */
    public void turnOffLights(){
        try {
            enhancedIO.setDigitalOutput(DISTANCE_BUTTON_KEY_LIGHT_CHANNEL, false);
            enhancedIO.setDigitalOutput(DISTANCE_BUTTON_FAR_LIGHT_CHANNEL, false);
            enhancedIO.setDigitalOutput(DISTANCE_BUTTON_FENDER_WIDE_LIGHT_CHANNEL, false);
            enhancedIO.setDigitalOutput(DISTANCE_BUTTON_FENDER_NARROW_LIGHT_CHANNEL, false);
            enhancedIO.setDigitalOutput(DISTANCE_BUTTON_REVERSE_LIGHT_CHANNEL, false);
            enhancedIO.setDigitalOutput(DISTANCE_BUTTON_FENDER_LIGHT_CHANNEL, false);
            enhancedIO.setDigitalOutput(DISTANCE_BUTTON_STOP_LIGHT_CHANNEL, false);
        }
        catch (EnhancedIOException e) {
        }
    }
    
    /**
     * Turns all lights on.
     */
    public void turnOnLights(){
        try {
            enhancedIO.setDigitalOutput(DISTANCE_BUTTON_KEY_LIGHT_CHANNEL, true);
            enhancedIO.setDigitalOutput(DISTANCE_BUTTON_FAR_LIGHT_CHANNEL, true);
            enhancedIO.setDigitalOutput(DISTANCE_BUTTON_FENDER_WIDE_LIGHT_CHANNEL, true);
            enhancedIO.setDigitalOutput(DISTANCE_BUTTON_FENDER_NARROW_LIGHT_CHANNEL, true);
            enhancedIO.setDigitalOutput(DISTANCE_BUTTON_REVERSE_LIGHT_CHANNEL, true);
            enhancedIO.setDigitalOutput(DISTANCE_BUTTON_FENDER_LIGHT_CHANNEL, true);
            enhancedIO.setDigitalOutput(DISTANCE_BUTTON_STOP_LIGHT_CHANNEL, true);
        }
        catch (EnhancedIOException e) {
        }
    }
    
    /**
     * Meant to be called continuously to update the lights on the OI board.
     * Depending on which button has been pressed last (which distance is
     * currently set), that button will be lit.
     */
    public void updateLights(){
        switch(distanceButton){
            case DISTANCE_BUTTON_KEY:
                setLight(DISTANCE_BUTTON_KEY_LIGHT_CHANNEL);
                break;
            case DISTANCE_BUTTON_FAR:
                setLight(DISTANCE_BUTTON_FAR_LIGHT_CHANNEL);
                break;
            case DISTANCE_BUTTON_FENDER_LENGTH:
                setLight(DISTANCE_BUTTON_FENDER_WIDE_LIGHT_CHANNEL);
                break;
            case DISTANCE_BUTTON_FENDER_WIDTH:
                setLight(DISTANCE_BUTTON_FENDER_NARROW_LIGHT_CHANNEL);
                break;
            case DISTANCE_BUTTON_REVERSE:
                setLight(DISTANCE_BUTTON_REVERSE_LIGHT_CHANNEL);
                break;
            case DISTANCE_BUTTON_FENDER:
                setLight(DISTANCE_BUTTON_FENDER_LIGHT_CHANNEL);
                break;
            case DISTANCE_BUTTON_STOP:
                setLight(DISTANCE_BUTTON_STOP_LIGHT_CHANNEL);
                break;
            default:
                turnOffLights();
                break;
        }
    }
    
    // For debugging purposes.
    public boolean getDigitalValue(int channel) {
        boolean b = false;
        try{
            b = !enhancedIO.getDigital(channel);
        }
        catch (EnhancedIOException e) {
        }
        return b;
    }
    
    // For debugging purposes.
    public double getAnalogValue(int channel) {
        double b = 0;
        try{
            b = enhancedIO.getAnalogOut(channel);
        }
        catch (EnhancedIOException e) {
        }
        return b;
    }

    public void resetBox(){
        turnOffLights();
        distanceButton = DISTANCE_BUTTON_STOP;
    }
}

