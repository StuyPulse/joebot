/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems.fake;

import edu.stuy.speed.JoeSpeed;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Kevin Wang
 */
public class FakeShooter extends Subsystem {
    /** Distances **/
    
    public static JoeSpeed upperRoller;
    public static JoeSpeed lowerRoller;




    /**
     * The two points that we're in between for shooting.
     * Set to the same value if you're at an exact point, like fenderIndex
     */
  // 0-1 position of distance setpoint between two closest points.

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
   
    /**
     * How much faster should the lower flywheel run, to:
     *  A)  Produce spin
     *  B)  Account for lower wheel losing energy by being in contact with ball longer
     *
     */
   
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
        System.out.println("isSpeedGood");
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