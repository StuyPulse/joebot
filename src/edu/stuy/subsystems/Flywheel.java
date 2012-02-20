/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.stuy.RobotMap;
import edu.stuy.commands.FlywheelRun;
import edu.stuy.speed.JaguarSpeed;
import edu.stuy.speed.JoeSpeed;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 * @author Kevin Wang
 */
public class Flywheel extends Subsystem {
    /** Distances **/
    static final double stop = 0;
    static final double wideBot = 28.0;
    static final double longBot = 38.0;
    static final double shooterToBumper = 23.5;
    static final double fenderDepth = 38.75;
    static final double backboardToHoopCenter = 6 + 9;
    static final double halfFenderWidth = 50.5;

    public static final double THETA_DEGREES = 72;
    public static final double THETA_RADIANS = Math.toRadians(THETA_DEGREES);
    
    public double lowerSetpoint;
    public double upperSetpoint;
    
    public static JoeSpeed upperRoller;
    public static JoeSpeed lowerRoller;

    public static final double TOP_HOOP_HEIGHT = 98.0;
    public static final double MIDDLE_HOOP_HEIGHT = 61.0;

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
    public static double rpmTolerance = 75;

    /** Positions **/
    public static final int numDistances = 14;
    public static final double[] distances = new double[numDistances]; // all inches
    public static final double[] speedsTopHoop = new double[numDistances];
    public static final double[] speedsMiddleHoop = new double[numDistances];
    /**
     * How much faster should the lower flywheel run, to:
     *  A)  Produce spin
     *  B)  Account for lower wheel losing energy by being in contact with ball longer
     *
     */
    public static double[] spinBottomMinusTopRPM = new double[numDistances];


    public static final int STOP_INDEX = 0;
    public static final int FENDER_INDEX = 1;
    public static final int FENDER_SIDE_INDEX = 2;
    public static final int FENDER_WIDE_INDEX = 3;
    public static final int HIGHEST_BACKBOARD_INDEX = 4;
    public static final int LOWEST_SWISH_INDEX = 5;
    public static final int FENDER_LONG_INDEX = 6;
    public static final int FENDER_SIDE_WIDE_INDEX = 7;
    public static final int FENDER_SIDE_LONG_INDEX = 8;
    public static final int KEY_INDEX = 9;
    public static final int KEY_SLANT_INDEX = 10;
    public static final int KEY_MIDDLE_HOOP_INDEX = 12;
    public static final int MAX_DIST = 13;
    
    public static final int MAX_TRIM_RPM = 400;
    
    static {
        distances[STOP_INDEX] = 0;
        distances[FENDER_INDEX] = fenderDepth + shooterToBumper;
        distances[FENDER_SIDE_INDEX] = halfFenderWidth + shooterToBumper;
        distances[FENDER_WIDE_INDEX] = distances[FENDER_INDEX] + wideBot;
        distances[HIGHEST_BACKBOARD_INDEX] = distances[FENDER_WIDE_INDEX];
        distances[LOWEST_SWISH_INDEX] = distances[HIGHEST_BACKBOARD_INDEX];
        distances[FENDER_SIDE_WIDE_INDEX] = distances[FENDER_SIDE_INDEX] + wideBot;
        distances[FENDER_LONG_INDEX] = distances[FENDER_INDEX] + longBot;
        distances[FENDER_SIDE_LONG_INDEX] = distances[FENDER_SIDE_INDEX] + longBot;
        distances[KEY_INDEX] = 144.0 + shooterToBumper;
        distances[KEY_SLANT_INDEX] = 215; //Fix this value through testing
        distances[KEY_MIDDLE_HOOP_INDEX] = 144 + shooterToBumper;
        distances[MAX_DIST] = 300;

        speedsTopHoop[STOP_INDEX] = 0;
        speedsTopHoop[FENDER_INDEX] = 1200;
        speedsTopHoop[FENDER_SIDE_INDEX] = 0;
        speedsTopHoop[FENDER_WIDE_INDEX] = 1340;
        speedsTopHoop[HIGHEST_BACKBOARD_INDEX] = speedsTopHoop[FENDER_WIDE_INDEX];
        speedsTopHoop[LOWEST_SWISH_INDEX] = speedsTopHoop[FENDER_WIDE_INDEX];
        speedsTopHoop[FENDER_SIDE_WIDE_INDEX] = 0; //NOT TESTED
        speedsTopHoop[FENDER_LONG_INDEX] = 1075;
        speedsTopHoop[FENDER_SIDE_LONG_INDEX] = 0; //NOT TESTED
        speedsTopHoop[KEY_INDEX] = 1550;
        speedsTopHoop[KEY_SLANT_INDEX] = 1560; //TODO: Fix this value through testing
        speedsTopHoop[KEY_MIDDLE_HOOP_INDEX] = 1425; //TODO: Fix value through testing
        speedsTopHoop[MAX_DIST] = 3000; // TODO: FIx this value through testing
        for (int i = 0; i <= HIGHEST_BACKBOARD_INDEX; i++) {
            speedsMiddleHoop[i] = theoreticalDesiredExitRPM(distances[i] + 2 * backboardToHoopCenter, MIDDLE_HOOP_HEIGHT);
        }
        for (int i = LOWEST_SWISH_INDEX; i < numDistances; i++) {
            speedsMiddleHoop[i] = theoreticalDesiredExitRPM(distances[i], MIDDLE_HOOP_HEIGHT);
        }
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public Flywheel() {
       // speedLight = new Relay(RobotMap.SPEED_BAD_LIGHT);
        upperRoller = new JaguarSpeed(RobotMap.SHOOTER_UPPER_ROLLER, rpmTolerance);
        lowerRoller = new JaguarSpeed(RobotMap.SHOOTER_LOWER_ROLLER, rpmTolerance);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new FlywheelRun());
    }

