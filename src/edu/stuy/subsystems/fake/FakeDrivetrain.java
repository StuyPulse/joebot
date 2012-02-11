/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems.fake;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 * @author Kevin Wang
 */
public class FakeDrivetrain extends Subsystem {
    public static class SpeedRamp {
        /**
         * Profiles based on generic distance measurement to wall and the distance to travel.
         * Speed/Distance follows the following profile:
         * |
         * |
         * |     _________
         * |    /         \ 
         * |   /           \
         * |__/             \__
         * |               
         * ------------------------------
         * 
         * NOTE: Possible issues include negative differences in case a sensor measures a greater
         *       distance than actually exists.
         * 
         * TODO: Make this work in the backwards direction, i.e. towards the bridge.
         * 
         * @param distToFinish Distance from the robot to the Fender
         * @param totalDistToTravel Total distance for the robot to travel
         * @param direction 1 for forward, -1 for backward
         * @return The speed at which to drive the motors, from -1.0 to 1.0
         */
        public static double profileSpeed_Bravo(double distToFinish, double totalDistToTravel, int direction) {
            return 0;

        }
    }
    
  


    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    public FakeDrivetrain() {
    
    }
    
    /**
     * Gets the analog voltage of the MaxBotics ultrasonic sensor, and debounces the input
     * @return Analog voltage reading from 0 to 5
     */
    public double getSonarVoltage() {
        return 0;
    }
    
    /**
     * Scales sonar voltage reading to centimeters
     * @return distance from alliance wall in centimeters, as measured by sonar sensor
     */
    public double getSonarDistance_in() {
   return 0;
    }
            
    public void initDefaultCommand() {
 
    }

    public Command getDefaultCommand(){
        return super.getDefaultCommand();
    }

    public void tankDrive(double leftValue, double rightValue) {
       System.out.println("tankDrive");
    }

    public void setGear(boolean high) {
 
    }
    
    public boolean getGear() {
        return false;
    }
    
    public void initController() {
       
    }
    
    public void endController() {
            }
    
    /**
     * Sets the ramping distance and direction by constructing a new PID controller.
     * @param distance inches to travel
     */
    public void setDriveStraightDistanceAndDirection(final double distance, final int direction) {
        System.out.println("setDriveStraightDistanceAndDirection");
    }

    public void driveStraight() {
        System.out.println("DriveStraight");
    
    }

    /**
     * Calculate average distance of the two encoders.  
     * @return Average of the distances (inches) read by each encoder since they were last reset.
     */
    public double getAvgDistance() {
        return 0;
    }
    
    /**
     * Reset both encoders's tick, distance, etc. count to zero
     */
    public void resetEncoders() {
   System.out.println("Drivetrain resetEncoder");
    }
}
