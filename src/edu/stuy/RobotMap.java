package edu.stuy;

/**
* The RobotMap is a mapping from the ports sensors and actuators are wired into
* to a variable name. This provides flexibility changing wiring, makes checking
* the wiring easier and significantly reduces the number of magic numbers
* floating around.
*/
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static final int leftMotor = 1;
    // public static final int rightMotor = 2;

    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static final int rangefinderPort = 1;
    // public static final int rangefinderModule = 1;

    /* USB PORTS */
    public static final int LEFT_JOYSTICK_PORT = 1;
    public static final int RIGHT_JOYSTICK_PORT = 2;
    public static final int SHOOTER_JOYSTICK_PORT = 3;
    public static final int DEBUG_BOX_PORT = 4;

    /* PWM OUTPUTS */
    public static final int FRONT_LEFT_MOTOR = 6;
    public static final int REAR_LEFT_MOTOR = 7;
    public static final int FRONT_RIGHT_MOTOR = 2;
    public static final int REAR_RIGHT_MOTOR = 3;

    public static final int ACQUIRER_ROLLER = 5;
    public static final int CONVEYOR_ROLLER = 1;
    
    /* CAN IDS */
    public static final int SHOOTER_UPPER_ROLLER = 3;
    public static final int SHOOTER_LOWER_ROLLER = 2;

    /* DIGITAL INPUTS */
    public static final int LEFT_ENCODER_CHANNEL_A = 5;
    public static final int LEFT_ENCODER_CHANNEL_B = 6;
    public static final int RIGHT_ENCODER_CHANNEL_A = 7;
    public static final int RIGHT_ENCODER_CHANNEL_B = 8;

    public static final int UPPER_CONVEYOR_SENSOR = 10;
    public static final int LOWER_CONVEYOR_SENSOR = 11;

    public static final int PRESSURE_SWITCH_CHANNEL = 14; // Switches when pneumatics pressure exceeds limit;  9 was bad
    
    /* RELAY OUTPUTS */
    public static final int COMPRESSOR_RELAY_CHANNEL = 1;
    public static final int REFLECTIVE_LIGHT = 2;
    public static final int TARGET_LIGHT = 3;
    public static final int UNDERBODY_LIGHTS = 4;
     

    /* SOLENOID */
    public static final int GEAR_SHIFT_LOW = 3;
    public static final int GEAR_SHIFT_HIGH = 4;
    public static final int TUSKS_SOLENOID_RETRACT = 5;
    public static final int TUSKS_SOLENOID_EXTEND = 6;

    /* VIRSYSJ */
    public static final int[] VIRSYS_OUTPUT_MAP = new int[15];
    public static final int[] VIRSYS_INPUT_MAP = new int[15];


    /* ANALOG INPUTS */
    public static final int GYRO_CHANNEL = 1;

    static {
        for (int i = 0; i < VIRSYS_OUTPUT_MAP.length; i++) {
            VIRSYS_OUTPUT_MAP[i] = -1;
        }
        VIRSYS_OUTPUT_MAP[FRONT_LEFT_MOTOR] = 0;
        VIRSYS_OUTPUT_MAP[REAR_LEFT_MOTOR] = 0;
        VIRSYS_OUTPUT_MAP[FRONT_RIGHT_MOTOR] = 1;
        VIRSYS_OUTPUT_MAP[REAR_RIGHT_MOTOR] = 1;

        for (int i = 0; i < VIRSYS_INPUT_MAP.length; i++) {
            VIRSYS_INPUT_MAP[i] = 0;
        }
        VIRSYS_INPUT_MAP[LEFT_ENCODER_CHANNEL_A] = 1;
        VIRSYS_INPUT_MAP[LEFT_ENCODER_CHANNEL_B] = 1;
        VIRSYS_INPUT_MAP[RIGHT_ENCODER_CHANNEL_A] = 2;
        VIRSYS_INPUT_MAP[RIGHT_ENCODER_CHANNEL_B] = 2;
    }
}
