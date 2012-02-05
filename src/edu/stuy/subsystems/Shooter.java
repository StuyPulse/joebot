/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.stuy.RobotMap;
import edu.stuy.speed.JaguarSpeed;
import edu.stuy.speed.JoeSpeed;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Kevin Wang
 */
public class Shooter extends Subsystem {
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

    public static JoeSpeed upperRoller;
    public static JoeSpeed lowerRoller;

    /**
     * The two points that we're in between for shooting.
     * Set to the same value if you're at an exact point, like fenderIndex
     */
    public int indexSetPointLower;
    public int indexSetPointHigher;
    public double ratioBetweenDistances; // 0-1 position of distance setpoint between two closest points.

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

    public static int fenderIndex = 0;
    public static int fenderSideIndex = 1;
    public static int fenderWideIndex = 2;
    public static int highestBackboardIndex = 3;
    public static int lowestSwishIndex = 4;
    public static int fenderLongIndex = 5;
    public static int fenderSideWideIndex = 6;
    public static int fenderSideLongIndex = 7;
    public static int keyIndex = 8;

    static {
        distances[fenderIndex] = fenderDepth + shooterToBumper;
        distances[fenderSideIndex] = halfFenderWidth + shooterToBumper;
        distances[fenderWideIndex] = distances[fenderIndex] + wideBot;
        distances[highestBackboardIndex] = distances[fenderWideIndex];
        distances[lowestSwishIndex] = distances[highestBackboardIndex];
        distances[fenderSideWideIndex] = distances[fenderSideIndex] + wideBot;
        distances[fenderLongIndex] = distances[fenderIndex] + longBot;
        distances[fenderSideLongIndex] = distances[fenderSideIndex] + longBot;
        distances[keyIndex] = 144.0 + shooterToBumper;
        
        for (int i = 0; i < distances.length; i++) {
            System.out.println(distances[i]);
        }

        for (int i = 0; i <= highestBackboardIndex; i++) {
            speeds[i] = theoreticalDesiredExitRPM(distances[i] + 2 * backboardToHoopCenter);
        }
        for (int i = lowestSwishIndex; i < numDistances; i++) {
            speeds[i] = theoreticalDesiredExitRPM(distances[i]);
        }
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public Shooter() {
        upperRoller = new JaguarSpeed(RobotMap.SHOOTER_UPPER_ROLLER);
        lowerRoller = new JaguarSpeed(RobotMap.SHOOTER_LOWER_ROLLER);
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }

    public void setFlywheelSpeeds(double upperRPM, double lowerRPM) {
        upperRoller.setRPM(upperRPM);
        lowerRoller.setRPM(lowerRPM);
    }
    
    public boolean isSpeedGood() {
        return (Math.abs(upperSetpoint - upperRoller.getRPM()) < rpmTolerance) &&
                (Math.abs(lowerSetpoint - lowerRoller.getRPM()) < rpmTolerance);
    }

    /**
     * Given a distance that we want to shoot the ball, calculate
     * the flywheel RPM necessary to shoot the ball that distance
     */
    public static double theoreticalDesiredExitRPM(double distanceInches) {
        double g = 387; // gravity: inches per second squared
        double shooterHeightInches = 36.0;
        double topHoopHeightInches = 98.0;
        double h = topHoopHeightInches - shooterHeightInches; // height of hoop above the shooter: inches
        double thetaRadians = Math.toRadians(72.0);
        double linearSpeedInchesPerSecond = (distanceInches * Math.sqrt(g)) /
                (Math.sqrt(2)* Math.cos(thetaRadians) * Math.sqrt(distanceInches * Math.tan(thetaRadians) - h));
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
     * Warning: intermediate distance calculations have not yet been implemented.
     *
     * @param distanceInches
     * @return
     */
    public double[] lookupRPM(double distanceInches) {
        double[] returnVal = new double[2];
        for (int i = 0; i < distances.length; i++) {
            indexSetPointHigher = i;
            indexSetPointLower = i-1;
            if (distances[i] > distanceInches) break;
        }
        ratioBetweenDistances = (distanceInches - distances[indexSetPointLower])
                /
                (distances[indexSetPointHigher] - distances[indexSetPointLower]);

        double upperRPM = speeds[indexSetPointLower] +
                ((speeds[indexSetPointHigher] - speeds[indexSetPointLower]) *
                (ratioBetweenDistances));
        double lowerRPM = upperRPM +
                spinBottomMinusTopRPM[indexSetPointLower] +
                (spinBottomMinusTopRPM[indexSetPointHigher] - spinBottomMinusTopRPM[indexSetPointLower]) *
                ratioBetweenDistances;
        returnVal[0] = lowerRPM;
        returnVal[1] = upperRPM;
        lowerSetpoint = lowerRPM;
        upperSetpoint = upperRPM;
        return returnVal;
    }
}