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

    public static JoeSpeed upperRoller;
    public static JoeSpeed lowerRoller;
    
    /**
     * The two points that we're in between for shooting.
     * Set to the same value if you're at an exact point, like fenderIndex
     */
    public int indexSetPointLower;
    public int indexSetPointHigher;
    public double ratioBetweenDistances; // 0-1 position of distance setpoint between two closest points.
    
    // Fender special Variables
    
    
    public double fenderDistance = fenderWidth + shooterToBumper + 13.35;
    
    public double fenderTopRPM = theoreticalDesiredExitRPM(fenderDistance);
    public double fenderBottomRPM = theoreticalDesiredExitRPM(fenderDistance) + 0;
    public double fenderTopUpperBoundTolerance = 1;
    public double fenderTopLowerBoundTolerance = 1;
    public double fenderBottomUpperBoundTolerance = 1;
    public double fenderBottomLowerBoundTolerance = 1;
    
    
    public static final double thetaDegrees = 72;
    public static final double thetaRadians = Math.toRadians(thetaDegrees);

    
    public static int fenderSideIndex = 0;
    public static int fenderWideIndex = 1;
    public static int fenderLongIndex = 2;
    public static int fenderSideWideIndex = 3;
    public static int fenderSideLongIndex = 4;
    public static int keyIndex = 5;

    public static int numDistances = 6;

    /**
     * How much faster should the lower flywheel run, to:
     *  A)  Produce spin
     *  B)  Account for lower wheel losing energy by being in contact with ball longer
     * 
     */
    public static double spinRPMDifference = 0; // We do not know
    
    public static double[] distances = new double[numDistances]; // all inches
    public static double[] speeds = new double[numDistances];
    
    public static double[] Lowtolerance = new double[numDistances]; // values haven't been found yet
    static{
        Lowtolerance[fenderLongIndex] = 1;
        Lowtolerance[fenderWideIndex] = 2;
        Lowtolerance[fenderSideIndex] = 3;
        Lowtolerance[fenderSideLongIndex] = 4;
        Lowtolerance[fenderSideWideIndex] = 5;
        Lowtolerance[keyIndex] = 6;
    }
       public static double[] Hightolerance = new double[numDistances]; // values haven't been found yet
    static{
        Hightolerance[fenderLongIndex] = 1;
        Hightolerance[fenderSideIndex] = 2;
        Hightolerance[fenderWideIndex] = 3;
        Hightolerance[fenderSideLongIndex] = 4;
        Hightolerance[fenderSideWideIndex] = 5;
        Hightolerance[keyIndex] = 6;
    }

    static double wideBot = 28.0;
    static double longBot = 38.0;
    static double shooterToBumper = 23.5;
    static double fenderWidth = 38.75;
    static double backboardToHoopCenter = 6 + 9;
    
    
//* 13.35 is the additional horizontal distance from the backboard to the center of the hoop  
    
    static {
        distances[fenderSideIndex] = 50.5 + shooterToBumper; // 74
        distances[fenderWideIndex] = distances[fenderIndex] + wideBot - backboardToHoopCenter; // 90.5
        distances[fenderSideWideIndex] = distances[fenderSideIndex] + wideBot; // 102
        distances[fenderLongIndex] = distances[fenderIndex] + longBot - backboardToHoopCenter; // 100.5
        distances[fenderSideLongIndex] = distances[fenderSideIndex] + longBot; // 112
        distances[keyIndex] = 144.0 + shooterToBumper - backboardToHoopCenter;
        
        for (int i = 0; i < distances.length; i++) {
            System.out.println(distances[i]);
        }

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
        
        //if(upperRoller.isAtSetPoint() && lowerRoller.isAtSetPoint()){
        //if (upperRoller.getRPM() - upperSetPoint < Hightolerance[fender])
        boolean speedGood = true;
        //}
        
        double highToleranceVal;
        double lowToleranceVal;
        
        highToleranceVal = Hightolerance[indexSetPointLower] +
                ratioBetweenDistances * (Hightolerance[indexSetPointHigher] - Hightolerance[indexSetPointLower]);
        
        
        lowToleranceVal = Lowtolerance[indexSetPointLower] +
                ratioBetweenDistances * (Lowtolerance[indexSetPointHigher] - Lowtolerance[indexSetPointLower]);
        
        
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
    public double[] lookupRPM(double distanceInches) {
        double[] returnVal = new double[2];
        
        if (Math.abs(distanceInches - fenderDistance) < 0.1) {
            returnVal[0] = fenderBottomRPM;
            returnVal[1] = fenderTopRPM;
        }
        else {
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
            double lowerRPM = upperRPM + spinRPMDifference;
            returnVal[0] = lowerRPM;
            returnVal[1] = upperRPM;
        }
        return returnVal;
    }
}