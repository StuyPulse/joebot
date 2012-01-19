/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.smartdashboard;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Gyro;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.networktables.NetworkListener;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * The {@link SendableGyro} class behaves exactly the same as a {@link Gyro} except that it
 * also implements {@link SmartDashboardData} so that it can be sent over to the {@link SmartDashboard}.
 * 
 * @author Joe Grinstead
 */
public class SendableGyro extends Gyro implements SmartDashboardData {

    /** The time (in seconds) between updates to the table */
    private static final double DEFAULT_TIME_BETWEEN_UPDATES = .2;
    /** The angle added to the gyro's value */
    private double offset = 0;
    /** The period (in seconds) between value updates */
    private double period = DEFAULT_TIME_BETWEEN_UPDATES;

    /**
     * Gyro constructor given a module and a channel.
    .
     * @param moduleNumber The cRIO module number for the analog module the gyro is connected to.
     * @param channel The analog channel the gyro is connected to.
     */
    public SendableGyro(int moduleNumber, int channel) {
        super(moduleNumber, channel);
    }

    /**
     * Gyro constructor with only a channel.
     *
     * Use the default analog module.
     *
     * @param channel The analog channel the gyro is connected to.
     */
    public SendableGyro(int channel) {
        super(channel);
    }

    /**
     * Gyro constructor with a precreated analog channel object.
     * Use this constructor when the analog channel needs to be shared. There
     * is no reference counting when an AnalogChannel is passed to the gyro.
     * @param channel The AnalogChannel object that the gyro is connected to.
     */
    public SendableGyro(AnalogChannel channel) {
        super(channel);
    }

    public double getAngle() {
        return offset + super.getAngle();
    }

    public void reset() {
        offset = 0;
        super.reset();
    }

    /**
     * Sets the time (in seconds) between updates to the {@link SmartDashboard}.
     * The default is 0.2 seconds.
     * @param period the new time between updates
     * @throws IllegalArgumentException if given a period that is less than or equal to 0
     */
    public void setUpdatePeriod(double period) {
        if (period <= 0) {
            throw new IllegalArgumentException("Must be given positive value, given:" + period);
        }
        this.period = period;
    }

    /**
     * Returns the period (in seconds) between updates to the {@link SmartDashboard}.
     * This value is independent of whether or not this {@link SendableGyro} is connected
     * to the {@link SmartDashboard}.  The default value is 0.2 seconds.
     * @return the period (in seconds)
     */
    public double getUpdatePeriod() {
        return period;
    }

    /**
     * Reset the gyro.
     * Resets the gyro to the given heading. This can be used if there is significant
     * drift in the gyro and it needs to be recalibrated after it has been running.
     * @param angle the angle the gyro should believe it is pointing
     */
    public void resetToAngle(double angle) {
        offset = angle;
        super.reset();
    }

    public String getType() {
        return "Gyro";
    }
    private NetworkTable table;

    public NetworkTable getTable() {
        if (table == null) {
            table = new NetworkTable();
            table.putInt("angle", (int) getAngle());
            table.addListener("angle", new NetworkListener() {

                public void valueChanged(String key, Object value) {
                    resetToAngle(((Integer) value).doubleValue());
                }

                public void valueConfirmed(String key, Object value) {
                }
            });
            new Thread() {

                public void run() {
                    while (true) {
                        Timer.delay(period);
                        table.putInt("angle", (int) getAngle());
                    }
                }
            }.start();
        }
        return table;
    }
}
