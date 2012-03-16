/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.util;

import edu.wpi.first.wpilibj.AnalogChannel;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/**
 *
 * @author 694
 */
public class CurrentThing {
    private AnalogChannel analog;
    private Vector measurements;
    private Timer updateMeasurements;
    private final int UPDATE_PERIOD_MS = 10;

    public CurrentThing(int channel) {
        analog = new AnalogChannel(channel);
        measurements = new Vector();
    }

    /**
     * Starts the update thread.
     */
    public void start() {
        stop();
        updateMeasurements = new Timer();
        updateMeasurements.schedule(new TimerTask() {

            public void run() {
                synchronized (measurements) {
                    measurements.addElement(new Double(analog.getVoltage()));
                    if (measurements.size() > 10) {
                        measurements.removeElementAt(0);
                    }
                }
            }
        }, 0, UPDATE_PERIOD_MS);
    }

    public void stop() {
        if (updateMeasurements != null) {
            updateMeasurements.cancel();
        }
    }

    public void reset() {
        measurements.removeAllElements();
    }

    /**
     * Returns the current read by the current thing.
     * @return Current in amps
     */
    public double getCurrent() {
        if (measurements.isEmpty()) {
            return 0;
        }
        double sum = 0;
        synchronized (measurements) {
            for (int i = 0; i < measurements.size(); i++) {
                sum += ((Double) measurements.elementAt(i)).doubleValue();
            }
            return sum / 0.01;
        }
    }
}
