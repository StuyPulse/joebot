/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems.fake;

import edu.stuy.subsystems.*;
import edu.stuy.RobotMap;
import edu.stuy.speed.JaguarSpeed;
import edu.stuy.speed.JoeSpeed;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.stuy.RobotMap;

/**
 *
 * @author Kevin Wang
 */
public class FakeShooter extends Subsystem {
    /** Distances **/
    static final double wideBot = 28.0;
    static final double longBot = 38.0;
    static final double shooterToBumper = 23.5;
    static final double fenderDepth = 38.75;
    static final double backboardToHoopCenter = 6 + 9;
    static final double halfFenderWidth = 50.5;

    public static final double thetaDegrees = 72;
    public static final double thetaRadians = Math.toRadians(thetaDegrees);

    public double lowerSetpoint;
    public double upperSetpoint;

    public static JaguarSpeed upperRoller;
    public static JaguarSpeed lowerRoller;
    private Relay speedLight;

    public static final double THETA_DEGREES = 72;
    public static final double THETA_RADIANS = Math.toRadians(THETA_DEGREES);

    /**
     * The two points that we're in between for shooting.
     * Set to the same value if you're at an exact point, like fenderIndex
     */
    public int indexSetPointLower;
    public int indexSetPointHigher;
    public double ratioBetweenDistances; // 0-1 position of distance setpoint between two closest points.

    /**
     * The maximum error in speed that will still result in a basket (5 inches
     * leeway on either end). Technically this is different for each distance
     * (both as a sheer RPM and as a percentage), but it is almost the same at
     * the fender and the key, the closest and farthest shots.
     * 
     * Farther shots have slightly lower tolerance, so this number represents
     * the minimum tolerance among all values (i.e. the tolerance at the key).
     * This way any shot made from within this tolerance will go the correct distance.
     */
    public static double rpmTolerance = 16;

    /** Positions **/
    public static int numDistances = 9;

    public static double[] distances = new double[numDistances]; // all inches
    public static double[] speeds = new double[numDistances];
    /**
     * How much faster should the lower flywheel run, to:
     *  A)  Produce spin
     *  B)  Account for lower wheel losing energy by being in contact with ball longer
     *
     */
    public static double[] spinBottomMinusTopRPM = new double[numDistances];

    public static final int FENDER_INDEX = 0;
    public static final int FENDER_SIDE_INDEX = 1;
    public static final int FENDER_WIDE_INDEX = 2;
    public static final int HIGHEST_BACKBOARD_INDEX = 3;
    public static final int LOWEST_SWISH_INDEX = 4;
    public static final int FENDER_LONG_INDEX = 5;
    public static final int FENDER_SIDE_WIDE_INDEX = 6;
    public static final int FENDER_SIDE_LONG_INDEX = 7;
    public static final int KEY_INDEX = 8;

    static {

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public FakeShooter() {
 
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public void setFlywheelSpeeds(double upperRPM, double lowerRPM) {
    System.out.println("Shooter setFlyWheelSpeeds");
    }

    /**
     * Given a desired flywheel RPM, checks if the current RPM is
     * within an acceptable range of the desired RPM, and turns
     * on or off the speed light accordingly.
     */
    public boolean isSpeedGood() {
    
        return true;
    }

    /**
     * Given a distance that we want to shoot the ball, calculate
     * the flywheel RPM necessary to shoot the ball that distance
     */
    public static double theoreticalDesiredExitRPM(double distanceInches) {
 
        return 0;
    }

    /**
     * Use the `speeds' lookup table.
     *
     * If the distance passed in is already in the speeds table, that speed is returned.
     * Otherwise, this will use the line of best fit to find the optimal speed for the
     * intermediate distance.
     *
     * @param distanceInches Distance from robot's shooter to the backboard.
     * @return Two-element array containing the speed in RPM that the shooter's
     * bottom and top rollers should run, respectively.
     * 
     */
    public double[] lookupRPM(double distanceInches) {
       double rpm[] = {0,0};
        return rpm;
    }
}