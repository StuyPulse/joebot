/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

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
public class Shooter extends Subsystem {

    public static JoeSpeed upperRoller;
    public static JoeSpeed lowerRoller;
    private Relay speedLight;
    public static final double thetaDegrees = 72;
    public static final double thetaRadians = Math.toRadians(thetaDegrees);
    public static int numDistances = 7;
    public static double[] distances = new double[numDistances]; // all inches
    public static int fenderIndex = 0;
    public static int fenderLongIndex = 1;
    public static int fenderWideIndex = 2;
    static int fenderSideIndex = 3;
    static int fenderSideLongIndex = 4;
    static int fenderSideWideIndex = 5;
    static int keyIndex = 6;
    public static double[] speeds = new double[numDistances];
    static double wideBot = 28.0;
    static double longBot = 38.0;

    static {
        distances[fenderIndex] = 34.0;
        distances[fenderLongIndex] = distances[fenderIndex] + longBot;
        distances[fenderWideIndex] = distances[fenderIndex] + wideBot;
        distances[fenderSideIndex] = 50.5;
        distances[fenderSideLongIndex] = distances[fenderSideIndex] + longBot;
        distances[fenderSideWideIndex] = distances[fenderSideIndex] + wideBot;
        distances[keyIndex] = 144.0;

        for (int i = 0; i < speeds.length; i++) {
            speeds[i] = theoreticalDesiredExitRPM(distances[i]);
        }
    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public Shooter() {
        speedLight = new Relay(RobotMap.SPEED_BAD_LIGHT);
        speedLight.setDirection(Relay.Direction.kForward);

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

    /**
     * Given a desired flywheel RPM, checks if the current RPM is
     * within an acceptable range of the desired RPM, and turns
     * on or off the speed light accordingly.
     */
    public boolean isSpeedGood() {
        boolean speedGood = false;
        if (upperRoller.isAtSetPoint() && lowerRoller.isAtSetPoint()) {
            speedGood = true;
            speedLight.set(Relay.Value.kOff);  // Turn OFF the red "SPEED_BAD" light
        } else {
            speedLight.set(Relay.Value.kOn);
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
        double linearSpeedInchesPerSecond = (distanceInches * Math.sqrt(g))
                / (Math.sqrt(2) * Math.cos(thetaRadians) * Math.sqrt(distanceInches * Math.tan(thetaRadians) - h));
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