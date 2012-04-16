/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.speed;

import edu.wpi.first.wpilibj.CANJaguar;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.can.CANTimeoutException;
import edu.wpi.first.wpilibj.networktables.NetworkTableKeyNotDefined;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Jaguar motor controller with speed methods
 * @author admin
 */
public class JaguarSpeed implements JoeSpeed {

    public static final double UPPER_KP = 0.3;
    public static final double UPPER_KI = 0.3;
    public static final double UPPER_KD = 0.3;
    public static final double LOWER_KP = 0.3;
    public static final double LOWER_KI = 0.3;
    public static final double LOWER_KD = 0.3;
    public static double KP;
    public static double KI;
    public static double KD;
    public CANJaguar jaguar;
    private double speedSetpoint;
    public double toleranceRPM;

    private int CANid;
    /**
     * Constructs a new CANJaguar using speed control.
     *
     * @param id CAN ID of the Jaguar
     * @param toleranceRPM the value of toleranceRPM
     */
    public JaguarSpeed(int id, double toleranceRPM, boolean isUpperRoller) {
        CANid = id;
        speedSetpoint = 0;
        this.toleranceRPM = toleranceRPM;
        try {
            jaguarInit();
        }
        catch (CANTimeoutException e) {
            if (!DriverStation.getInstance().isFMSAttached()) {
                e.printStackTrace();
            }
        }
        if (isUpperRoller) {
            KP = UPPER_KP;
            KI = UPPER_KI;
            KD = UPPER_KD;
        } else {
            KP = LOWER_KP;
            KI = LOWER_KI;
            KD = LOWER_KD;
        }
    }

    public void jaguarInit() throws CANTimeoutException {
        jaguar = new CANJaguar(CANid);
        jaguar.changeControlMode(CANJaguar.ControlMode.kSpeed);
        jaguar.setSpeedReference(CANJaguar.SpeedReference.kQuadEncoder);
        jaguar.configEncoderCodesPerRev(ENCODER_CODES_PER_REV);
        jaguar.setPID(KP, KI, KD);
        jaguar.configFaultTime(0.5);
        jaguar.enableControl();
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
            jaguar.setX(-rpm);
            
        } catch (CANTimeoutException e) {
            if (!DriverStation.getInstance().isFMSAttached()) {
                e.printStackTrace();
            }
        }
    }

    public void setPID(String prefix) {
        try {
            jaguar.setPID(SmartDashboard.getDouble(prefix+"P"), SmartDashboard.getDouble(prefix+"I"), SmartDashboard.getDouble(prefix+"D"));
        }
        catch (NetworkTableKeyNotDefined e) {
            SmartDashboard.putDouble(prefix+"P", 0);
            SmartDashboard.putDouble(prefix+"I", 0);
            SmartDashboard.putDouble(prefix+"D", 0);
        }
        catch (CANTimeoutException e) {
        }
    }

    /**
     * Checks if the current RPM is within the
     * tolerance range of the desired RPM.
     * @return atSetPoint
     */
    public boolean isAtSetPoint() {
        boolean atSetPoint = false;
        if (Math.abs(getRPM() - speedSetpoint) < toleranceRPM) {
            atSetPoint = true;
        }
        return atSetPoint;
    }
}
