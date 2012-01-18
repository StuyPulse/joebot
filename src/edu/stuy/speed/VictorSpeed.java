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

    public double getRPM() {
        return m_RPM;
    }

    public void setRPM(double rpm) {
        m_RPM = rpm;
    }
    
    int SECOND_SIDECAR_SLOT = 6;
    double m_RPM;
    Encoder encoder;
    Victor victor;
    PIDController controller;
    double lastTime;
    final double PROPORTIONAL                       = 0.00365;
    final double INTEGRAL                       = 0.00;
    final double DERIVATIVE                       = 0.000012;
    
    /**
     * Make an actual speed controller complete with a Victor, Encoder and PIDController
     * @param victorChannel The PWM channel for the victor.
     * @param encoderAChannel Digital in for the encoder.
     * @param encoderBChannel Input for the other encoder.
     * @param reverse Not used.  Was for reversing encoder direction.
     */
    public VictorSpeed(int victorChannel, int encoderAChannel, int encoderBChannel, boolean reverse) {
        victor = new Victor(victorChannel);

        encoder = new Encoder(encoderAChannel, encoderBChannel, reverse, CounterBase.EncodingType.k2X);
        encoder.setDistancePerPulse(ENCODER_RPM_PER_PULSE);
        encoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate); // use e.getRate() for feedback
        encoder.start();

        controller = new PIDController(PROPORTIONAL, INTEGRAL, DERIVATIVE, encoder, victor);
        controller.setOutputRange(-1, 1);
        controller.enable();
    }

    /**
     * Make a FAKE one just to construct dummy encoders.  Only the first, third,
     * fifth and eighth encoders to be constructed will work properly when we
     * call getRate(); this is an acknowledged bug in the NI software and requires
     * us to make fake Encoders on a different slot.
     * @param encoderAChannel
     * @param encoderBChannel
     * @param reverse
     */
    public VictorSpeed(int encoderAChannel, int encoderBChannel, boolean reverse) {
        encoder = new Encoder(SECOND_SIDECAR_SLOT, encoderAChannel, SECOND_SIDECAR_SLOT, encoderBChannel, reverse, CounterBase.EncodingType.k2X);
        encoder.setDistancePerPulse(ENCODER_RPM_PER_PULSE);
        encoder.setPIDSourceParameter(Encoder.PIDSourceParameter.kRate); // use e.getRate() for feedback
        encoder.start();
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
        controller.setSetpoint(speedRPM);
    }

    /**
     * Never call this method.  We need to have one in order to implement the
     * SpeedController interface and pass this class into a DriveTrain, etc.,
     * but we are using Victors which do not use syncGroups.
     */
    public void set(double speed, byte syncGroup) {
        set(speed);
    }

    public double get() {
        return victor.get();
    }

    public void disable() {
        victor.disable();
    }
}