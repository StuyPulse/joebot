/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.subsystems;

import edu.stuy.RobotMap;
import edu.stuy.commands.AcquirerStop;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 * @author Kevin Wang
 */
public class Acquirer extends Subsystem {
    private Victor roller;
    private boolean isAcquiring;

    // WARNING: The acquirer runs on a FisherPrice motor, meaning you CANNOT use a floating point value between 0 and 1!
    public static final int FWD = 1;
    public static final int OFF = 0;
    public static final int REV = -1;

    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    
    public Acquirer() {
        roller = new Victor(RobotMap.ACQUIRER_ROLLER);
        isAcquiring = false;
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new AcquirerStop());
    }
    
    /**
     * Rolls the acquirer forwards, backwards, or stops.
     * 
     * WARNING: The acquirer runs on a FisherPrice motor, meaning you 
     * CANNOT use a floating point value between 0 and 1!
     * 
     * @param speed Either 1, 0, or -1.
     */
    private void roll(double speed) {
        roller.set(speed);
    }

    public void stop() {
        roll(OFF);// WARNING: The acquirer runs on a FisherPrice motor, meaning you CANNOT use a floating point value between 0 and 1!
        isAcquiring = false;
    }

    public void acquire() {
        isAcquiring = true;
        roll(FWD); // WARNING: The acquirer runs on a FisherPrice motor, meaning you CANNOT use a floating point value between 0 and 1!
    }

    public void acquireReverse() {
        roll(REV);// WARNING: The acquirer runs on a FisherPrice motor, meaning you CANNOT use a floating point value between 0 and 1!
    }

    public double getRoller() {
        return roller.get();
    }

    public boolean isAcquiring() {
        return isAcquiring;
    }
}
