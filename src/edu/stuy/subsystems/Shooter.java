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

    public static JaguarSpeed upperRoller;
    public static JaguarSpeed lowerRoller;

    public static final double THETA_DEGREES = 72;
    public static final double THETA_RADIANS = Math.toRadians(THETA_DEGREES);


    public static int NUM_DISTANCES = 7;

    public static double[] distances = new double[NUM_DISTANCES]; // all inches
    public static final int FENDER_INDEX = 0;
    public static final int FENDER_LONG_INDEX = 1;
    public static final int FENDER_WIDE_INDEX = 2;
    public static final int FENDER_SIDE_INDEX = 3;
    public static final int FENDER_SIDE_LONG_INDEX = 4;
    public static final int FENDER_SIDE_WIDE_INDEX = 5;
    public static final int KEY_INDEX = 6;

    public static double[] speeds = new double[NUM_DISTANCES];

    public static final double WIDE_BOT = 28.0;
    public static final double LONG_BOT = 38.0;

    static {
        distances[FENDER_INDEX] = 34.0;
        distances[FENDER_LONG_INDEX] = distances[FENDER_INDEX] + LONG_BOT;
        distances[FENDER_WIDE_INDEX] = distances[FENDER_INDEX] + WIDE_BOT;
        distances[FENDER_SIDE_INDEX] = 50.5;
        distances[FENDER_SIDE_LONG_INDEX] = distances[FENDER_SIDE_INDEX] + LONG_BOT;
        distances[FENDER_SIDE_WIDE_INDEX] = distances[FENDER_SIDE_INDEX] + WIDE_BOT;
        distances[KEY_INDEX] = 144.0;

        for (int i = 0; i < speeds.length; i++) {
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
        boolean speedGood = false;
        if(upperRoller.isAtSetPoint() && lowerRoller.isAtSetPoint()){
            speedGood = true;
        }
        return speedGood;
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
    public double lookupRPM(double distanceInches) {
        for (int i = 0; i < distances.length; i++) {
            if (Math.abs(distances[i] - distanceInches) < 0.1) {
                return speeds[i];
            }
        }
        return 0; // change this to find the intermediate value for a speed
                // that we haven't tuned for.
    }
}