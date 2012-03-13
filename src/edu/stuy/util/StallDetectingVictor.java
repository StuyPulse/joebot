/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.stuy.util;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Victor;

/**
 *
 * @author 694
 */
public class StallDetectingVictor extends Victor {
    private static double STALL_CURRENT = 30;
    private static double FAULT_TIME = 1;
    
    private CurrentThing currentThing;
    private double lastStallTime;

    public StallDetectingVictor(int victorChannel, int currentThingChannel) {
        super(victorChannel);
        currentThing = new CurrentThing(currentThingChannel);
        currentThing.start();
    }

    public void set(double value) {
        if (isStalled()) {
            lastStallTime = Timer.getFPGATimestamp();
            super.set(0);
        }
        else if (isStallFault()) {
            super.set(0);
        }
        else {
            super.set(value);
        }
    }

    public double getCurrent() {
        return currentThing.getCurrent();
    }

    public boolean isStalled() {
        return getCurrent() > STALL_CURRENT;
    }

    public boolean isStallFault() {
        return Timer.getFPGATimestamp() - lastStallTime < FAULT_TIME;
    }
}
