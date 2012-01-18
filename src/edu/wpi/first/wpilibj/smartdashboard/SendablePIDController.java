/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008-2012. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.smartdashboard;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.networktables.NetworkListener;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

/**
 * A {@link SendablePIDController} is a {@link PIDController} that can be sent over to the {@link SmartDashboard} using
 * the {@link SmartDashboard#putData(java.lang.String, edu.wpi.first.wpilibj.smartdashboard.SmartDashboardData) putData(...)}
 * method.
 *
 * <p>It is useful if you want to test values of a {@link PIDController} without having to recompile code between tests.
 * Also, consider using {@link Preferences} to save the important values between matches.</p>
 * 
 * @author Joe Grinstead
 * @see SmartDashboard
 */
public class SendablePIDController extends PIDController implements SmartDashboardData {

    /**
     * Allocate a PID object with the given constants for P, I, D, using a 50ms period.
     * @param p the proportional coefficient
     * @param i the integral coefficient
     * @param d the derivative coefficient
     * @param source The PIDSource object that is used to get values
     * @param output The PIDOutput object that is set to the output value
     */
    public SendablePIDController(double p, double i, double d, PIDSource source, PIDOutput output) {
        super(p, i, d, source, output);
    }

    /**
     * Allocate a PID object with the given constants for P, I, D
     * @param p the proportional coefficient
     * @param i the integral coefficient
     * @param d the derivative coefficient
     * @param source The PIDSource object that is used to get values
     * @param output The PIDOutput object that is set to the output value
     * @param period the loop time for doing calculations. This particularly effects calculations of the
     * integral and differential terms. The default is 50ms.
     */
    public SendablePIDController(double p, double i, double d, PIDSource source, PIDOutput output, double period) {
        super(p, i, d, source, output, period);
    }

    /**
     * Set the setpoint for the PIDController
     * @param setpoint the desired setpoint
     */
    public synchronized void setSetpoint(double setpoint) {
        super.setSetpoint(setpoint);
	if (table != null) {
            table.putDouble("setpoint", setpoint);
	}
}
    
    
    
    public synchronized void setPID(double p, double i, double d) {
        super.setPID(p, i, d);

        if (table != null) {
            table.putDouble("p", p);
            table.putDouble("i", i);
            table.putDouble("d", d);
        }
    }

    public synchronized void enable() {
        super.enable();

        if (table != null) {
            table.putBoolean("enabled", true);
        }
    }

    public synchronized void disable() {
        super.disable();

        if (table != null) {
            table.putBoolean("enabled", false);
        }
    }
    private NetworkTable table;

    public String getType() {
        return "PIDController";
    }

    public NetworkTable getTable() {
        if (table == null) {
            table = new NetworkTable();

            table.putDouble("p", getP());
            table.putDouble("i", getI());
            table.putDouble("d", getD());
            table.putDouble("setpoint", getSetpoint());
            table.putBoolean("enabled", isEnable());

            table.addListenerToAll(new NetworkListener() {

                public void valueChanged(String key, Object value) {
                    if (key.equals("p") || key.equals("i") || key.equals("d")) {
                        SendablePIDController.super.setPID(table.getDouble("p", 0.0), table.getDouble("i", 0.0), table.getDouble("d", 0.0));
                    } else if (key.equals("setpoint")) {
                        SendablePIDController.super.setSetpoint(((Double) value).doubleValue());
                    } else if (key.equals("enabled")) {
                        if (((Boolean) value).booleanValue()) {
                            SendablePIDController.super.enable();
                        } else {
                            SendablePIDController.super.disable();
                        }
                    }
                }

                public void valueConfirmed(String key, Object value) {
                }
            });
        }
        return table;
    }
}
