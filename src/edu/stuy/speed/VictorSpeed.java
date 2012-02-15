/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.stuy.speed;

import edu.wpi.first.wpilibj.*;

/**
 *
 * @author admin
 */
public class VictorSpeed implements JoeSpeed {

    public static final double KP = 0.00365;
    public static final double KI = 0.00;
    public static final double KD = 0.000012;
    
    private Encoder encoder;
    private Victor victor;
    private PIDController controller;

    private double speedSetpoint;
    //This is wrong. Need to run calculations for the real one.
    private static final double TOLERANCE_RPM = 4;

    /**
     * Make an actual speed controller complete with a Victor, Encoder and PIDController
     * @param victorChannel The PWM channel for the victor.
     * @param encoderAChannel Digital in for the encoder.
     * @param encoderBChannel Input for the other encoder.
     * @param reverse Not used.  Was for reversing encoder direction.
     */
    public VictorSpeed(int victorChannel, int encoderAChannel, int encoderBChannel) {
        speedSetpoint = 0;
        victor = new Victor(victorChannel);

        encoder = new Encoder(encoderAChannel, encoderBChannel, false, CounterBase.EncodingType.k4X);
        encoder.setDistancePerPulse(ENCODER_RPM_PER_PULSE);
        encoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate); // use e.getRate() for feedback
        encoder.start();

        controller = new PIDController(KP, KI, KD, encoder, victor);
        controller.setOutputRange(-1, 1);
        controller.enable();
    }

    /**
     * Set the PWM value.
     *
     * The PWM value is set using a range of -1.0 to 1.0, appropriately
     * scaling the value for the FPGA.
     *
     * @param output The speed value between -1.0 and 1.0 to set.
     */
    public void pidWrite(double output) {
        victor.set(output);
    }

    /**
     * Set a wheel's speed setpoint.
     * @param speedRPM The desired wheel speed in RPM (revolutions per minute).
     */
    public void set(double speedRPM) {
        speedSetpoint = speedRPM;
        controller.setSetpoint(speedRPM);
    }

    public double get() {
        return victor.get();
    }

    public void disable() {
        victor.disable();
    }

    public double getRPM() {
        double circumference = 2 * Math.PI * ROLLER_RADIUS;
        int seconds = 60;
        return (seconds * encoder.getRate()/(circumference));  //Converted from Distance/Second to RPM
    }

    public void setRPM(double rpm) {
        set(rpm);
    }

    public boolean isAtSetPoint() {
        boolean atSetPoint = false;
        if (Math.abs(getRPM() - speedSetpoint) < TOLERANCE_RPM) {
            atSetPoint = true;
        }
        return atSetPoint;
    }
}