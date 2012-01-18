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

    /* PWM OUTPUTS */
    public static final int FRONT_LEFT_MOTOR = 1;
    public static final int REAR_LEFT_MOTOR = 2;
    public static final int FRONT_RIGHT_MOTOR = 3;
    public static final int REAR_RIGHT_MOTOR = 4;

    public static final int LEFT_GEAR_SHIFT = 5;
    public static final int RIGHT_GEAR_SHIFT = 6;
    
    public static final int UPPER_SHOOTER_ROLLER = 7;
    public static final int LOWER_SHOOTER_ROLLER = 8;

    public static final int ACQUIRER_ROLLER = 9;

    /* DIGITAL INPUT */
    public static final int UPPER_ROLLER_ENCODER_A = 1;
    public static final int UPPER_ROLLER_ENCODER_B = 2;
    public static final int LOWER_ROLLER_ENCODER_A = 3;
    public static final int LOWER_ROLLER_ENCODER_B = 4;
}