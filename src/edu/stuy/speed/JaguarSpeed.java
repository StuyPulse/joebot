/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.speed;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.can.CANTimeoutException;

/**
 * Jaguar motor controller with speed methods
 * @author admin
 */
public class JaguarSpeed implements JoeSpeed {

    public static final double KP = 0.0;
    public static final double KI = 0.0;
    public static final double KD = 0.0;
    private CANJaguar jaguar;
    private double speedSetpoint;
    //This is wrong. Need to run calculations for the real one.
    private static final double TOLERANCE_RPM = 4;

    /**
     * Constructs a new CANJaguar using speed control.
     * @param id CAN ID of the Jaguar
     */
    public JaguarSpeed(int id) {
        speedSetpoint = 0;
        try {
            jaguar = new CANJaguar(id, CANJaguar.ControlMode.kSpeed);
            jaguar.configEncoderCodesPerRev(ENCODER_CODES_PER_REV);
            jaguar.setPID(KP, KI, KD);
            jaguar.enableControl();
        } catch (CANTimeoutException e) {
        }

    }

    public double getRPM() {
        try {
            return jaguar.getSpeed();
        } catch (CANTimeoutException e) {
            return 0;
        }
    }

    public void setRPM(double rpm) {
        speedSetpoint = rpm;
        try {
            jaguar.setX(rpm);
        } catch (CANTimeoutException e) {
        }
    }

    /**
     * Checks if the current RPM is within the
     * tolerance range of the desired RPM.
     * @return atSetPoint
     */
    public boolean isAtSetPoint() {
        boolean atSetPoint = false;
        if (Math.abs(getRPM() - speedSetpoint) < TOLERANCE_RPM) {
            atSetPoint = true;
        }
        return atSetPoint;
    }
}