    public void setFlywheelSpeeds(double upperRPM, double lowerRPM) {
        upperRoller.setRPM(upperRPM);
        lowerRoller.setRPM(lowerRPM);
        lowerSetpoint = lowerRPM;
        upperSetpoint = upperRPM;
    }

    /**
     * Given a desired flywheel RPM, checks if the current RPM is
     * within an acceptable range of the desired RPM, and turns
     * on or off the speed light accordingly.
     */
    public boolean isSpeedGood() {
        boolean speedGood = (Math.abs(Math.abs(upperSetpoint) - Math.abs(upperRoller.getRPM())) < rpmTolerance) &&
                            (Math.abs(Math.abs(lowerSetpoint) - Math.abs(lowerRoller.getRPM())) < rpmTolerance);
        SmartDashboard.putDouble("Upper error", Math.abs(Math.abs(upperSetpoint) - Math.abs(upperRoller.getRPM())));
        SmartDashboard.putDouble("Lower error", Math.abs(Math.abs(lowerSetpoint) - Math.abs(lowerRoller.getRPM())));
        return speedGood;
    }

    /**
     * Given a distance that we want to shoot the ball, calculate
     * the flywheel RPM necessary to shoot the ball that distance
     */
    public static double theoreticalDesiredExitRPM(double distanceInches, double hoopHeightInches) {
        double g = 387; // gravity: inches per second squared
        double shooterHeightInches = 36.0;
        double h = hoopHeightInches - shooterHeightInches; // height of hoop above the shooter: inches
        double linearSpeedInchesPerSecond = (distanceInches * Math.sqrt(g))
                / (Math.sqrt(2) * Math.cos(THETA_RADIANS) * Math.sqrt(distanceInches * Math.tan(THETA_RADIANS) - h));
        double wheelRadiusInches = 3.0;
        double wheelCircumferenceInches = 2 * Math.PI * wheelRadiusInches;
        double RPM = 60 * linearSpeedInchesPerSecond / wheelCircumferenceInches;
        return RPM;
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
    public double[] lookupRPM(double distanceInches, double[] speeds) {
        double[] returnVal = new double[2];
        
        // Linear search for given distance in distances array.
        // The distances[] array must be sorted from smallest to largest.
        // Assuming the generic case of a distance that's not already in the table,
        // this loop finds which two points it's in between.
        for (int i = 1; i < distances.length; i++) {
            indexSetPointHigher = i;
            indexSetPointLower = i-1;
            if (distances[i] > distanceInches) break;
            
            /* Keep iterating through the loop until we find the two points that
             * distanceInches is in-between.  We know we're finished when
             *     distances[i-1] < distanceInches < distances[i]
             *
             * Suppose we're searching for distanceInches = 35, in this array:
             *      | 10  | 20  | 30  | 40 | 50 |
             * 
             * First we try this (start at i=1, i-1 = 0):
             *      | i-1 | i   |     |    |    |
             *   distances[1] is 20, less than distance inches.  We want distances[i]
             *   to be the nearest point *above* distanceInches, so if it's less than
             *   distanceInches then we haven't found the right point yet.
             * 
             * So we continue:
             *      |     | i-1 | i   |    |    |
             *   distances[2] is 30, still less than distance inches.
             * 
             * Next:
             *      |     |     | i-1 |  i |    |
             * 
             * Finally distances[3] > distanceInches, and since we already found
             * distances[2] < distanceInches, we know that the two closest points
             * for 35 are indices 2 and 3 (values 30 and 40).
             */
        }
        
        /** ratioBetweenDistances gives a value between 0-1 describing where
         *  distanceInches is between its two closest points.
         *    0.0: exactly equal to distances[indexSetPointLower]
         *    0.5: halfway between
         *    1.0: exactly equal to distances[indexSetPointHigher]
         */
        ratioBetweenDistances = (distanceInches - distances[indexSetPointLower])
                /
                (distances[indexSetPointHigher] - distances[indexSetPointLower]);
        
        /* Set upperRPM to be a speed between indexSetPointLower and indexSetPointHigher,
         * in the same proportion as the distances.
         * Basically plot an intermediate point on the line connecting the two closest points.
         */
        double upperRPM = speeds[indexSetPointLower] +
                ((speeds[indexSetPointHigher] - speeds[indexSetPointLower]) *
                (ratioBetweenDistances));
        
        /* Add some backspin to lowerRPM, again using a value in between the two closest points.
         * Interpolation doesn't do so much here, but at least it sets it to the
         * correct spin for backboard vs. swish
         */
        double lowerRPM = upperRPM +
                spinBottomMinusTopRPM[indexSetPointLower] +
                (spinBottomMinusTopRPM[indexSetPointHigher] - spinBottomMinusTopRPM[indexSetPointLower]) *
                ratioBetweenDistances;
        
        returnVal[0] = lowerRPM;
        returnVal[1] = upperRPM;
        return returnVal;
    }
}